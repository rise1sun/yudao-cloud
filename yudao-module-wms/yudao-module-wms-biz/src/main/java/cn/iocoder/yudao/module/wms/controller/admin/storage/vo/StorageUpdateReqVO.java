package cn.iocoder.yudao.module.wms.controller.admin.storage.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

@Schema(description="管理后台 - 库位更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StorageUpdateReqVO extends StorageBaseVO {

    @Schema(description = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

}
