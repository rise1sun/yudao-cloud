package cn.iocoder.yudao.module.wms.dal.dataobject.batch;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 批次 DO
 *
 * @author 管理员
 */
@TableName("wms_batch")
@KeySequence("wms_batch_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchDO extends BaseDO {

    /**
     * 批次ID
     */
    @TableId
    private Long id;
    /**
     * 工艺流程ID
     */
    private Long formulaId;
    /**
     * 批次名称
     */
    private String name;
    /**
     * 型号
     */
    private String spec;
    /**
     * 备注
     */
    private String remark;
    /**
     * 批次状态（0正常 1停用 2上料绑定批次）
     */
    private Byte status;
    /**
     * 默认提醒次数
     */
    private Integer number;

}
