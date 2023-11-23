package cn.iocoder.yudao.module.wms.enums.api.barcode.dto;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author jiangfeng
 * @date 2023/6/13
 */

@Schema(defaultValue = "RPC 服务 - 第三方系统创建条码 Request DTO")
@Data
public class BarcodeCreateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "托盘号",  requiredMode = Schema.RequiredMode.REQUIRED, example = "T00001")
    private String tray;

    @NotNull
    @Schema(description = "条码号", requiredMode = Schema.RequiredMode.REQUIRED, example = "['202306130001','202306130001']")
    private List<String> barcodeLists;

    @Schema(description = "数据来源", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer dataSource;

    @Schema(description = "通道号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer channelIndex;

    public BarcodeCreateDTO() {

    }
}
