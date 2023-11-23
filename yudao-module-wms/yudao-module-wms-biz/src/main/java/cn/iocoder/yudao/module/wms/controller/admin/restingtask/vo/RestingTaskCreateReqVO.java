package cn.iocoder.yudao.module.wms.controller.admin.restingtask.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description="管理后台 - 静置任务信息创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestingTaskCreateReqVO extends RestingTaskBaseVO {

}
