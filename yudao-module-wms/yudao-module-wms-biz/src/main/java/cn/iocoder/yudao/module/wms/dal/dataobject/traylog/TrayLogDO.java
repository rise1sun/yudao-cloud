package cn.iocoder.yudao.module.wms.dal.dataobject.traylog;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * wms托盘日志记录 DO
 *
 * @author 管理员
 */
@TableName("wms_tray_log")
@KeySequence("wms_tray_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrayLogDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 托盘号
     */
    private String trayNo;
    /**
     * 日志类型
     */
    private Byte type;
    /**
     * 业务类型
     */
    private Byte serviceType;
    /**
     * wcs调度任务号
     */
    private String dispatchTaskNumber;
    /**
     * 0失败 1成功
     */
    private Byte result;
    /**
     * 接口调用方
     */
    private String caller;
    /**
     * 接口被调用方
     */
    private String callee;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 请求参数
     */
    private String requestParameters;
    /**
     * 返回结果
     */
    private String responseResults;
    /**
     * 起始库位
     */
    private String startStorage;
    /**
     * 起始库区
     */
    private String startArea;
    /**
     * 结束库位
     */
    private String endStorage;
    /**
     * 结束库区
     */
    private String endArea;
    /**
     * 异常信息
     */
    private String errorInfo;
    /**
     * 当前条码信息
     */
    private String barcodes;
    /**
     * 条码数量
     */
    private Integer barcodeNumber;

    private Byte trayType;

    /**
     * 条码状态
     */
    private String barcodeStatus;

    private Integer orderTaskId;

}
