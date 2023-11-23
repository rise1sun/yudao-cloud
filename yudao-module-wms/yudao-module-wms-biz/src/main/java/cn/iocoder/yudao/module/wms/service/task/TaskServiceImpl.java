package cn.iocoder.yudao.module.wms.service.task;

import cn.iocoder.yudao.module.wms.mq.message.TaskSendMessage;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.wms.controller.admin.task.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.task.TaskDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.wms.convert.task.TaskConvert;
import cn.iocoder.yudao.module.wms.dal.mysql.task.TaskMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wms.enums.ErrorCodeConstants.*;

/**
 * 任务管理 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;

    @Override
    public Integer createTask(TaskCreateReqVO createReqVO) {
        // 插入
        TaskDO task = TaskConvert.INSTANCE.convert(createReqVO);
        taskMapper.insert(task);
        // 返回
        return task.getId();
    }

    @Override
    public void updateTask(TaskUpdateReqVO updateReqVO) {
        // 校验存在
        validateTaskExists(updateReqVO.getId());
        // 更新
        TaskDO updateObj = TaskConvert.INSTANCE.convert(updateReqVO);
        taskMapper.updateById(updateObj);
    }

    @Override
    public void deleteTask(Integer id) {
        // 校验存在
        validateTaskExists(id);
        // 删除
        taskMapper.deleteById(id);
    }

    private void validateTaskExists(Integer id) {
        if (taskMapper.selectById(id) == null) {
            throw exception(TASK_NOT_EXISTS);
        }
    }

    @Override
    public TaskDO getTask(Integer id) {
        return taskMapper.selectById(id);
    }

    @Override
    public List<TaskDO> getTaskList(Collection<Integer> ids) {
        return taskMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<TaskDO> getTaskPage(TaskPageReqVO pageReqVO) {
        return taskMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TaskDO> getTaskList(TaskExportReqVO exportReqVO) {
        return taskMapper.selectList(exportReqVO);
    }

    @Override
    public TaskDO executeTask(Integer id) {
        return null;
    }

    @Override
    public void doSendSms(TaskSendMessage taskSendMessage) {
        //TODO 更新任务完成标志
    }

}
