package cn.iocoder.yudao.module.wms.controller.admin.batch.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import cn.iocoder.yudao.module.wms.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 批次 Excel VO
 *
 * @author 管理员
 */
@Data
public class BatchExcelVO {

    @ExcelProperty("批次ID")
    private Long id;

    @ExcelProperty("工艺流程ID")
    private Long formulaId;

    @ExcelProperty("工艺流程")
    private String formulaName;

    @ExcelProperty("批次名称")
    private String name;

    @ExcelProperty("型号")
    private String spec;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty(value = "批次状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.WMS_BATCH_STATUS)
    private Byte status;

    @ExcelProperty("默认提醒次数")
    private Integer number;

    @ExcelProperty("创建时间")
    private Date createTime;

}
