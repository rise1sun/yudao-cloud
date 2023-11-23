package cn.iocoder.yudao.module.wms.controller.admin.formula.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Schema(description="管理后台 - 工艺流程创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FormulaCreateReqVO extends FormulaBaseVO {

    /**
     * 工艺节点列表
     */
    @NotEmpty(message = "工艺节点列表不能为空")
    @Valid
    private List<FormulaItem> formulaItemList;
}
