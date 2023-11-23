package cn.iocoder.yudao.module.wms.common.Strategy.FormulaItem;

import cn.iocoder.yudao.module.wms.enums.api.strategy.dto.FormulaItemStrategyDTO;

public class OcvTest implements FormulaItemStrategy {
    @Override
    public void handle(FormulaItemStrategyDTO dto) {
        System.out.println("OCV");
    }
}
