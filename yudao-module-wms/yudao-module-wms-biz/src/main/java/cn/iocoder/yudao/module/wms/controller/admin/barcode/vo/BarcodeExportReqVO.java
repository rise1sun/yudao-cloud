package cn.iocoder.yudao.module.wms.controller.admin.barcode.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 条码 Excel 导出 Request VO")
@Data
public class BarcodeExportReqVO {

    @Schema(description = "条码号")
    private String barcode;

    @Schema(description = "数据来源")
    private String dataSource;

    @Schema(description = "规格")
    private String spec;

    @Schema(description = "库区")
    private String area;

    @Schema(description = "库位")
    private String storage;

    @Schema(description = "条码状态")
    private Byte barcodeStatus;

    @Schema(description = "当前工艺流程编号")
    private Integer formulaItemId;

    @Schema(description = "ng点位")
    private String ngSite;

    @Schema(description = "复测记录标记")
    private Integer retest;

    @Schema(description = "工艺流程id")
    private Integer formulaId;

    @Schema(description = "托盘条码")
    private String tray;

    @Schema(description = "通道号")
    private Integer channelIndex;

    @Schema(description = "批次id")
    private Integer batchId;

    @Schema(description = "关联电芯号")
    private Integer batteryId;

    @Schema(description = "设备名")
    private String cabName;

    @Schema(description = "是否锁定，0未锁定，1锁定")
    private Boolean isLocked;

    @Schema(description = "MES生产订单号")
    private String mesProductionNumber;

    @Schema(description = "客户编号")
    private String customerNumber;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

    @Schema(description = "批次名称")
    private String batchName;

    @Schema(description = "工艺流程名称")
    private String formulaName;

    @Schema(description = "工艺节点名称")
    private String formulaItemName;

    @Schema(description = "备用字段1")
    private String bak01;

    @Schema(description = "备用字段2")
    private String bak02;

    @Schema(description = "备用字段3")
    private String bak03;

    @Schema(description = "老托盘号")
    private String oldTrayNo;

}
