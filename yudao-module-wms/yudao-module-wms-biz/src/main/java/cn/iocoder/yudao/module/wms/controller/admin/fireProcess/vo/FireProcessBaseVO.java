package cn.iocoder.yudao.module.wms.controller.admin.fireProcess.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
* 消防任务 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class FireProcessBaseVO {

    @Schema(description = "区域")
    private String area;

    @Schema(description = "起始位置")
    private String startStorage;

    @Schema(description = "报警原因")
    private String content;

    @Schema(description = "消防任务状态  0未处理 1已处理")
    private Integer state;

}
