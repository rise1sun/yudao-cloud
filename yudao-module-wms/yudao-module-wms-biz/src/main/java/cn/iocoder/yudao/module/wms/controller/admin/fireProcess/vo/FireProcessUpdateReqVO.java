package cn.iocoder.yudao.module.wms.controller.admin.fireProcess.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

@Schema(description="管理后台 - 消防任务更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FireProcessUpdateReqVO extends FireProcessBaseVO {

    @Schema(description = "主键", required = true)
    @NotNull(message = "主键不能为空")
    private Long id;

}
