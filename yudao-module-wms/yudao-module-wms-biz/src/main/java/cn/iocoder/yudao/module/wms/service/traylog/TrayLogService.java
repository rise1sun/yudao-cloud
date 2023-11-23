package cn.iocoder.yudao.module.wms.service.traylog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wms.controller.admin.traylog.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.traylog.TrayLogDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.wms.enums.common.Constants;

/**
 * wms托盘日志记录 Service 接口
 *
 * @author 管理员
 */
public interface TrayLogService {

    /**
     * 创建wms托盘日志记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createTrayLog(@Valid TrayLogCreateReqVO createReqVO);

    /**
     * 更新wms托盘日志记录
     *
     * @param updateReqVO 更新信息
     */
    void updateTrayLog(@Valid TrayLogUpdateReqVO updateReqVO);

    /**
     * 删除wms托盘日志记录
     *
     * @param id 编号
     */
    void deleteTrayLog(Integer id);

    /**
     * 获得wms托盘日志记录
     *
     * @param id 编号
     * @return wms托盘日志记录
     */
    TrayLogDO getTrayLog(Integer id);

    /**
     * 获得wms托盘日志记录列表
     *
     * @param ids 编号
     * @return wms托盘日志记录列表
     */
    List<TrayLogDO> getTrayLogList(Collection<Integer> ids);

    /**
     * 获得wms托盘日志记录分页
     *
     * @param pageReqVO 分页查询
     * @return wms托盘日志记录分页
     */
    PageResult<TrayLogDO> getTrayLogPage(TrayLogPageReqVO pageReqVO);

    /**
     * 获得wms托盘日志记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return wms托盘日志记录列表
     */
    List<TrayLogDO> getTrayLogList(TrayLogExportReqVO exportReqVO);

    void insertTaryLog(TrayLogDO setType);

    /**
     * 查询下料任务
     * @param trayNO
     * @param storage
     * @param ordinal
     * @return
     */
    TrayLogDO getTrayLogByTrayAndStorage(String trayNO, String storage, int ordinal);

    /**
     * 根据托盘号获取TrayLog信息
     *
     * @param tray
     * @return
     */
    TrayLogDO getTrayLogByTray(String tray);
}
