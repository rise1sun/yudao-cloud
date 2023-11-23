package cn.iocoder.yudao.module.wms.controller.admin.traylog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Schema(description="管理后台 - wms托盘日志记录创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrayLogCreateReqVO extends TrayLogBaseVO {

}
