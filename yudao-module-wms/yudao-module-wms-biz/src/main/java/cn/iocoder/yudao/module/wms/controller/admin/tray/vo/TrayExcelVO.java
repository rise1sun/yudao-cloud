package cn.iocoder.yudao.module.wms.controller.admin.tray.vo;

import lombok.*;
import java.util.*;

import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

import javax.validation.constraints.NotNull;


/**
 * 托盘 Excel VO
 *
 * @author 管理员
 */
@Data
public class TrayExcelVO {

    @ExcelProperty("托盘id")
    private Long id;

    @ExcelProperty(value = "托盘类型", converter = DictConvert.class)
    @DictFormat("wms_tray_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte type;

    @ExcelProperty("最大绑定数量")
    private Integer maxBindNumber;

    @ExcelProperty("最大使用次数")
    private Integer maxUseNumber;

    @ExcelProperty("使用次数")
    private Integer useNumber;

    @ExcelProperty(value = "托盘状态", converter = DictConvert.class)
    @DictFormat("wms_tray_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;

    @ExcelProperty(value = "托盘测试", converter = DictConvert.class)
    @DictFormat("wms_tray_test")
    private Byte isTestTray;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("托盘编号")
    private String trayNo;



}
