package cn.iocoder.yudao.module.wms.controller.admin.batch.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

@Schema(description="管理后台 - 批次更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BatchUpdateReqVO extends BatchBaseVO {

    @Schema(description = "批次ID", required = true)
    @NotNull(message = "批次ID不能为空")
    private Long id;

}
