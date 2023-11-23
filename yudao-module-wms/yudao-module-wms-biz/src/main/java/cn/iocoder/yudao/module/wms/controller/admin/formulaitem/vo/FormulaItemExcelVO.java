package cn.iocoder.yudao.module.wms.controller.admin.formulaitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;

import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;


/**
 * 工艺流程节点 Excel VO
 *
 * @author 管理员
 */
@Data
public class FormulaItemExcelVO {

    @ExcelProperty("主键")
    private Integer id;

    @ExcelProperty("序号")
    private Integer serialNumber;

    @ExcelProperty("工艺流程编码")
    private String formulaId;

    @ExcelProperty("配方名称    ")
    private String name;

    @ExcelProperty("工艺节点编码")
    private String code;

    @ExcelProperty("配方静置时长")
    private String restingTime;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("wms_formula_item_status")
    private Byte status;

    @ExcelProperty("规则表id")
    private String ruleId;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("库区")
    private String area;

    @ExcelProperty("复测次数")
    private Integer retestNumber;

    @ExcelProperty(value = "设备开关", converter = DictConvert.class)
    @DictFormat("wms_uplink_switch")
    private Byte equipmentSwitch;

    @Schema(description = "任务步骤")
    private String steps;

}
