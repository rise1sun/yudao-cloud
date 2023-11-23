package cn.iocoder.yudao.module.wms.controller.admin.traylog.vo;

import lombok.*;
import java.util.*;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * wms托盘日志记录 Excel VO
 *
 * @author 管理员
 */
@Data
public class TrayLogExcelVO {

    @ExcelProperty("主键")
    private Integer id;

    @ExcelProperty("托盘号")
    private String trayNo;

    @ExcelProperty("日志类型")
    private Byte type;

    @ExcelProperty("业务类型")
    private Byte serviceType;

    @ExcelProperty("wcs调度任务号")
    private String dispatchTaskNumber;

    @ExcelProperty("0失败 1成功")
    private Byte result;

    @ExcelProperty("接口调用方")
    private String caller;

    @ExcelProperty("接口被调用方")
    private String callee;

    @ExcelProperty("方法名称")
    private String methodName;

    @ExcelProperty("请求参数")
    private String requestParameters;

    @ExcelProperty("返回结果")
    private String responseResults;

    @ExcelProperty("起始库位")
    private String startStorage;

    @ExcelProperty("起始库区")
    private String startArea;

    @ExcelProperty("结束库位")
    private String endStorage;

    @ExcelProperty("结束库区")
    private String endArea;

    @ExcelProperty("异常信息")
    private String errorInfo;

    @ExcelProperty("当前条码信息")
    private String barcodes;

    @ExcelProperty("条码数量")
    private Integer barcodeNumber;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("托盘类型")
    private Integer trayType;

    @ExcelProperty("条码状态")
    private String barcodeStatus;

}
