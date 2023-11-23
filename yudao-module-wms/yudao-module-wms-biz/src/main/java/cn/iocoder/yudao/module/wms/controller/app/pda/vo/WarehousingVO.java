package cn.iocoder.yudao.module.wms.controller.app.pda.vo;

import cn.iocoder.yudao.module.wms.enums.api.barcode.dto.BatteryinfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author jiangfeng
 * @date 2023/7/18
 */
@Schema(description="通用入库方法请求参数")
@Data
public class WarehousingVO {
    private static final long serialVersionUID =1L;

    @Schema(description ="托盘号")
    private String trayNo;

    @Schema(description ="托盘Id")
    private Long trayId;

    @Schema(description ="条码表名")
    private String barcodeTableName;

    @Schema(description ="电芯条码列表")
    private List<BatteryinfoDTO> barcodes;

    @Schema(description ="起始位置")
    private String device;

    @Schema(description ="目标位置")
    private String enddevice;

    @Schema(description ="使用次数")
    private Integer useNumber;

    @Schema(description ="使用人")
    private String userName;

    @Schema(description ="条码状态")
    private Integer barcodeStatus;

    @Schema(description ="任务订单id")
    private Integer orderTaskId;

    @Schema(description ="是否测试托盘")
    private Byte isTestTray;

    @Schema(description ="入库类型")
    private Byte type;
}
