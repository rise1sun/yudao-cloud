package cn.iocoder.yudao.module.wms.enums.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jiangfeng
 * @version 1.0
 * @date 2023/4/11 11:02
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {

    /** 空闲 */
    FREE(1),
    /** 绑定 */
    BIND(2),
    /* 运行中 */
    RUN(3);

    /**
     * 状态
     */
    private final Integer status;

}
