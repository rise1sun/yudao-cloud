package cn.iocoder.yudao.module.wms.common.Strategy.FormulaItem;

import cn.iocoder.yudao.module.wms.controller.admin.restingtask.vo.RestingTaskCreateReqVO;
import cn.iocoder.yudao.module.wms.convert.restingtask.RestingTaskConvert;
import cn.iocoder.yudao.module.wms.enums.api.strategy.dto.FormulaItemStrategyDTO;
import cn.iocoder.yudao.module.wms.service.restingtask.RestingTaskService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jiangfeng
 * @date 2023/7/19
 */
@Component
public class HightemperatureResting implements FormulaItemStrategy {
    @Resource
    private RestingTaskService restingTaskService;
    @Override
    public void handle(FormulaItemStrategyDTO dto) {
        RestingTaskCreateReqVO convert = RestingTaskConvert.INSTANCE.convert(dto);
        restingTaskService.createRestingTask(convert);
    }
}

