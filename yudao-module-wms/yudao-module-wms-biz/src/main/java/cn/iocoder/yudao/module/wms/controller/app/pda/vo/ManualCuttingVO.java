package cn.iocoder.yudao.module.wms.controller.app.pda.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author jiangfeng
 * @date 2023/7/26
 */
@Component
@Data
public class ManualCuttingVO implements Serializable {
    private static final long serialVersionUID =1L;

    @NotNull
    @Schema(description ="托盘号")
    private String trayNO;

    @NotNull
    @Schema(description ="位置编码")
    private String storage;

    @Schema(description ="操作人名字")
    private String userName;

    @Schema(description ="流程结束标识")
    private Boolean formulaItemEndFlag;
}
