package cn.iocoder.yudao.module.wms.service.common;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.module.wms.common.Strategy.FormulaItem.FormulaItemStrategy;
import cn.iocoder.yudao.module.wms.common.Strategy.factory.FormulaItmeStrategyFactory;
import cn.iocoder.yudao.module.wms.controller.app.pda.vo.WarehousingVO;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaitem.FormulaItemDO;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaordertask.FormulaOrderTaskDO;
import cn.iocoder.yudao.module.wms.dal.dataobject.storage.StorageDO;
import cn.iocoder.yudao.module.wms.dal.dataobject.tray.TrayDO;
import cn.iocoder.yudao.module.wms.dal.dataobject.traylog.TrayLogDO;
import cn.iocoder.yudao.module.wms.enums.api.barcode.dto.BatteryinfoDTO;
import cn.iocoder.yudao.module.wms.enums.api.storage.dto.StorageDTO;
import cn.iocoder.yudao.module.wms.enums.api.strategy.dto.FormulaItemStrategyDTO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.GroupTrayReqDTO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.OutboundDTO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.RemoveTrayReqDTO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.TrayDTO;
import cn.iocoder.yudao.module.wms.enums.common.Constants;
import cn.iocoder.yudao.module.wms.mq.producer.TaskProducer;
import cn.iocoder.yudao.module.wms.service.barcode.BarcodeService;
import cn.iocoder.yudao.module.wms.service.formulaitem.FormulaItemService;
import cn.iocoder.yudao.module.wms.service.formulaordertask.FormulaOrderTaskService;
import cn.iocoder.yudao.module.wms.service.storage.StorageService;
import cn.iocoder.yudao.module.wms.service.tray.TrayService;
import cn.iocoder.yudao.module.wms.service.traylog.TrayLogService;
import com.alibaba.fastjson.JSON;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jiangfeng
 * @date 2023/7/18
 */

@Service
public class WmsServiceImpl implements WmsService {

    @Resource
    private TrayService trayService;

    @Resource
    private BarcodeService barcodeService;

    @Resource
    private FormulaItemService formulaItemService;

    @Resource
    private FormulaOrderTaskService formulaOrderTaskService;

    @Resource
    private TrayLogService trayLogService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StorageService storageService;

    @Resource
    private TaskProducer taskProducer;


    @Override
    @Transactional
    public TrayLogDO groupTrayWarehousing(WarehousingVO warehousingVO, TrayDO trayDO) {
        Integer orderTaskId = formulaItemService.formulaItemFlow(warehousingVO);
        return commonWarehousing(warehousingVO, trayDO, orderTaskId);
    }

    /**
     * 通用入库方法
     *
     * @param warehousingVO
     * @param trayDO
     * @param orderTaskId
     * @return
     */
    private TrayLogDO commonWarehousing(WarehousingVO warehousingVO, TrayDO trayDO, Integer orderTaskId) {
        List<String> barcodes = barcodeService.updateBarcodeByCode(warehousingVO);
        String startArea = getAreaByStorage(warehousingVO.getDevice());
        String endArea = getAreaByStorage(warehousingVO.getEnddevice());
        //入库业务逻辑处理
        strategyHandle(warehousingVO.getDevice(), warehousingVO.getTrayNo(), orderTaskId);
        TrayLogDO trayLogDO = new TrayLogDO()
                .setType(warehousingVO.getType())
                .setTrayNo(warehousingVO.getTrayNo())
                .setStartStorage(warehousingVO.getDevice())
                .setStartArea(startArea)
                .setBarcodes(barcodes.toString())
                .setBarcodeNumber(barcodes.size())
                .setBarcodeStatus("托盘" + warehousingVO.getTrayNo() + "入库成功")
                .setServiceType((byte) Constants.trayLogType.WAREHOUSING.ordinal())
                .setEndStorage(warehousingVO.getEnddevice())
                .setEndArea(endArea)
                .setTrayType(trayDO.getIsTestTray())
                .setOrderTaskId(orderTaskId);
        //托盘日志记录
        trayLogService.insertTaryLog(trayLogDO);
        return trayLogDO;
    }

    /**
     * 获取托盘条码对应关系
     *
     * @param trayNo
     */
    private List<BatteryinfoDTO> trayBarcodesGetInRedis(String trayNo) {
        List<String> barcodes = (List<String>) redisTemplate.opsForHash().get(Constants.TRAY_BARCODES_HASHKEY, trayNo);
        List<BatteryinfoDTO> batteryinfoDTOList = barcodes.stream()
                .map(barcode -> JSONUtil.createObj().set("batteriesBarcode", barcode))
                .map(barcode -> JSON.parseObject(String.valueOf(barcode), BatteryinfoDTO.class))
                .collect(Collectors.toList());
        //TODO 数据为空 添加兜底方案
        return batteryinfoDTOList;
    }


    /**
     * 缓存托盘条码对应关系
     *
     * @param trayNo
     * @param barcodes
     */
    private void trayBarcodesPutToRedis(String trayNo, List<String> barcodes) {
        redisTemplate.opsForHash().put(Constants.TRAY_BARCODES_HASHKEY, trayNo, barcodes);
    }

    private String vaildAndGetStorage(String enddevice, Integer orderTaskId) {
        if (StrUtil.isBlank(enddevice)) {
            synchronized (WmsServiceImpl.class) {
                String storage = storageService.getRecommendStorageByOrderTaskId(orderTaskId);
                storageService.preLockStorage(storage);
                return storage;
            }
        } else {
            StorageDO storageDO = storageService.getStorageDOByStorage(enddevice);
            storageService.preLockStorage(storageDO.getCode());
            return storageDO.getCode();
        }
    }

    private String getAreaByStorage(String device) {
        if (StrUtil.isBlank(device)) {
            return null;
        }
        String val = (String) redisTemplate.opsForHash().get(Constants.STORAGE_HASHKEY, device);
        StorageDTO storageDTO = JSON.parseObject(val, StorageDTO.class);
        if (storageDTO != null && StrUtil.isNotBlank(storageDTO.getAreaPrefix())) {
            return storageDTO.getAreaPrefix();
        }
        List<StorageDTO> areaCode = storageService.getStorageInfoList(device);
        if (areaCode.size() > 0 && StrUtil.isNotBlank(areaCode.get(0).getAreaPrefix())) {
            return areaCode.get(0).getAreaPrefix();
        }
        return "区域未维护";
    }

    /**
     * PDA 托盘绑定（更新条码的托盘数据合并到入库逻辑中）
     *
     * @param trayDO
     * @param vo
     */
    public void trayBind(TrayDO trayDO, TrayLogDO vo) {
        trayService.trayOccupy(trayDO, vo.getStartStorage());
        TrayLogDO newTrayLogDO = new TrayLogDO()
                .setType(vo.getType())
                .setTrayNo(trayDO.getTrayNo())
                .setStartStorage(vo.getStartStorage())
                .setStartArea(vo.getStartArea())
                .setBarcodeStatus("组盘")
                .setServiceType((byte) Constants.trayLogType.GROUPTRAY.ordinal())
                .setTrayType(trayDO.getIsTestTray())
                .setBarcodeNumber(vo.getBarcodeNumber())
                .setBarcodes(vo.getBarcodes());
        //托盘日志记录
        trayLogService.insertTaryLog(newTrayLogDO);
        trayBarcodesPutToRedis(trayDO.getTrayNo(), JSONUtil.parseArray(vo.getBarcodes()).toList(String.class));


    }

    @Override
    @Transactional
    public String taryArrival(TrayDTO reqDTO) {
        TrayDO trayDO = trayService.getTrayDO(reqDTO.getTray());
        WarehousingVO warehousingVO = reqDTOToWarehousingVO(reqDTO, trayDO);
        Integer orderTaskId = formulaItemService.formulaItemFlow(warehousingVO);
        commonWarehousing(warehousingVO, trayDO, orderTaskId);
        return "入库成功";
    }

    @Override
    public String taryStartMove(TrayDTO trayDTO) {
        storageService.unLockStorage(trayDTO.getStorage());
        return "库位更新成功";
    }

    @Override
    @Transactional
    public String outbound(OutboundDTO outboundDTO) {
        String tray = outboundDTO.getTray();
        /**
         * 获取最新一条入库记录(期间没有拆组盘)
         * 1、无拆组盘 直接出库
         * 2、有拆组盘=> 拆盘 => 组盘 => 出库
         */
        TrayLogDO result = storageService.getTrayLogByTray(tray);
        //TODO 后面改入库记录的细节
        result.setType((byte) Constants.dataSourceType.WCS.ordinal());
        generalMethodOfOutbound(result);
        return "出库成功";
    }

    @Override
    @Transactional
    public String groupTray(GroupTrayReqDTO groupTrayReqDTO) {
        List<BatteryinfoDTO> barcodes = groupTrayReqDTO.getBarcodes();
        String tray = groupTrayReqDTO.getTray();
        TrayDO trayDO = trayService.getTrayDO(tray);
        TrayLogDO result = storageService.getTrayLogByTray(tray);
        trayBind(trayDO, result);
        return "组盘成功";
    }

    @Override
    @Transactional
    public String removeTray(RemoveTrayReqDTO removeTrayReqDTO) {
        String tray = removeTrayReqDTO.getTray();
        TrayDO trayDO = trayService.getTrayDO(tray);
        trayService.taryRelease(tray);
        List<String> barcodes = null;
        //更新条码信息
        barcodeService.updateBarcodeTrayInfoByCode(barcodes, trayDO.getId());
        TrayLogDO result = storageService.getTrayLogByTray(tray);
        TrayLogDO newTrayLogDO = new TrayLogDO()
                .setType(result.getType())
                .setTrayNo(tray)
                .setStartStorage(result.getStartStorage())
                .setStartArea(result.getStartArea())
                .setBarcodeStatus("拆盘")
                .setServiceType((byte) Constants.trayLogType.REMOVETRAY.ordinal())
                .setTrayType(result.getTrayType())
                .setBarcodeNumber(result.getBarcodeNumber())
                .setBarcodes(result.getBarcodes());
        //托盘日志记录
        trayLogService.insertTaryLog(newTrayLogDO);
        deleteTrayBarcodesInRedis(tray);
        return "拆盘成功";
    }

    /**
     * 清除托盘条码缓存
     *
     * @param tray
     */
    private void deleteTrayBarcodesInRedis(String tray) {
        redisTemplate.opsForHash().delete(Constants.TRAY_BARCODES_HASHKEY, tray);
    }

    private WarehousingVO reqDTOToWarehousingVO(TrayDTO reqDTO, TrayDO trayDO) {

        WarehousingVO warehousingVO = new WarehousingVO()
                .setTrayNo(reqDTO.getTray())
                .setTrayId(trayDO.getId())
                .setDevice(reqDTO.getStorage())
                .setEnddevice(null)
                .setBarcodes(trayBarcodesGetInRedis(reqDTO.getTray()))
                .setBarcodeStatus(Constants.barcodeStatus.INSTOCK.ordinal())
                .setOrderTaskId(trayDO.getOrderTaskId())
                .setUseNumber(trayDO.getUseNumber())
                .setIsTestTray(trayDO.getIsTestTray())
                .setType((byte) Constants.dataSourceType.WCS.ordinal());
        // .setUserName(groupTrayVO.getUsercode());
        return warehousingVO;
    }

    /**
     * @param storage
     * @param trayNo
     * @param orderTaskId
     * @return
     */
    @NotNull
    private FormulaItemDO strategyHandle(String storage, String trayNo, Integer orderTaskId) {
        FormulaItemDO formulaItem = formulaItemService.getFormulaItemByorderTaskId(orderTaskId);
        FormulaItemStrategy strategy = FormulaItmeStrategyFactory.getStrategy(formulaItem.getCode());
        FormulaItemStrategyDTO formulaItemStrategyDTO = new FormulaItemStrategyDTO()
                .setTrayNo(trayNo)
                .setStorage(storage)
                .setArea(formulaItem.getArea())
                .setRestingTime(formulaItem.getRestingTime());
        strategy.handle(formulaItemStrategyDTO);
        return formulaItem;
    }

    @Override
    public Boolean updateDataWhenArrive(String trayNo, String storage) {
        String stringTray = (String) redisTemplate.opsForHash().get(Constants.TRAY_HASHKEY, trayNo);
        TrayDO trayDO = JSON.parseObject(stringTray, TrayDO.class);
        Integer orderTaskId = trayDO.getOrderTaskId();
        FormulaOrderTaskDO formulaOrderTask = formulaOrderTaskService.getFormulaOrderTask(orderTaskId);
        String formulaItemId = formulaOrderTask.getFormulaItemId();
        FormulaItemDO formulaItem = formulaItemService.getFormulaItem(Integer.valueOf(formulaItemId));
        String restingTime = formulaItem.getRestingTime();
        try {
           // SettingTheRestingTime(trayNo, restingTime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public TrayLogDO generalMethodOfOutbound(TrayLogDO vo) {
        String trayNo = vo.getTrayNo();
        String startStorage = vo.getStartStorage();
        String startArea = vo.getStartArea();
        Byte isTestTray = vo.getTrayType();
        Integer orderTaskId = vo.getOrderTaskId();
        String endStorage = vaildAndGetStorage(vo.getEndStorage(), orderTaskId);
        String endArea = getAreaByStorage(endStorage);
        //String wcsTaskNo = "wcs123";
        TrayLogDO trayLogDO = new TrayLogDO()
                .setType(vo.getType())
                .setTrayNo(trayNo)
                .setStartStorage(startStorage)
                .setStartArea(startArea)
                .setBarcodes(vo.getBarcodes())
                .setBarcodeNumber(vo.getBarcodeNumber())
                .setBarcodeStatus("托盘" + trayNo + "出库成功")
                .setServiceType((byte) Constants.trayLogType.OUTBOUND.ordinal())
                .setEndStorage(endStorage)
                .setEndArea(endArea)
             //   .setDispatchTaskNumber(wcsTaskNo) wcs调用成功回写任务号,否则任务调用失败,定时器进行重试
                .setTrayType(isTestTray);
        trayLogService.insertTaryLog(trayLogDO);
        taskProducer.sendTaskMessage(trayNo,startStorage,startArea,orderTaskId,endStorage,endArea);
        return trayLogDO;
    }
}
