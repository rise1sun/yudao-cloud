package cn.iocoder.yudao.module.wms.enums.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jiangfeng
 * @version 1.0
 * @date 2023/4/11 11:28
 */
@Getter
@AllArgsConstructor
public enum TypeEnum {
    /** 小托盘 */
    SAMLL(1),
    /** 中等托盘 */
    NORMOL(2),
    /* 大托盘 */
    BIG(3);

    /**
     * 状态
     */
    private final Integer type;

}
