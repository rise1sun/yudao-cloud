package cn.iocoder.yudao.module.wms.controller.admin.tray.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

/**
* 托盘 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class TrayBaseVO {

    @Schema(description = "托盘类型", required = true)
    @NotNull(message = "托盘类型不能为空")
    private Byte type;

    @Schema(description = "最大绑定数量", required = true)
    @NotNull(message = "最大绑定数量不能为空")
    private Integer maxBindNumber;

    @Schema(description = "最大使用次数", required = true)
    @NotNull(message = "最大使用次数不能为空")
    private Integer maxUseNumber;

    @Schema(description = "使用次数", required = true)
//    @NotNull(message = "使用次数不能为空")
    private Integer useNumber;

    @Schema(description = "托盘状态", required = true)
    @NotNull(message = "托盘状态不能为空")
    private Byte status;

    @Schema(description = "托盘编号", required = true)
    @NotNull(message = "托盘编号不能为空")
    private String trayNo;

    @Schema(description = "测试托盘标识", required = true)
    @NotNull(message = "测试托盘标识不能为空")
    private Byte isTestTray;

    @Schema(description = "库位id", required = true)
    private Integer storageId;

    @Schema(description = "库位名称", required = true)
    private String storageName;

    @Schema(description = "订单任务id", required = true)
    private Integer orderTaskId;

}
