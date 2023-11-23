package cn.iocoder.yudao.module.wms.dal.dataobject.storage;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 库位 DO
 *
 * @author 管理员
 */
@TableName("wms_storage")
@KeySequence("wms_storage_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 区域ID
     */
    private Long areaId;
    /**
     * 库位编号
     */
    private String code;
    /**
     * 库位名称
     */
    private String name;
    /**
     * 状态
     *
     * 枚举 {@link TODO storage_status 对应的类}
     */
    private Byte status;
    /**
     * 有无货物
     *
     * 枚举 {@link TODO storage_has_goods 对应的类}
     */
    private Byte hasGood;
    /**
     * 有无消防通道
     *
     * 枚举 {@link TODO storage_has_fire_channel 对应的类}
     */
    private Byte hasFireChannel;
    /**
     * 特殊库位
     *
     * 枚举 {@link TODO special_storage 对应的类}
     */
    private Byte specialStorage;
    /**
     * 备注
     */
    private String remark;

   /* private String areaName;

    private String areaPrefix;*/

}
