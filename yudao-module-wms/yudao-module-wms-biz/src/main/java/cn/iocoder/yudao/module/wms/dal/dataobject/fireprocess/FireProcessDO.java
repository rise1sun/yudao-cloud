package cn.iocoder.yudao.module.wms.dal.dataobject.fireprocess;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 消防任务 DO
 *
 * @author 管理员
 */
@TableName("wms_fire_process")
@KeySequence("wms_fire_process_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FireProcessDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 区域
     */
    private String area;
    /**
     * 起始位置
     */
    private String startStorage;
    /**
     * 报警原因
     */
    private String content;
    /**
     * 消防任务状态  0未处理 1已处理
     *
     * 枚举 {@link TODO wms_fire_process_state 对应的类}
     */
    private Integer state;

}
