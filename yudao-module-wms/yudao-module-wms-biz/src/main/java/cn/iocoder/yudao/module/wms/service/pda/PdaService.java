package cn.iocoder.yudao.module.wms.service.pda;

import cn.iocoder.yudao.module.wms.controller.app.pda.vo.GroupTrayVO;
import cn.iocoder.yudao.module.wms.controller.app.pda.vo.ManualCuttingVO;
import cn.iocoder.yudao.module.wms.controller.app.pda.vo.TrayStorageVO;

import java.util.Map;

/**
 * @author jiangfeng
 * @date 2023/7/13
 */
public interface PdaService {

    /**
     * 手工组托
     * @param reqDTO
     * @return
     */
    String groupTray(GroupTrayVO reqDTO);

    /**
     * 手动下料
     *
     * @return
     */
    String manualCutting(String trayNo,String storage);

    /**
     * 校验托盘库位信息
     * @param reqDTO
     * @return
     */
    Map<Boolean, String> checkParameters(TrayStorageVO reqDTO);

    /**
     * 校验托盘位置是否正确
     * @param tray
     * @param storage
     * @return
     */
    Boolean cuttingValid(String tray, String storage);

    String getIdempotent(String trayNo, String storage);
}
