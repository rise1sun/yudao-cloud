package cn.iocoder.yudao.module.wms.controller.admin.batch.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

/**
* 批次 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Schema
@Data
public class BatchBaseVO {

    @Schema(description = "工艺流程ID")
    private Long formulaId;

    @Schema(description = "批次名称", required = true)
    @NotNull(message = "批次名称不能为空")
    private String name;

    @Schema(description = "型号")
    private String spec;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "批次状态", required = true)
    @NotNull(message = "批次状态不能为空")
    private Byte status;

    @Schema(description = "默认提醒次数", required = true)
    @NotNull(message = "默认提醒次数不能为空")
    private Integer number;

}
