package cn.iocoder.yudao.module.wms.service.pda.manualCutting;

import cn.iocoder.yudao.module.wms.common.annotation.RedissonLock;
import cn.iocoder.yudao.module.wms.dal.dataobject.traylog.TrayLogDO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.OutboundDTO;
import cn.iocoder.yudao.module.wms.enums.common.Constants;
import cn.iocoder.yudao.module.wms.mq.producer.TaskProducer;
import cn.iocoder.yudao.module.wms.service.common.WmsService;
import cn.iocoder.yudao.module.wms.service.pda.PdaService;
import cn.iocoder.yudao.module.wms.service.traylog.TrayLogService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangfeng
 * @date 2023/10/13
 */
@Service
public class ManualCuttingServiceImpl implements ManualCuttingService {
    @Resource
    private TrayLogService trayLogService;

    @Resource
    private WmsService wmsService;

    @Resource
    private PdaService pdaService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private TaskProducer taskProducer;

    @Override
    public String manualCutting(String tray,String storage) {
        TrayLogDO result = trayLogService.getTrayLogByTray(tray);
        result.setType((byte) Constants.dataSourceType.PDA.ordinal());
        return manualCuttingOutbound(result);
    }

    /**
     * 下料出库
     * @param result
     */
    @RedissonLock(key = "#trayNo+'_'+#storage")
    private String  manualCuttingOutbound(TrayLogDO result) {
        //幂等
        String idempotent = pdaService.getIdempotent(result.getTrayNo(),result.getDispatchTaskNumber());
        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent("wms_manualCutting:" + idempotent,
                idempotent, 5, TimeUnit.SECONDS);
        if(!ifAbsent){
            return "正在下料！";
        }


        String trayNo = result.getTrayNo();
        String startStorage = result.getStartStorage();
        String startArea = result.getStartArea();
        Byte isTestTray = result.getTrayType();
        //Integer orderTaskId = result.getOrderTaskId();
        String endStorage = "TS-01";
        String endArea = "TS";
        //String wcsTaskNo = "wcs123";
        TrayLogDO trayLogDO = new TrayLogDO()
                .setType(result.getType())
                .setTrayNo(trayNo)
                .setStartStorage(startStorage)
                .setStartArea(startArea)
                .setBarcodes(result.getBarcodes())
                .setBarcodeNumber(result.getBarcodeNumber())
                .setBarcodeStatus("托盘" + trayNo + "下料")
                .setServiceType((byte) Constants.trayLogType.OUTBOUND.ordinal())
                .setEndStorage(endStorage)
                .setEndArea(endArea)
                //   .setDispatchTaskNumber(wcsTaskNo) wcs调用成功回写任务号,否则任务调用失败,定时器进行重试
                .setTrayType(isTestTray);
        trayLogService.insertTaryLog(trayLogDO);
        taskProducer.sendTaskMessage(trayNo,startStorage,startArea,null,endStorage,endArea);

        return "下料成功";
    }
}
