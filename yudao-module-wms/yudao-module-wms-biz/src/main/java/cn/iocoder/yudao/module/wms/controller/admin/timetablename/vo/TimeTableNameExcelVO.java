package cn.iocoder.yudao.module.wms.controller.admin.timetablename.vo;

import lombok.*;
import java.util.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 月份条码 Excel VO
 *
 * @author 管理员
 */
@Data
public class TimeTableNameExcelVO {

    @ExcelProperty("主键")
    private Integer id;

    @ExcelProperty("表名称")
    private String tableName;

    @ExcelProperty("月份")
    private Integer month;

    @ExcelProperty("创建时间")
    private Date createTime;

}
