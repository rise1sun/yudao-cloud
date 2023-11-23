package cn.iocoder.yudao.module.wms.dal.dataobject.timetablename;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 月份条码 DO
 *
 * @author 管理员
 */
@TableName("wms_time_table_name")
@KeySequence("wms_time_table_name_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeTableNameDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 月份
     */
    private Integer month;

}
