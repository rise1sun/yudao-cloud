package cn.iocoder.yudao.module.wms.service.task;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wms.controller.admin.task.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.task.TaskDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.wms.mq.message.TaskSendMessage;

/**
 * 任务管理 Service 接口
 *
 * @author 管理员
 */
public interface TaskService {

    /**
     * 创建任务管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createTask(@Valid TaskCreateReqVO createReqVO);

    /**
     * 更新任务管理
     *
     * @param updateReqVO 更新信息
     */
    void updateTask(@Valid TaskUpdateReqVO updateReqVO);

    /**
     * 删除任务管理
     *
     * @param id 编号
     */
    void deleteTask(Integer id);

    /**
     * 获得任务管理
     *
     * @param id 编号
     * @return 任务管理
     */
    TaskDO getTask(Integer id);

    /**
     * 获得任务管理列表
     *
     * @param ids 编号
     * @return 任务管理列表
     */
    List<TaskDO> getTaskList(Collection<Integer> ids);

    /**
     * 获得任务管理分页
     *
     * @param pageReqVO 分页查询
     * @return 任务管理分页
     */
    PageResult<TaskDO> getTaskPage(TaskPageReqVO pageReqVO);

    /**
     * 获得任务管理列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 任务管理列表
     */
    List<TaskDO> getTaskList(TaskExportReqVO exportReqVO);

    /**
     * 执行任务
     * @param id
     * @return
     */
    TaskDO executeTask(Integer id);

    void doSendSms(TaskSendMessage taskSendMessage);
}
