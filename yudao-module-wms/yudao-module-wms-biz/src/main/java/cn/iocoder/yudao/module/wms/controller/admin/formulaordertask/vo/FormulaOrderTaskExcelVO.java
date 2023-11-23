package cn.iocoder.yudao.module.wms.controller.admin.formulaordertask.vo;

import lombok.*;
import java.util.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 工艺流程订单任务 Excel VO
 *
 * @author 管理员
 */
@Data
public class FormulaOrderTaskExcelVO {

    @ExcelProperty("主键")
    private Integer id;

    @ExcelProperty("工艺流程id")
    private Integer formulaId;

    @ExcelProperty("工艺流程名称")
    private String formulaName;

    @ExcelProperty("工艺流程节点id")
    private Integer formulaItemId;

    @ExcelProperty("工艺流程节点名称")
    private String formulaItemName;

    @ExcelProperty("库位id")
    private Integer storageId;

    @ExcelProperty("库位名称")
    private String storageName;

    @ExcelProperty("任务订单号")
    private String orderTask;

    @ExcelProperty("托盘号")
    private String trayNo;

    @ExcelProperty("创建时间")
    private Date createTime;

}
