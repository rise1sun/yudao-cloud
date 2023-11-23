package cn.iocoder.yudao.module.wms.controller.admin.traylog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.Date;

@Schema(description="管理后台 - wms托盘日志记录 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrayLogRespVO extends TrayLogBaseVO {

    @Schema(description = "主键", required = true)
    private Integer id;

    @Schema(description = "创建时间", required = true)
    private Date createTime;

}
