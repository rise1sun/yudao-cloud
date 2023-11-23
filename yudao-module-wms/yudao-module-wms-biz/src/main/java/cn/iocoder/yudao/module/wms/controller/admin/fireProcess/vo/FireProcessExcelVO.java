package cn.iocoder.yudao.module.wms.controller.admin.fireProcess.vo;

import lombok.*;
import java.util.*;

import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;


/**
 * 消防任务 Excel VO
 *
 * @author 管理员
 */
@Data
public class FireProcessExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("区域")
    private String area;

    @ExcelProperty("起始位置")
    private String startStorage;

    @ExcelProperty("报警原因")
    private String content;

    @ExcelProperty(value = "消防任务状态  0未处理 1已处理", converter = DictConvert.class)
    @DictFormat("wms_fire_process_state") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer state;

    @ExcelProperty("创建时间")
    private Date createTime;

}
