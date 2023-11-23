package cn.iocoder.yudao.module.wms.service.pda.manualCutting;

import org.springframework.stereotype.Service;

/**
 * @author jiangfeng
 * @date 2023/10/13
 */
public interface ManualCuttingService {

    /**
     * 下料
     * @param tray
     * @return
     */
    String manualCutting(String tray,String storage);
}
