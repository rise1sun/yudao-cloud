package cn.iocoder.yudao.module.wms.enums.api.barcode.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jiangfeng
 * @date 2023/7/13
 */
@Schema(description = "RPC 服务 - 电芯 Request DTO")
@Data
public class BatteryinfoDTO {

    @NotNull
    @Schema(description = "条码号")
    private String batteriesBarcode;

    @Schema(description = "通道号")
    private String id;
}
