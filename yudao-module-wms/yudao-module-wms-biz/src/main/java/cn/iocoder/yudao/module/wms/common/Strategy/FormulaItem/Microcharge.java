package cn.iocoder.yudao.module.wms.common.Strategy.FormulaItem;

import cn.iocoder.yudao.module.wms.enums.api.strategy.dto.FormulaItemStrategyDTO;

/**
 * @author jiangfeng
 * @date 2023/7/25
 */
public class Microcharge implements FormulaItemStrategy{
    @Override
    public void handle(FormulaItemStrategyDTO dto) {
        System.out.println("微充");
    }
}
