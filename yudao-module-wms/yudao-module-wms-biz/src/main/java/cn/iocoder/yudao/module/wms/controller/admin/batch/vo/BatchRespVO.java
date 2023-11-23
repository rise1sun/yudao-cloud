package cn.iocoder.yudao.module.wms.controller.admin.batch.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.Date;

@Schema(description="管理后台 - 批次 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BatchRespVO extends BatchBaseVO {

    @Schema(description = "批次ID", required = true)
    private Long id;

    @Schema(description = "创建时间", required = true)
    private Date createTime;

}
