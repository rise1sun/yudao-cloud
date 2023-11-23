package cn.iocoder.yudao.module.wms.dal.dataobject.formulaordertask;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工艺流程订单任务 DO
 *
 * @author 管理员
 */
@TableName("wms_formula_order_task")
@KeySequence("wms_formula_order_task_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormulaOrderTaskDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 工艺流程编码
     */
    private String formulaId;
    /**
     * 工艺流程名称
     */
    private String formulaName;
    /**
     * 当前工艺流程节点
     */
    private String formulaItemId;
    /**
     * 当前工艺流程节点名称
     */
    private String formulaItemName;
    /**
     * 任务订单号
     */
    private String orderTask;
    /**
     * 托盘号
     */
    private String trayNo;

}
