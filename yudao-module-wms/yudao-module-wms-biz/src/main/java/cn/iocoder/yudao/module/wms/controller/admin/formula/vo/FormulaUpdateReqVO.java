package cn.iocoder.yudao.module.wms.controller.admin.formula.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Schema(description="管理后台 - 工艺流程更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FormulaUpdateReqVO extends FormulaUpdateBaseVO {

    @Schema(description = "主键", required = true)
    @NotNull(message = "主键不能为空")
    private Integer id;

    /**
     * 工艺节点列表
     */
    @NotEmpty(message = "工艺节点列表不能为空")
    @Valid
    private List<FormulaItem> formulaItemList;
}
