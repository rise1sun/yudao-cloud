package cn.iocoder.yudao.module.wms.enums.api.tray.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jiangfeng
 * @date 2023/8/9
 */
@Data
@Schema
public class RemoveTrayReqDTO {

    @Schema(description = "托盘号", example = "T00001")
    @NotNull
    private String tray;
}
