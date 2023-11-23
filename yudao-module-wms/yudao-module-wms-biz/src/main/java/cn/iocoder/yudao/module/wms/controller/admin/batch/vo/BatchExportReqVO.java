package cn.iocoder.yudao.module.wms.controller.admin.batch.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.module.wms.enums.DictTypeConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 批次 Excel 导出 Request VO")
@Data
public class BatchExportReqVO {

    @Schema(description = "工艺流程ID")
    private Long formulaId;

    @Schema(description = "工艺流程")
    private String formulaName;

    @Schema(description = "批次名称")
    private String name;

    @Schema(description = "型号")
    private String spec;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "批次状态")
    private Byte status;

    @Schema(description = "默认提醒次数")
    private Integer number;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

}
