package cn.iocoder.yudao.module.wms.enums.api.tray.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author jiangfeng
 * @date 2023/8/1
 */
@Data
@Schema(description = "RPC 服务 - 托盘 Request DTO")
public class TrayDTO{

    @Schema(description = "托盘号", example = "T00001")
    @NotNull
    private String tray;

    @Schema(description = "库位", example = "CW-01-01-A")
    @NotNull
    private String storage;

}
