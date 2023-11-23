package cn.iocoder.yudao.module.wms.dal.dataobject.task;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 任务管理 DO
 *
 * @author 管理员
 */
@TableName("wms_task")
@KeySequence("wms_task_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDO extends BaseDO {

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
     * 起始位置
     */
    private String startStorage;
    /**
     * 起始区域
     */
    private String startArea;
    /**
     * 结束位置
     */
    private String endStorage;
    /**
     * 结束区域
     */
    private String endArea;
    /**
     * 任务状态
     */
    private Byte status;
    /**
     * 静置时长
     */
    private Integer sleepTime;
    /**
     * 任务类型
     */
    private Byte type;
    /**
     * wcs调度号
     */
    private Integer wcsTaskId;

}
