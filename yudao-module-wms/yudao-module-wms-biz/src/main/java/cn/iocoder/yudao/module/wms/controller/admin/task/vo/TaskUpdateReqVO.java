package cn.iocoder.yudao.module.wms.controller.admin.task.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

@Schema(description="管理后台 - 任务管理更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskUpdateReqVO extends TaskBaseVO {

    @Schema(description = "主键", required = true)
    @NotNull(message = "主键不能为空")
    private Integer id;

}
