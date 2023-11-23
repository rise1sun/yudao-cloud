package cn.iocoder.yudao.module.wms.controller.admin.task.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

/**
* 任务管理 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class TaskBaseVO {

    @NotNull(message = "托盘号不能为空")
    @Schema(description = "托盘号")
    private String trayNo;

    @NotNull(message = "起始位置不能为空")
    @Schema(description = "起始位置")
    private String startStorage;

    @Schema(description = "起始区域")
    private String startArea;

    @NotNull(message = "结束位置不能为空")
    @Schema(description = "结束位置")
    private String endStorage;

    @Schema(description = "结束区域")
    private String endArea;

    @Schema(description = "任务状态", required = true)
    @NotNull(message = "任务状态不能为空")
    private Byte status;

    @Schema(description = "静置时长", required = true)
    //@NotNull(message = "静置时长不能为空")
    private Integer sleepTime;

    @Schema(description = "任务类型", required = true)
    @NotNull(message = "任务类型不能为空")
    private Byte type;

    @Schema(description = "wcs调度号", required = true)
    //@NotNull(message = "wcs调度号不能为空")
    private Integer wcsTaskId;

}
