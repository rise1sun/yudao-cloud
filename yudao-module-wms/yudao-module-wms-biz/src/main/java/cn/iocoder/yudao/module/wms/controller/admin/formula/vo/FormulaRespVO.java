package cn.iocoder.yudao.module.wms.controller.admin.formula.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Schema(description="管理后台 - 工艺流程 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FormulaRespVO extends FormulaUpdateBaseVO {

    @Schema(description = "主键", required = true)
    private Integer id;

    @Schema(description = "创建时间", required = true)
    private Date createTime;

    /**
     * 工艺节点列表
     */
    @NotEmpty(message = "工艺节点列表不能为空")
    @Valid
    private List<FormulaItem> formulaItemList;
}
