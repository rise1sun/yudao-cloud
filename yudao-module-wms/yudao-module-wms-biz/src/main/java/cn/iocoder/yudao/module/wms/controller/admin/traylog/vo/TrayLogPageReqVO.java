package cn.iocoder.yudao.module.wms.controller.admin.traylog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description="管理后台 - wms托盘日志记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrayLogPageReqVO extends PageParam {

    @Schema(description = "托盘号")
    private String trayNo;

    @Schema(description = "日志类型")
    private Byte type;

    @Schema(description = "业务类型")
    private Byte serviceType;

    @Schema(description = "wcs调度任务号")
    private String dispatchTaskNumber;

    @Schema(description = "0失败 1成功")
    private Byte result;

    @Schema(description = "接口调用方")
    private String caller;

    @Schema(description = "接口被调用方")
    private String callee;

    @Schema(description = "方法名称")
    private String methodName;

    @Schema(description = "请求参数")
    private String requestParameters;

    @Schema(description = "返回结果")
    private String responseResults;

    @Schema(description = "起始库位")
    private String startStorage;

    @Schema(description = "起始库区")
    private String startArea;

    @Schema(description = "结束库位")
    private String endStorage;

    @Schema(description = "结束库区")
    private String endArea;

    @Schema(description = "异常信息")
    private String errorInfo;

    @Schema(description = "当前条码信息")
    private String barcodes;

    @Schema(description = "条码数量")
    private Integer barcodeNumber;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

    @Schema(description = "托盘类型")
    private Integer trayType;

    @Schema(description = "条码状态")
    private String barcodeStatus;

    @Schema(description = "托盘运行状态")
    private Integer trayRunStatus;

}
