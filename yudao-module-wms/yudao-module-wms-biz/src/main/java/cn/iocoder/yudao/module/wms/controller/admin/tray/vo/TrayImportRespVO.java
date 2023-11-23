package cn.iocoder.yudao.module.wms.controller.admin.tray.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author jiangfeng
 * @version 1.0
 * @date 2023/4/11 11:52
 */
@Schema(description="管理后台 - 托盘导入 Response VO")
@Data
@Builder
public class TrayImportRespVO {
    @Schema(description = "创建成功的托盘数组", required = true)
    private List<String> createTrayNo;

    @Schema(description = "更新成功的托盘数组", required = true)
    private List<String> updateTrayNo;

    @Schema(description = "导入失败的托盘集合", required = true)
    private Map<String, String> failureTaryNo;
}
