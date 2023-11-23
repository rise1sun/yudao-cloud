package cn.iocoder.yudao.module.wms.controller.admin.formulaordertask.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Schema(description="管理后台 - 工艺流程订单任务 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FormulaOrderTaskRespVO extends FormulaOrderTaskBaseVO {

    @Schema(description = "主键", required = true)
    private Integer id;

    @Schema(description = "创建时间", required = true)
    private Date createTime;

}
