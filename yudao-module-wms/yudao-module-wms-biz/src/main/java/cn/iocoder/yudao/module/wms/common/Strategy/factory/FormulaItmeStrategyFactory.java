package cn.iocoder.yudao.module.wms.common.Strategy.factory;

import cn.iocoder.yudao.module.wms.common.Strategy.FormulaItem.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jiangfeng
 * @date 2023/7/20
 */
@Slf4j
@Component
public class FormulaItmeStrategyFactory {
    private static final ConcurrentHashMap<String, FormulaItemStrategy> strategyFactory = new ConcurrentHashMap<>();

    static {
        strategyFactory.put("HIGH-TEMPERATURE_RESTING", new HightemperatureResting());
        strategyFactory.put("LIQUID_INJECTION", new LiquidInjection());  //注液
        strategyFactory.put("MICROCHARGE", new Microcharge()); //微充
        strategyFactory.put("STAPLE", new Staple());  //插订
        strategyFactory.put("OCV1", new OcvTest());
        strategyFactory.put("OCV2", new OcvTest());
        strategyFactory.put("OCV3", new OcvTest());
        strategyFactory.put("OCV4", new OcvTest());
        strategyFactory.put("OCV5", new OcvTest());
        strategyFactory.put("OCV6", new OcvTest());
        strategyFactory.put("OCV7+DCR", new OcvTest());
        // strategyFactory.put("STAPLE",new Staple()); //插钉
        // strategyFactory.put("NAIL_REMOVAL",new NailRemoval()); //拔钉
        //  strategyFactory.put("NEGATIVE_PRESSURE_PRE-CHARGE",new NegativePressurePreCharge()); //负压预充
        // strategyFactory.put("REHYDRATION_FLUID",new PehydrationFluid()); //补液
        // strategyFactory.put("SEAL", new Seal()); //封口
        //strategyFactory.put("CLEAN_OIL", new CleanOil()); //清洗涂油
        strategyFactory.put("TEMPERATURE_REST", new TemperatureResting());
        log.info("[strategyFactory] [策略工厂加载完毕!]");
        // 在这里添加其他的策略模式实例
    }

    public static FormulaItemStrategy getStrategy(String code) {
        FormulaItemStrategy strategy = strategyFactory.get(code);
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown code: " + code);
        }
        return strategy;
    }
}
