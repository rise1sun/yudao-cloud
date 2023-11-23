package cn.iocoder.yudao.module.wms.service.restingtask;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.wms.controller.admin.restingtask.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.restingtask.RestingTaskDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 静置任务信息 Service 接口
 *
 * @author 管理员
 */
public interface RestingTaskService {

    /**
     * 创建静置任务信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createRestingTask(@Valid RestingTaskCreateReqVO createReqVO);

    /**
     * 更新静置任务信息
     *
     * @param updateReqVO 更新信息
     */
    void updateRestingTask(@Valid RestingTaskUpdateReqVO updateReqVO);

    /**
     * 删除静置任务信息
     *
     * @param id 编号
     */
    void deleteRestingTask(Integer id);

    /**
     * 获得静置任务信息
     *
     * @param id 编号
     * @return 静置任务信息
     */
    RestingTaskDO getRestingTask(Integer id);

    /**
     * 获得静置任务信息列表
     *
     * @param ids 编号
     * @return 静置任务信息列表
     */
    List<RestingTaskDO> getRestingTaskList(Collection<Integer> ids);

    /**
     * 获得静置任务信息分页
     *
     * @param pageReqVO 分页查询
     * @return 静置任务信息分页
     */
    PageResult<RestingTaskDO> getRestingTaskPage(RestingTaskPageReqVO pageReqVO);

    /**
     * 获得静置任务信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 静置任务信息列表
     */
    List<RestingTaskDO> getRestingTaskList(RestingTaskExportReqVO exportReqVO);

    /**
     * 静置完成执行出库任务
     */
    void executeTask();

}
