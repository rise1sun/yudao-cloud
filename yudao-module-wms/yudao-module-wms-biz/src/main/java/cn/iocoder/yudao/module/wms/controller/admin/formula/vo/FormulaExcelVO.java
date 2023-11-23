package cn.iocoder.yudao.module.wms.controller.admin.formula.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 工艺流程 Excel VO
 *
 * @author 管理员
 */
@Data
public class FormulaExcelVO {

    @ExcelProperty("主键")
    private Integer id;

    @ExcelProperty("工艺流程编码")
    private String code;

    @ExcelProperty("工艺流程名称")
    private String name;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("wms_formula_status")
    private Byte status;

    @ExcelProperty(value = "类型", converter = DictConvert.class)
    @DictFormat("wms_formula_type")
    private Byte type;

    @ExcelProperty("超时时间")
    private String timeOut;

    @ExcelProperty("创建时间")
    private Date createTime;

}
