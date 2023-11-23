package cn.iocoder.yudao.module.wms.controller.admin.barcode.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Schema(description = "管理后台 - 条码 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BarcodeRespVO extends BarcodeBaseVO {

    @Schema(description = "条码id", required = true)
    private Long id;

    @Schema(description = "创建时间", required = true)
    private Date createTime;

}
