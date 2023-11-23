package cn.iocoder.yudao.module.wms.controller.admin.task.vo;

import lombok.*;
import java.util.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 任务管理 Excel VO
 *
 * @author 管理员
 */
@Data
public class TaskExcelVO {

    @ExcelProperty("主键")
    private Integer id;

    @ExcelProperty("托盘号")
    private String trayNo;

    @ExcelProperty("起始位置")
    private String startStorage;

    @ExcelProperty("起始区域")
    private String startArea;

    @ExcelProperty("结束位置")
    private String endStorage;

    @ExcelProperty("结束区域")
    private String endArea;

    @ExcelProperty("任务状态")
    private Byte status;

    @ExcelProperty("静置时长")
    private Integer sleepTime;

    @ExcelProperty("任务类型")
    private Byte type;

    @ExcelProperty("wcs调度号")
    private Integer wcsTaskId;

    @ExcelProperty("创建时间")
    private Date createTime;

}
