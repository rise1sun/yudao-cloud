package cn.iocoder.yudao.module.wms.controller.admin.formulaordertask.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工艺流程订单任务 Excel 导出 Request VO")
@Data
public class FormulaOrderTaskExportReqVO {

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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

}
