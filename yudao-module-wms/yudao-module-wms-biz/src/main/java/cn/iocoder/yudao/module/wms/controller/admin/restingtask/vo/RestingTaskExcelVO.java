package cn.iocoder.yudao.module.wms.controller.admin.restingtask.vo;

import lombok.*;
import java.util.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 静置任务信息 Excel VO
 *
 * @author 管理员
 */
@Data
public class RestingTaskExcelVO {

    @ExcelProperty("主键")
    private Integer id;

    @ExcelProperty("托盘号")
    private String trayNo;

    @ExcelProperty("静置区域")
    private String area;

    @ExcelProperty("静置库位")
    private String storage;

    @ExcelProperty("静置时间")
    private String restingTime;

    @ExcelProperty("任务订单号")
    private String orderTask;

    @ExcelProperty("状态（0静置中 1静置完成未调度 2静置完成已调度 3调度后wcs异常托盘还在库）")
    private Byte status;

    @ExcelProperty("创建时间")
    private Date createTime;

}
