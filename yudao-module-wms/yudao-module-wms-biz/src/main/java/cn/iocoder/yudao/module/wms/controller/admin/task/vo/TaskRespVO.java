package cn.iocoder.yudao.module.wms.controller.admin.task.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Schema(description="管理后台 - 任务管理 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskRespVO extends TaskBaseVO {

    @Schema(description = "主键", required = true)
    private Integer id;

    @Schema(description = "创建时间", required = true)
    private Date createTime;

}
