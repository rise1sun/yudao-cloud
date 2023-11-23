package cn.iocoder.yudao.module.wms.enums.api.tray.dto;

import cn.iocoder.yudao.module.wms.enums.api.barcode.dto.BatteryinfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author jiangfeng
 * @date 2023/8/9
 */
@Data
@Schema
public class GroupTrayReqDTO {

    @Schema(description = "托盘号", example = "T00001")
    @NotNull
    private String tray;

    @NotNull
    @Schema(description="电芯条码列表")
    private List<BatteryinfoDTO> barcodes;

}
