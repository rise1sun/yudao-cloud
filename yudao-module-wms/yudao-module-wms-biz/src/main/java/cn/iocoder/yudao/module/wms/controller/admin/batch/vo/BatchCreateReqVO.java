package cn.iocoder.yudao.module.wms.controller.admin.batch.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Schema(description = "管理后台 - 批次创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BatchCreateReqVO extends BatchBaseVO {

}
