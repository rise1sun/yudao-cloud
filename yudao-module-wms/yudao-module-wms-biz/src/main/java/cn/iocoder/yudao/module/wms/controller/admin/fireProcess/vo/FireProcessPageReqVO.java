package cn.iocoder.yudao.module.wms.controller.admin.fireProcess.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;


import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description="管理后台 - 消防任务分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FireProcessPageReqVO extends PageParam {

    @Schema(description = "区域")
    private String area;

    @Schema(description = "起始位置")
    private String startStorage;

    @Schema(description = "报警原因")
    private String content;

    @Schema(description = "消防任务状态  0未处理 1已处理")
    private Integer state;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

}
