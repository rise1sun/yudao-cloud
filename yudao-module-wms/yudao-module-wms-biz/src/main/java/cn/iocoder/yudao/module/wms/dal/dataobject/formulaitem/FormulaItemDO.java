package cn.iocoder.yudao.module.wms.dal.dataobject.formulaitem;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 工艺流程节点 DO
 *
 * @author 管理员
 */
@TableName("wms_formula_item")
@KeySequence("wms_formula_item_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormulaItemDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 序号
     */
    private Integer serialNumber;
    /**
     * 工艺流程编码
     */
    private String formulaId;
    /**
     * 配方名称
     */
    private String name;
    /**
     * 工艺节点编码
     */
    private String code;
    /**
     * 配方静置时长
     */
    private String restingTime;
    /**
     * 状态
     *
     * 枚举 {@link TODO wms_formula_item_status 对应的类}
     */
    private Byte status;
    /**
     * 规则表id
     */
    private String ruleId;
    /**
     * 库区
     */
    private String area;
    /**
     * 复测次数
     */
    private Integer retestNumber;

    /**
     * 设备开关
     */
    private Byte equipmentSwitch;

    /**
     * 工艺步骤
     */
    private String steps;

}
