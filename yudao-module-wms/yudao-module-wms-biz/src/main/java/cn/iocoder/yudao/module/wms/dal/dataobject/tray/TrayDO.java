package cn.iocoder.yudao.module.wms.dal.dataobject.tray;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 托盘 DO
 *
 * @author 管理员
 */
@TableName("wms_tray")
@KeySequence("wms_tray_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrayDO extends BaseDO {

    /**
     * 托盘id
     */
    @TableId
    private Long id;
    /**
     * 托盘类型
     *
     * 枚举 {@link TODO wms_tray_type 对应的类}
     */
    private Byte type;
    /**
     * 最大绑定数量
     */
    private Integer maxBindNumber;
    /**
     * 最大使用次数
     */
    private Integer maxUseNumber;

    /**
     * 使用次数
     */
    private Integer useNumber;
    /**
     * 托盘状态
     *
     * 枚举 {@link TODO wms_tray_status 对应的类}
     */
    private Byte status;
    /**
     * 托盘编号
     */
    private String trayNo;

    /**
     * 托盘测试标识
     */
    private Byte isTestTray;

    private String barcodeTableName;

    private Integer storageId;

    private String storageName;

    private Integer orderTaskId;

}
