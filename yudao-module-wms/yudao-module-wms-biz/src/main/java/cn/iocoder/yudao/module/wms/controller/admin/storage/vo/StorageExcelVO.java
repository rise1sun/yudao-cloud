package cn.iocoder.yudao.module.wms.controller.admin.storage.vo;

import lombok.*;
import java.util.*;
import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;


/**
 * 库位 Excel VO
 *
 * @author 管理员
 */
@Data
public class StorageExcelVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("区域ID")
    private Long areaId;

    @ExcelProperty("库位编号")
    private String code;

    @ExcelProperty("库位名称")
    private String name;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("storage_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;

    @ExcelProperty(value = "有无货物", converter = DictConvert.class)
    @DictFormat("storage_has_goods") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte hasGood;

    @ExcelProperty(value = "有无消防通道", converter = DictConvert.class)
    @DictFormat("storage_has_fire_channel") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte hasFireChannel;

    @ExcelProperty(value = "特殊库位", converter = DictConvert.class)
    @DictFormat("special_storage") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte specialStorage;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private Date createTime;

}
