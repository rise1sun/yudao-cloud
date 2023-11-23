package cn.iocoder.yudao.module.wms.controller.admin.formula.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description="管理后台 - 工艺流程 Excel 导出 Request VO")
@Data
public class FormulaExportReqVO {

    @Schema(description = "工艺流程编码")
    private String code;

    @Schema(description = "工艺流程名称")
    private String name;

    @Schema(description = "状态")
    private Byte status;

    @Schema(description = "类型")
    private Byte type;

    @Schema(description = "超时时间")
    private String timeOut;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

}
