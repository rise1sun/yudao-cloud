package cn.iocoder.yudao.module.wms.controller.admin.tray.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import cn.iocoder.yudao.module.wms.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户 Excel 导入 VO
 * @author admin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class TrayImportExcelVO {


    @ExcelProperty("托盘编号")
    private String trayNo;

    @ExcelProperty(value = "托盘类型", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.WMS_TRAY_TYPE)
    private Integer type;

    @ExcelProperty("最大绑定数量")
    private Integer maxBindNumber;

    @ExcelProperty("最大使用次数")
    private Integer maxUseNumber;

    @ExcelProperty("使用次数")
    private Integer useNumber;

    @ExcelProperty(value = "托盘状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.WMS_TRAY_STATUS)
    private Integer status;

    @ExcelProperty(value = "测试托盘标识", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.WMS_TRAY_TEST)
    private Byte isTestTray;




}
