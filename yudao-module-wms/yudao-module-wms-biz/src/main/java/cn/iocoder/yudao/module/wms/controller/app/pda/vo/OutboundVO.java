package cn.iocoder.yudao.module.wms.controller.app.pda.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author jiangfeng
 * @date 2023/7/26
 */
@Component
@Data
public class OutboundVO implements Serializable {
    private static final long serialVersionUID =1L;

    @NotNull
    @Schema(description ="托盘号")
    private String trayNO;

    @Schema(description ="操作人名字")
    private String userName;

    @Schema(description ="流程结束标识")
    private Boolean formulaItemEndFlag;

    @NotNull
    @Schema(description ="起始位置")
    private String startStorage;

    @Schema(description ="起始区域")
    private String startArea;

    @Schema(description ="目标区域")
    private String endArea;

    @NotNull
    @Schema(description ="目标位置")
    private String endStorage;

    @Schema(description ="电芯条码")
    private String barcodes;

    @Schema(description ="电芯数量")
    private Integer barcodeNumber;

    private Integer trayType;

    @Schema(description ="电芯状态")
    private String barcodeStatus;

    @Schema(description ="订单任务id")
    private Integer orderTaskId;

    @Schema(description ="是否测试托盘")
    private Byte isTestTray;

}
