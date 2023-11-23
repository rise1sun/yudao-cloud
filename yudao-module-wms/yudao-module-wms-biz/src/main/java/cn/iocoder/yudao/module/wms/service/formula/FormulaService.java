package cn.iocoder.yudao.module.wms.service.formula;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wms.controller.admin.formula.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.formula.FormulaDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaitem.FormulaItemDO;

/**
 * 工艺流程 Service 接口
 *
 * @author 管理员
 */
public interface FormulaService {

    /**
     * 创建工艺流程
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createFormula(@Valid FormulaCreateReqVO createReqVO);

    /**
     * 更新工艺流程
     *
     * @param updateReqVO 更新信息
     */
    void updateFormula(@Valid FormulaUpdateReqVO updateReqVO);

    /**
     * 删除工艺流程
     *
     * @param id 编号
     */
    void deleteFormula(Integer id);

    /**
     * 获得工艺流程
     *
     * @param id 编号
     * @return 工艺流程
     */
    FormulaDO getFormula(Integer id);

    /**
     * 获得工艺流程列表
     *
     * @param ids 编号
     * @return 工艺流程列表
     */
    List<FormulaDO> getFormulaList(Collection<Integer> ids);

    /**
     * 获得工艺流程分页
     *
     * @param pageReqVO 分页查询
     * @return 工艺流程分页
     */
    PageResult<FormulaDO> getFormulaPage(FormulaPageReqVO pageReqVO);

    /**
     * 获得工艺流程列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 工艺流程列表
     */
    List<FormulaDO> getFormulaList(FormulaExportReqVO exportReqVO);

    /**
     * 获取工艺流程主子表数据，用于修改操作
     * @param id
     * @return
     */
    List<FormulaItemDO> getFormulaForUpdate(Integer id);

    /**
     * 获取启用工艺流程信息
     * @return
     */
    List<FormulaDO> getFormulas();

    /**
     * 初始化本地缓存
     */
    void initLocalCache();

}
