package cn.iocoder.yudao.module.wms.controller.admin.formulaitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工艺流程节点 Excel 导出 Request VO")
@Data
public class FormulaItemExportReqVO {

    @Schema(description = "序号")
    private Integer serialNumber;

    @Schema(description = "工艺流程编码")
    private String formulaId;

    @Schema(description = "配方名称    ")
    private String name;

    @Schema(description = "工艺节点编码")
    private String code;

    @Schema(description = "配方静置时长")
    private String restingTime;

    @Schema(description = "状态")
    private Byte status;

    @Schema(description = "规则表id")
    private String ruleId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

    @Schema(description = "库区")
    private String area;

    @Schema(description = "复测次数")
    private Integer retestNumber;

    @Schema(description = "设备开关")
    private Byte equipmentSwitch;

    @Schema(description = "任务步骤")
    private String steps;

}
