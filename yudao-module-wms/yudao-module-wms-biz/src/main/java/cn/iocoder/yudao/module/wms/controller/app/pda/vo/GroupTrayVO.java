package cn.iocoder.yudao.module.wms.controller.app.pda.vo;

import cn.iocoder.yudao.module.wms.enums.api.barcode.dto.BatteryinfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author jiangfeng
 * @date 2023/7/13
 */
@Schema(description="RPC 服务 - PDA组盘 Request DTO")
@Data
public class GroupTrayVO implements Serializable {

    private static final long serialVersionUID =1L;

    @NotNull
    @Schema(description = "托盘号")
    private String tray;

    @Schema(description ="位置编码")
    private String device;

    @NotNull
    @Schema(description ="目的位置编码")
    private String enddevice;

    @NotNull
    @Schema(description ="电芯条码列表")
    private List<BatteryinfoDTO> barcodes;

    @Schema(description ="用户姓名")
    private String usercode;

}
