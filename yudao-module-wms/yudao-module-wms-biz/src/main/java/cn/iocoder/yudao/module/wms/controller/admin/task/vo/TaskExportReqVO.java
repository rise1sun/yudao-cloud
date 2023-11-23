package cn.iocoder.yudao.module.wms.controller.admin.task.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 任务管理 Excel 导出 Request VO")
@Data
public class TaskExportReqVO {

    @Schema(description = "托盘号")
    private String trayNo;

    @Schema(description = "起始位置")
    private String startStorage;

    @Schema(description = "起始区域")
    private String startArea;

    @Schema(description = "结束位置")
    private String endStorage;

    @Schema(description = "结束区域")
    private String endArea;

    @Schema(description = "任务状态")
    private Byte status;

    @Schema(description = "静置时长")
    private Integer[] sleepTime;

    @Schema(description = "任务类型")
    private Byte type;

    @Schema(description = "wcs调度号")
    private Integer wcsTaskId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

}
