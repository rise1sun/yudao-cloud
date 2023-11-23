package cn.iocoder.yudao.module.wms.common.Strategy.FormulaItem;

import cn.iocoder.yudao.module.wms.enums.api.strategy.dto.FormulaItemStrategyDTO;

/**
 * @author jiangfeng
 * @date 2023/7/19
 */
public interface FormulaItemStrategy {

    void handle(FormulaItemStrategyDTO dto);
}
