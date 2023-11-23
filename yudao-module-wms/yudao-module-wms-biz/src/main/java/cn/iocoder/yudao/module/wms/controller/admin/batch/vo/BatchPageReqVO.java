package cn.iocoder.yudao.module.wms.controller.admin.batch.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 批次分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BatchPageReqVO extends PageParam {

    @Schema(description = "工艺流程ID")
    private Long formulaId;

    @Schema(description = "批次名称")
    private String name;

    @Schema(description = "型号")
    private String spec;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "批次状态（0正常 1停用 2上料绑定批次）")
    private Byte status;

    @Schema(description = "默认提醒次数")
    private Integer number;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

}
