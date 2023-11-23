package cn.iocoder.yudao.module.wms.controller.admin.barcode.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 条码更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BarcodeUpdateReqVO extends BarcodeBaseVO {

    @Schema(description = "条码id", required = true)
    @NotNull(message = "条码id不能为空")
    private Long id;

    @Schema(description = "条码创建时间")
   /* @NotNull(message = "条码创建时间不能为空")*/
    private Long createTime;

}
