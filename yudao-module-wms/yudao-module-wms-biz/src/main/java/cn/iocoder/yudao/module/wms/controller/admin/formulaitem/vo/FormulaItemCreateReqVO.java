package cn.iocoder.yudao.module.wms.controller.admin.formulaitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description="管理后台 - 工艺流程节点创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FormulaItemCreateReqVO extends FormulaItemBaseVO {

}
