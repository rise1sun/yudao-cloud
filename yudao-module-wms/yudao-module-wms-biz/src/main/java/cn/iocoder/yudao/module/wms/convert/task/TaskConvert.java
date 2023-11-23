package cn.iocoder.yudao.module.wms.convert.task;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.wms.controller.admin.task.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.task.TaskDO;

/**
 * 任务管理 Convert
 *
 * @author 管理员
 */
@Mapper
public interface TaskConvert {

    TaskConvert INSTANCE = Mappers.getMapper(TaskConvert.class);

    TaskDO convert(TaskCreateReqVO bean);

    TaskDO convert(TaskUpdateReqVO bean);

    TaskRespVO convert(TaskDO bean);

    List<TaskRespVO> convertList(List<TaskDO> list);

    @Mapping(target = "TaskRespVO.createTime", expression = "java(toDate(TaskDO.createTime))")
    PageResult<TaskRespVO> convertPage(PageResult<TaskDO> page);

    List<TaskExcelVO> convertList02(List<TaskDO> list);

    default Date toDate(LocalDateTime dateTime) {
        //LocalDateTime adjustedDateTime = dateTime.minusHours(8); // 减去 8 小时
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
