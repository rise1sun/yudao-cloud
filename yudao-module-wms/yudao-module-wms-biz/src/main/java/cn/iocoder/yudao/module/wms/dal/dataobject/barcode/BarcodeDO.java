package cn.iocoder.yudao.module.wms.dal.dataobject.barcode;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 条码 DO
 *
 * @author 管理员
 */
@TableName("wms_barcode")
@KeySequence("wms_barcode_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BarcodeDO extends BaseDO {

    /**
     * 条码id
     */
    @TableId
    private Long id;
    /**
     * 条码号
     */
    private String barcode;
    /**
     * 数据来源
     */
    private String dataSource;
    /**
     * 规格
     */
    private String spec;
    /**
     * 库区
     */
    private String area;
    /**
     * 库位
     */
    private String storage;
    /**
     * 条码状态
     *
     * 枚举 {@link TODO wms_barcode_status 对应的类}
     */
    private Byte barcodeStatus;
    /**
     * 当前工艺流程编号
     */
    private Integer formulaItemId;
    /**
     * ng点位
     */
    private String ngSite;
    /**
     * 复测记录标记
     */
    private Integer retest;
    /**
     * 工艺流程id
     */
    private Integer formulaId;
    /**
     * 托盘条码
     */
    private String trayId;
    /**
     * 通道号
     */
    private Integer channelIndex;
    /**
     * 批次id
     */
    private Integer batchId;
    /**
     * 关联电芯号
     */
    private Integer batteryId;
    /**
     * 设备名
     */
    private String cabName;
    /**
     * 是否锁定，0未锁定，1锁定
     */
    private Boolean isLocked;
    /**
     * MES生产订单号
     */
    private String mesProductionNumber;
    /**
     * 客户编号
     */
    private String customerNumber;
    /**
     * 备注
     */
    private String remark;

    private String batchName;

    private String formulaName;

    private String formulaItemName;

    private String bak01;

    private String bak02;

    private String bak03;

    private String oldTrayNo;

}
