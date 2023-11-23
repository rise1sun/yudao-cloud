package cn.iocoder.yudao.module.wms.controller.admin.formula.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
* 工艺流程 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 * @author admin
 */
@Data
public class FormulaUpdateBaseVO {

    @Schema(description = "工艺流程编码")
    private String code;

    @Schema(description = "工艺流程名称")
    private String name;

    @Schema(description = "状态", required = true)
    @NotNull(message = "状态不能为空")
    private Byte status;

    @Schema(description = "类型", required = true)
    @NotNull(message = "类型不能为空")
    private Byte type;

    @Schema(description = "超时时间")
    private String timeOut;


    @Data
    public static class FormulaItem {
        @Schema(description = "id")
        private Integer id;

        @Schema(description = "序号")
        private Integer serialNumber;

        @Schema(description = "工艺流程编码")
        private String formulaId;

        @Schema(description = "配方名称")
        private String name;

        @Schema(description = "工艺节点编码")
        private String code;

        @Schema(description = "配方静置时长")
        private String restingTime;

        @Schema(description = "状态", required = true)
        @NotNull(message = "状态不能为空")
        private Byte status;

        @Schema(description = "规则表id")
        private String ruleId;

        @Schema(description = "库区")
        private String area;

        @Schema(description = "复测次数")
        private Integer retestNumber;

        @Schema(description = "设备开关", required = true)
        @NotNull(message = "设备开关")
        private Byte equipmentSwitch;

        @Schema(description = "任务步骤")
        private String steps;

    }

}
