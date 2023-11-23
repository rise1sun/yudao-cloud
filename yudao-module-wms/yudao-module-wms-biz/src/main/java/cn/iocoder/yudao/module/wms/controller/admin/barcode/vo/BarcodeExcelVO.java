package cn.iocoder.yudao.module.wms.controller.admin.barcode.vo;

import lombok.*;
import java.util.*;

import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;


/**
 * 条码 Excel VO
 *
 * @author 管理员
 */
@Data
public class BarcodeExcelVO {

    @ExcelProperty("条码id")
    private Long id;

    @ExcelProperty("条码号")
    private String barcode;

    @ExcelProperty("数据来源")
    private String dataSource;

    @ExcelProperty("规格")
    private String spec;

    @ExcelProperty("库区")
    private String area;

    @ExcelProperty("库位")
    private String storage;

    @ExcelProperty(value = "条码状态", converter = DictConvert.class)
    @DictFormat("wms_barcode_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte barcodeStatus;

    @ExcelProperty("当前工艺流程编号")
    private Integer formulaItemId;

    @ExcelProperty("ng点位")
    private String ngSite;

    @ExcelProperty("复测记录标记")
    private Integer retest;

    @ExcelProperty("工艺流程id")
    private Integer formulaId;

    @ExcelProperty("托盘条码")
    private String tray;

    @ExcelProperty("通道号")
    private Integer channelIndex;

    @ExcelProperty("批次id")
    private Integer batchId;

    @ExcelProperty("关联电芯号")
    private Integer batteryId;

    @ExcelProperty("设备名")
    private String cabName;

    @ExcelProperty("是否锁定，0未锁定，1锁定")
    private Boolean isLocked;

    @ExcelProperty("MES生产订单号")
    private String mesProductionNumber;

    @ExcelProperty("客户编号")
    private String customerNumber;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("批次名称")
    private String batchName;

    @ExcelProperty("工艺流程名称")
    private String formulaName;

    @ExcelProperty("工艺节点名称")
    private String formulaItemName;

    @ExcelProperty("备用字段1")
    private String bak01;

    @ExcelProperty("备用字段2")
    private String bak02;

    @ExcelProperty("备用字段3")
    private String bak03;

    @ExcelProperty("老托盘号")
    private String oldTrayNo;

}
