package cn.iocoder.yudao.module.wms.controller.admin.barcode.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author jiangfeng
 * @date 2023/7/11
 */
@Data
@Schema(description = "管理后台 - 条码删除 Request VO")
@ToString(callSuper = true)
public class BarcodeDeleteReqVO{

    @NotNull
    @Schema(description = "条码号")
    private String barcode;
}
