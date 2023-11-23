package cn.iocoder.yudao.module.wms.controller.admin.restingtask.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

/**
* 静置任务信息 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class RestingTaskBaseVO {

    @NotNull(message = "托盘号不能为空")
    @Schema(description = "托盘号")
    private String trayNo;

    @Schema(description = "静置区域")
    private String area;

    @NotNull(message = "静置库位不能为空")
    @Schema(description = "静置库位")
    private String storage;

    @NotNull(message = "静置时间不能为空")
    @Schema(description = "静置时间")
    private String restingTime;

    @Schema(description = "任务订单号")
    private String orderTask;

    @Schema(description = "状态", required = true)
    @NotNull(message = "状态不能为空")
    private Byte status;

}
