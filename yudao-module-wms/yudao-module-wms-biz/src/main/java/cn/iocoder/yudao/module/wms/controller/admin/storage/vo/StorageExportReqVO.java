package cn.iocoder.yudao.module.wms.controller.admin.storage.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 库位 Excel 导出 Request VO")
@Data
public class StorageExportReqVO {

    @Schema(description = "区域ID")
    private Long areaId;

    @Schema(description = "库位编号")
    private String code;

    @Schema(description = "库位名称")
    private String name;

    @Schema(description = "状态")
    private Byte status;

    @Schema(description = "有无货物")
    private Byte hasGood;

    @Schema(description = "有无消防通道")
    private Byte hasFireChannel;

    @Schema(description = "特殊库位")
    private Byte specialStorage;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

}
