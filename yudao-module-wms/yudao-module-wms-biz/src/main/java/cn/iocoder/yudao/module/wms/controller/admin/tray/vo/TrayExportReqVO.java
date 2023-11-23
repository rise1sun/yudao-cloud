package cn.iocoder.yudao.module.wms.controller.admin.tray.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 托盘 Excel 导出 Request VO")
@Data
public class TrayExportReqVO {

    @Schema(description = "托盘类型")
    private Byte type;

    @Schema(description = "最大绑定数量")
    private Integer maxBindNumber;

    @Schema(description = "最大使用次数")
    private Integer maxUseNumber;

    @Schema(description = "最大使用次数")
    private Integer useNumber;

    @Schema(description = "托盘状态")
    private Byte status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

    @Schema(description = "托盘编号")
    private String trayNo;

    @Schema(description = "测试托盘标识")
    private Byte isTestTray;

    @Schema(description = "库位名称")
    private String storageName;

    @Schema(description= "订单任务id")
    private Integer orderTaskId;


}
