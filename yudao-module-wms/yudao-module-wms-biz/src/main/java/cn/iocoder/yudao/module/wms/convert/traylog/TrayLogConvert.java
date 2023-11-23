package cn.iocoder.yudao.module.wms.convert.traylog;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.wms.controller.admin.traylog.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.traylog.TrayLogDO;

/**
 * wms托盘日志记录 Convert
 *
 * @author 管理员
 */
@Mapper
public interface TrayLogConvert {

    TrayLogConvert INSTANCE = Mappers.getMapper(TrayLogConvert.class);

    TrayLogDO convert(TrayLogCreateReqVO bean);

    TrayLogDO convert(TrayLogUpdateReqVO bean);

    TrayLogRespVO convert(TrayLogDO bean);

    List<TrayLogRespVO> convertList(List<TrayLogDO> list);

    @Mapping(target = "TrayLogRespVO.createTime", expression = "java(toDate(TrayLogDO.createTime))")
    PageResult<TrayLogRespVO> convertPage(PageResult<TrayLogDO> page);

    List<TrayLogExcelVO> convertList02(List<TrayLogDO> list);

    default Date toDate(LocalDateTime dateTime) {
        //LocalDateTime adjustedDateTime = dateTime.minusHours(8); // 减去 8 小时
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
