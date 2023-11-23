package cn.iocoder.yudao.module.wms.dal.dataobject.formula;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 工艺流程 DO
 *
 * @author 管理员
 */
@TableName("wms_formula")
@KeySequence("wms_formula_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormulaDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 工艺流程编码
     */
    private String code;
    /**
     * 工艺流程名称
     */
    private String name;
    /**
     * 状态
     *
     * 枚举
     */
    private Byte status;

    private Byte type;
    /**
     * 超时时间
     */
    private String timeOut;

}
