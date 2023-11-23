package cn.iocoder.yudao.module.wms.service.common;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.wms.controller.app.pda.vo.WarehousingVO;
import cn.iocoder.yudao.module.wms.dal.dataobject.tray.TrayDO;
import cn.iocoder.yudao.module.wms.dal.dataobject.traylog.TrayLogDO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.GroupTrayReqDTO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.OutboundDTO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.RemoveTrayReqDTO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.TrayDTO;

/**
 * @author jiangfeng
 * @date 2023/7/18
 */
public interface WmsService {
    /**
     * 组盘入库方法
     *
     * @param warehousingVO
     * @param trayDO
     * @return
     */
    TrayLogDO groupTrayWarehousing(WarehousingVO warehousingVO, TrayDO trayDO);


    Boolean updateDataWhenArrive(String trayNo, String storage);

    /**
     * 通用出库方法
     * @param outboundDTO
     * @return
     */
    TrayLogDO generalMethodOfOutbound(TrayLogDO outboundDTO);

    /**
     * 托盘绑定
     *
     * @param trayDO
     * @param trayLogDO
     */
    void trayBind(TrayDO trayDO, TrayLogDO trayLogDO);

    /**
     * 托盘到达供wcs调用
     * @param reqDTO
     * @return
     */
    String taryArrival(TrayDTO reqDTO);

    /**
     * 托盘开始移动供WCS调用
     * @param trayDTO
     * @return
     */
    String taryStartMove(TrayDTO trayDTO);

    /**
     * 托盘出库 供WCS、上位机使用（出库前无拆盘组盘操作）
     * @param outboundDTO
     * @return
     */
    String outbound(OutboundDTO outboundDTO);

    /**
     * 组托 供WCS、上位机使用
     * @param groupTrayReqDTO
     * @return
     */
    String groupTray(GroupTrayReqDTO groupTrayReqDTO);

    /**
     * 拆托 供WCS、上位机使用
     * @param removeTrayReqDTO
     * @return
     */
    String removeTray(RemoveTrayReqDTO removeTrayReqDTO);
}
