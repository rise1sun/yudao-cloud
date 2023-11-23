package cn.iocoder.yudao.module.wms.controller.admin.formulaitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Schema(description="管理后台 - 工艺流程节点 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FormulaItemRespVO extends FormulaItemBaseVO {

    @Schema(description = "主键", required = true)
    private Integer id;

    @Schema(description = "创建时间", required = true)
    private Date createTime;

}
