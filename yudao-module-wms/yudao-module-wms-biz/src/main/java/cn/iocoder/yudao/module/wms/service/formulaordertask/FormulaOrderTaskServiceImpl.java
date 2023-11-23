package cn.iocoder.yudao.module.wms.service.formulaordertask;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.wms.controller.admin.formulaordertask.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaordertask.FormulaOrderTaskDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.wms.convert.formulaordertask.FormulaOrderTaskConvert;
import cn.iocoder.yudao.module.wms.dal.mysql.formulaordertask.FormulaOrderTaskMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wms.enums.ErrorCodeConstants.*;

/**
 * 工艺流程订单任务 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class FormulaOrderTaskServiceImpl implements FormulaOrderTaskService {

    @Resource
    private FormulaOrderTaskMapper formulaOrderTaskMapper;

    @Override
    public Integer createFormulaOrderTask(FormulaOrderTaskCreateReqVO createReqVO) {
        // 插入
        FormulaOrderTaskDO formulaOrderTask = FormulaOrderTaskConvert.INSTANCE.convert(createReqVO);
        formulaOrderTaskMapper.insert(formulaOrderTask);
        // 返回
        return formulaOrderTask.getId();
    }

    @Override
    public void updateFormulaOrderTask(FormulaOrderTaskUpdateReqVO updateReqVO) {
        // 校验存在
        validateFormulaOrderTaskExists(updateReqVO.getId());
        // 更新
        FormulaOrderTaskDO updateObj = FormulaOrderTaskConvert.INSTANCE.convert(updateReqVO);
        formulaOrderTaskMapper.updateById(updateObj);
    }

    @Override
    public void deleteFormulaOrderTask(Integer id) {
        // 校验存在
        validateFormulaOrderTaskExists(id);
        // 删除
        formulaOrderTaskMapper.deleteById(id);
    }

    private void validateFormulaOrderTaskExists(Integer id) {
        if (formulaOrderTaskMapper.selectById(id) == null) {
            throw exception(FORMULA_ORDER_TASK_NOT_EXISTS);
        }
    }

    @Override
    public FormulaOrderTaskDO getFormulaOrderTask(Integer id) {
        return formulaOrderTaskMapper.selectById(id);
    }

    @Override
    public List<FormulaOrderTaskDO> getFormulaOrderTaskList(Collection<Integer> ids) {
        return formulaOrderTaskMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FormulaOrderTaskDO> getFormulaOrderTaskPage(FormulaOrderTaskPageReqVO pageReqVO) {
        return formulaOrderTaskMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FormulaOrderTaskDO> getFormulaOrderTaskList(FormulaOrderTaskExportReqVO exportReqVO) {
        return formulaOrderTaskMapper.selectList(exportReqVO);
    }

}
