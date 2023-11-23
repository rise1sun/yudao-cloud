package cn.iocoder.yudao.module.wms.controller.app.pda.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jiangfeng
 * @date 2023/10/7
 */
@Schema(description="托盘库位校验")
@Data
public class TrayStorageVO {
    private static final long serialVersionUID =1L;

    @NotNull
    @Schema(description ="托盘号")
    private String tray;

    @NotNull
    @Schema(description ="起始库位")
    private String startStorage;

    @NotNull
    @Schema(description ="终点库位")
    private String endStorage;
}
