package cn.iocoder.yudao.module.wms.controller.admin.tray.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description="管理后台 - 托盘创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrayCreateReqVO extends TrayBaseVO {

}
