package cn.iocoder.yudao.module.wms.enums.batch;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BatchStatusEnum {

    /**正常 **/
    NORMAL(0),
    /** 停用 */
    DISABLE(1),
    /** 运行中 */
    RUNNING(2);

    private final Integer status;

}