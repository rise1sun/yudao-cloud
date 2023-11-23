package cn.iocoder.yudao.module.wms.enums.formula;

import lombok.AllArgsConstructor;
import lombok.Getter;

    @Getter
    @AllArgsConstructor
    public enum FormulaTypeEnum {
        /**正常 **/
        NORMAL(0),
        /** 停用 */
        DISABLE(1);

        /**
         * 状态
         */
        private final Integer type;
}
