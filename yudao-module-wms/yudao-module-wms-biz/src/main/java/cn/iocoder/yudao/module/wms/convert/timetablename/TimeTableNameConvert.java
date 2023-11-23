package cn.iocoder.yudao.module.wms.convert.timetablename;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.wms.controller.admin.timetablename.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.timetablename.TimeTableNameDO;

/**
 * 月份条码 Convert
 *
 * @author 管理员
 */
@Mapper
public interface TimeTableNameConvert {

    TimeTableNameConvert INSTANCE = Mappers.getMapper(TimeTableNameConvert.class);

    TimeTableNameDO convert(TimeTableNameCreateReqVO bean);

    TimeTableNameDO convert(TimeTableNameUpdateReqVO bean);

    TimeTableNameRespVO convert(TimeTableNameDO bean);

    List<TimeTableNameRespVO> convertList(List<TimeTableNameDO> list);

    PageResult<TimeTableNameRespVO> convertPage(PageResult<TimeTableNameDO> page);

    List<TimeTableNameExcelVO> convertList02(List<TimeTableNameDO> list);

}
