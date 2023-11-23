package cn.iocoder.yudao.module.wms.controller.admin.timetablename.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description="管理后台 - 月份条码创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TimeTableNameCreateReqVO extends TimeTableNameBaseVO {

}
