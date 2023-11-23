package cn.iocoder.yudao.module.wms.controller.admin.tray.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

@Schema(description="管理后台 - 托盘更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrayUpdateReqVO extends TrayBaseVO {

    @Schema(description = "托盘id", required = true)
    @NotNull(message = "托盘id不能为空")
    private Long id;

}
