package cn.iocoder.yudao.module.wms.dal.dataobject.restingtask;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 静置任务信息 DO
 *
 * @author 管理员
 */
@TableName("wms_resting_task")
@KeySequence("wms_resting_task_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestingTaskDO extends BaseDO {

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
     * 静置区域
     */
    private String area;
    /**
     * 静置库位
     */
    private String storage;
    /**
     * 静置时间
     */
    private String restingTime;
    /**
     * 任务订单号
     */
    private String orderTask;
    /**
     * 状态
     */
    private Byte status;

    private String errorInfo;

}
