package cn.iocoder.yudao.module.wms.dal.mysql.task;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wms.dal.dataobject.task.TaskDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wms.controller.admin.task.vo.*;

/**
 * 任务管理 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TaskMapper extends BaseMapperX<TaskDO> {

    default PageResult<TaskDO> selectPage(TaskPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TaskDO>()
                .eqIfPresent(TaskDO::getTrayNo, reqVO.getTrayNo())
                .eqIfPresent(TaskDO::getStartStorage, reqVO.getStartStorage())
                .eqIfPresent(TaskDO::getStartArea, reqVO.getStartArea())
                .eqIfPresent(TaskDO::getEndStorage, reqVO.getEndStorage())
                .eqIfPresent(TaskDO::getEndArea, reqVO.getEndArea())
                .eqIfPresent(TaskDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TaskDO::getSleepTime, reqVO.getSleepTime())
                .eqIfPresent(TaskDO::getType, reqVO.getType())
                .eqIfPresent(TaskDO::getWcsTaskId, reqVO.getWcsTaskId())
                .betweenIfPresent(TaskDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TaskDO::getId));
    }

    default List<TaskDO> selectList(TaskExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TaskDO>()
                .eqIfPresent(TaskDO::getTrayNo, reqVO.getTrayNo())
                .eqIfPresent(TaskDO::getStartStorage, reqVO.getStartStorage())
                .eqIfPresent(TaskDO::getStartArea, reqVO.getStartArea())
                .eqIfPresent(TaskDO::getEndStorage, reqVO.getEndStorage())
                .eqIfPresent(TaskDO::getEndArea, reqVO.getEndArea())
                .eqIfPresent(TaskDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TaskDO::getSleepTime, reqVO.getSleepTime())
                .eqIfPresent(TaskDO::getType, reqVO.getType())
                .eqIfPresent(TaskDO::getWcsTaskId, reqVO.getWcsTaskId())
                .betweenIfPresent(TaskDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TaskDO::getId));
    }

}
