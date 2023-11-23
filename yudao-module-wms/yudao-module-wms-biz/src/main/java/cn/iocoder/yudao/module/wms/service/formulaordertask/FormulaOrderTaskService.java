package cn.iocoder.yudao.module.wms.service.formulaordertask;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wms.controller.admin.formulaordertask.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaordertask.FormulaOrderTaskDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 工艺流程订单任务 Service 接口
 *
 * @author 管理员
 */
public interface FormulaOrderTaskService {

    /**
     * 创建工艺流程订单任务
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createFormulaOrderTask(@Valid FormulaOrderTaskCreateReqVO createReqVO);

    /**
     * 更新工艺流程订单任务
     *
     * @param updateReqVO 更新信息
     */
    void updateFormulaOrderTask(@Valid FormulaOrderTaskUpdateReqVO updateReqVO);

    /**
     * 删除工艺流程订单任务
     *
     * @param id 编号
     */
    void deleteFormulaOrderTask(Integer id);

    /**
     * 获得工艺流程订单任务
     *
     * @param id 编号
     * @return 工艺流程订单任务
     */
    FormulaOrderTaskDO getFormulaOrderTask(Integer id);

    /**
     * 获得工艺流程订单任务列表
     *
     * @param ids 编号
     * @return 工艺流程订单任务列表
     */
    List<FormulaOrderTaskDO> getFormulaOrderTaskList(Collection<Integer> ids);

    /**
     * 获得工艺流程订单任务分页
     *
     * @param pageReqVO 分页查询
     * @return 工艺流程订单任务分页
     */
    PageResult<FormulaOrderTaskDO> getFormulaOrderTaskPage(FormulaOrderTaskPageReqVO pageReqVO);

    /**
     * 获得工艺流程订单任务列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 工艺流程订单任务列表
     */
    List<FormulaOrderTaskDO> getFormulaOrderTaskList(FormulaOrderTaskExportReqVO exportReqVO);

}
