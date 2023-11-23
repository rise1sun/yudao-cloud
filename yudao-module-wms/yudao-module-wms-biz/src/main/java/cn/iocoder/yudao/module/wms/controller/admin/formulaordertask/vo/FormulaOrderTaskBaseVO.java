package cn.iocoder.yudao.module.wms.controller.admin.formulaordertask.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
* 工艺流程订单任务 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class FormulaOrderTaskBaseVO {

    @Schema(description = "工艺流程id")
    private Integer formulaId;

    @Schema(description = "工艺流程名称")
    private String formulaName;

    @Schema(description = "工艺流程节点id")
    private Integer formulaItemId;

    @Schema(description = "工艺流程节点名称")
    private String formulaItemName;

    @Schema(description = "任务订单号")
    private String orderTask;

    @Schema(description = "托盘号")
    private String trayNo;

    @Schema(description = "库位id")
    private Integer storageId;

    @Schema(description = "库位名称")
    private String storageName;

}
