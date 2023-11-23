package cn.iocoder.yudao.module.wms.enums.api.strategy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author jiangfeng
 * @date 2023/7/19
 */
@Component
@Data
@Schema
public class FormulaItemStrategyDTO {
    @Schema(description = "托盘号")
    private String trayNo;

    @Schema(description = "区域")
    private String area;

    @Schema(description = "库位")
    private String storage;

    @Schema(description = "静置时间")
    private String restingTime;

    @Schema(description = "状态")
    private Integer status;
}
