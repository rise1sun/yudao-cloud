package cn.iocoder.yudao.module.wms.controller.admin.storage.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

/**
* 库位 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class StorageBaseVO {

    @Schema(description = "区域ID", required = true)
    @NotNull(message = "区域ID不能为空")
    private Long areaId;

    @Schema(description = "库位编号")
    private String code;

    @Schema(description = "库位名称")
    private String name;

    @Schema(description = "状态", required = true)
    @NotNull(message = "状态不能为空")
    private Byte status;

    @Schema(description = "有无货物", required = true)
    @NotNull(message = "有无货物不能为空")
    private Byte hasGood;

    @Schema(description = "有无消防通道", required = true)
    @NotNull(message = "有无消防通道不能为空")
    private Byte hasFireChannel;

    @Schema(description = "特殊库位", required = true)
    @NotNull(message = "特殊库位不能为空")
    private Byte specialStorage;

    @Schema(description = "备注")
    private String remark;

}
