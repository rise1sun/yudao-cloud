package cn.iocoder.yudao.module.wms.controller.admin.task.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description="管理后台 - 任务管理创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskCreateReqVO extends TaskBaseVO {

}
