package cn.iocoder.yudao.module.wms.service.pda;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.module.wms.common.annotation.RedissonLock;
import cn.iocoder.yudao.module.wms.controller.app.pda.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.storage.StorageDO;
import cn.iocoder.yudao.module.wms.dal.dataobject.tray.TrayDO;
import cn.iocoder.yudao.module.wms.dal.dataobject.traylog.TrayLogDO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.OutboundDTO;
import cn.iocoder.yudao.module.wms.enums.common.Constants;
import cn.iocoder.yudao.module.wms.service.barcode.BarcodeService;
import cn.iocoder.yudao.module.wms.service.common.WmsService;
import cn.iocoder.yudao.module.wms.service.storage.StorageService;
import cn.iocoder.yudao.module.wms.service.tray.TrayService;
import cn.iocoder.yudao.module.wms.service.traylog.TrayLogService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.jetbrains.annotations.NotNull;
import org.redisson.Redisson;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangfeng
 * @date 2023/7/13
 */
@Service
public class PdaServiceImpl implements PdaService {

    @Resource
    private TrayService trayService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private BarcodeService barcodeServoce;

    @Resource
    private WmsService wmsService;

    @Resource
    private StorageService storageService;

    @Resource
    private TrayLogService trayLogService;

    @Override
    @Transactional
    public String groupTray(GroupTrayVO groupTrayVO) {
        TrayDO trayDO = trayService.getTrayDOByDB(groupTrayVO.getTray());
        WarehousingVO warehousingVO = groupTrayVOToWarehousingVO(groupTrayVO, trayDO);
        //入库, 后续考虑单独剥离
        TrayLogDO result = wmsService.groupTrayWarehousing(warehousingVO, trayDO);
        trayDO.setOrderTaskId(result.getOrderTaskId());
        //组盘
        wmsService.trayBind(trayDO, result);
        result.setType((byte) Constants.dataSourceType.PDA.ordinal());
        //出库
        wmsService.generalMethodOfOutbound(result);
        return "组盘成功";
    }

    private WarehousingVO groupTrayVOToWarehousingVO(GroupTrayVO groupTrayVO, TrayDO trayDO) {
        WarehousingVO warehousingVO = new WarehousingVO()
                .setTrayNo(groupTrayVO.getTray())
                .setTrayId(trayDO.getId())
                .setDevice(groupTrayVO.getDevice())
                .setEnddevice(groupTrayVO.getEnddevice())
                .setBarcodes(groupTrayVO.getBarcodes())
                .setBarcodeStatus(Constants.barcodeStatus.INSTOCK.ordinal())
                .setOrderTaskId(trayDO.getOrderTaskId())
                .setUseNumber(trayDO.getUseNumber())
                .setIsTestTray(trayDO.getIsTestTray())
                .setType((byte) Constants.dataSourceType.PDA.ordinal())
                .setUserName(groupTrayVO.getUsercode());
        return warehousingVO;
    }

    @Override
    @RedissonLock(key = "#trayNo+'_'+#storage")
    public String manualCutting(String trayNo,String storage) {
        //组装幂等号
        String idempotent = getIdempotent(trayNo, storage);
        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent("wms_manualCutting:" + idempotent,
                idempotent, 5, TimeUnit.SECONDS);
        if(!ifAbsent){
            return "正在下料！";
        }

       //下料出库  TODO 下料过程工艺流程是否需要完结？
        wmsService.outbound(new OutboundDTO().setTray(trayNo));

        return "下料成功";
    }

    public String getIdempotent(String trayNo, String storage) {
        return String.format("%s_%s", trayNo, storage);
    }

    public Boolean cuttingValid(String trayNO, String storage) {
        //校验库位和托盘的关系是否对应
        TrayDO tray = trayService.getTrayDOByDB(trayNO);
        if(storage.equals(tray.getStorageName())){
            return true;
        }
        return false;
    }

    @Override
    public Map<Boolean, String> checkParameters(TrayStorageVO reqDTO) {
        HashMap<Boolean, String> resultMap = new HashMap<>();
        String endStorage = reqDTO.getEndStorage();
        try{
            StorageDO endStorageDO = storageService.getStorageDOByStorage(endStorage);
          /*  if(endStorageDO == null){
                resultMap.put(false,endStorage+",没有维护,请在系统添加该库位！");
                return resultMap;
            }
            if(!endStorageDO.getStatus().equals(Constants.storageStatus.FREE.ordinal())){
                resultMap.put(false,endStorage+",已被占用,请确认库位信息或更换库位！");
                return resultMap;
            }*/
        }catch (Exception e){
            resultMap.put(false,endStorage+","+e.getMessage());
            return resultMap;
        }


        //起始位置不需要校验库位状态
        String startStorage = reqDTO.getStartStorage();
        try{
            StorageDO endStorageDO = storageService.getStorageDOByStorage(startStorage);
          /*  if(endStorageDO == null){
                resultMap.put(false,endStorage+",没有维护,请在系统添加该库位！");
                return resultMap;
            }
            if(!endStorageDO.getStatus().equals(Constants.storageStatus.FREE.ordinal())){
                resultMap.put(false,endStorage+",已被占用,请确认库位信息或更换库位！");
                return resultMap;
            }*/
        }catch (Exception e){
            resultMap.put(false,startStorage+","+e.getMessage());
            return resultMap;
        }
        /*if(startStorageDO == null){
            resultMap.put(false,endStorage+",没有维护,请在系统添加该库位！");
            return resultMap;
        }*/

        TrayDO trayDO = trayService.getTrayDOByDB(reqDTO.getTray());
        if(trayDO == null){
            resultMap.put(false, "托盘不存在,请确认托盘信息或更换托盘！");
            return resultMap;
        }
        // 校验状态和绑定次数
        Byte status = trayDO.getStatus();
        if (Constants.trayType.BUSY.ordinal() == status) {
            resultMap.put(false, "托盘已绑定,请确认托盘信息或更换托盘！");
            return resultMap;
        }
        if (trayDO.getMaxUseNumber() <= trayDO.getUseNumber()) {
            resultMap.put(false, "托盘使用次数超过设置上限,请确认托盘信息或更换托盘！");
            return resultMap;
        }
        resultMap.put(true, "允许组盘");
        return resultMap;
    }

    @NotNull
    private static OutboundVO getOutboundVO(ManualCuttingVO reqVO) {
        OutboundVO outboundVO = new OutboundVO();
        // outboundVO.setStorage(reqVO.getStorage());
        outboundVO.setUserName(reqVO.getUserName());
        outboundVO.setTrayNO(reqVO.getTrayNO());
        outboundVO.setFormulaItemEndFlag(reqVO.getFormulaItemEndFlag());
        return outboundVO;
    }
}
