package cn.iocoder.yudao.module.wms.controller.admin.tray.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.Date;

@Schema(description="管理后台 - 托盘 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrayRespVO extends TrayBaseVO {

    @Schema(description = "托盘id", required = true)
    private Long id;

    @Schema(description = "创建时间", required = true)
    private Date createTime;

}
