package cn.iocoder.yudao.module.wms.convert.restingtask;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.wms.enums.api.strategy.dto.FormulaItemStrategyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.wms.controller.admin.restingtask.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.restingtask.RestingTaskDO;

/**
 * 静置任务信息 Convert
 *
 * @author 管理员
 */
@Mapper
public interface RestingTaskConvert {

    RestingTaskConvert INSTANCE = Mappers.getMapper(RestingTaskConvert.class);

    RestingTaskDO convert(RestingTaskCreateReqVO bean);

    RestingTaskDO convert(RestingTaskUpdateReqVO bean);

    RestingTaskRespVO convert(RestingTaskDO bean);

    RestingTaskCreateReqVO convert(FormulaItemStrategyDTO bean);

    List<RestingTaskRespVO> convertList(List<RestingTaskDO> list);

    @Mapping(target = "RestingTaskRespVO.createTime", expression = "java(toDate(RestingTaskDO.createTime))")
    PageResult<RestingTaskRespVO> convertPage(PageResult<RestingTaskDO> page);

    List<RestingTaskExcelVO> convertList02(List<RestingTaskDO> list);

    default Date toDate(LocalDateTime dateTime) {
        //LocalDateTime adjustedDateTime = dateTime.minusHours(8); // 减去 8 小时
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
