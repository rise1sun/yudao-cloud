package cn.iocoder.yudao.module.wms.controller.admin.fireProcess.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Schema(description="管理后台 - 消防任务 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FireProcessRespVO extends FireProcessBaseVO {

    @Schema(description = "主键", required = true)
    private Long id;

    @Schema(description = "创建时间", required = true)
    private Date createTime;

}
