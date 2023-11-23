package cn.iocoder.yudao.module.wms.controller.admin.restingtask.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 静置任务信息 Excel 导出 Request VO")
@Data
public class RestingTaskExportReqVO {

    @Schema(description = "托盘号")
    private String trayNo;

    @Schema(description = "静置区域")
    private String area;

    @Schema(description = "静置库位")
    private String storage;

    @Schema(description = "静置时间")
    private String restingTime;

    @Schema(description = "任务订单号")
    private String orderTask;

    @Schema(description = "状态（0静置中 1静置完成未调度 2静置完成已调度 3调度后wcs异常托盘还在库）")
    private Byte status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

}
