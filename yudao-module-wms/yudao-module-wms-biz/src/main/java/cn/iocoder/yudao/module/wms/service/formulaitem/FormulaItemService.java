package cn.iocoder.yudao.module.wms.service.formulaitem;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wms.controller.admin.formulaitem.vo.*;
import cn.iocoder.yudao.module.wms.controller.app.pda.vo.WarehousingVO;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaitem.FormulaItemDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.wms.dal.dataobject.tray.TrayDO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工艺流程节点 Service 接口
 *
 * @author 管理员
 */
public interface FormulaItemService {

    /**
     * 创建工艺流程节点
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createFormulaItem(@Valid FormulaItemCreateReqVO createReqVO);

    /**
     * 更新工艺流程节点
     *
     * @param updateReqVO 更新信息
     */
    void updateFormulaItem(@Valid FormulaItemUpdateReqVO updateReqVO);

    /**
     * 删除工艺流程节点
     *
     * @param id 编号
     */
    void deleteFormulaItem(Integer id);

    /**
     * 获得工艺流程节点
     *
     * @param id 编号
     * @return 工艺流程节点
     */
    FormulaItemDO getFormulaItem(Integer id);

    /**
     * 获得工艺流程节点列表
     *
     * @param ids 编号
     * @return 工艺流程节点列表
     */
    List<FormulaItemDO> getFormulaItemList(Collection<Integer> ids);

    /**
     * 获得工艺流程节点分页
     *
     * @param pageReqVO 分页查询
     * @return 工艺流程节点分页
     */
    PageResult<FormulaItemDO> getFormulaItemPage(FormulaItemPageReqVO pageReqVO);

    /**
     * 获得工艺流程节点列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 工艺流程节点列表
     */
    List<FormulaItemDO> getFormulaItemList(FormulaItemExportReqVO exportReqVO);

    /**
     * 获取工艺节点列表，用于创建工艺流程
     * @return
     */
    List<FormulaItemDO> getFormulaItemLists();

    /**
     * 工艺流转
     *
     * @param warehousingVO
     * @return
     */
    Integer formulaItemFlow(WarehousingVO warehousingVO);

    /**
     * 根据订单任务id获取tFormulaItemDO
     * @param orderTaskId
     * @return
     */
    FormulaItemDO getFormulaItemByorderTaskId(Integer orderTaskId);

    /**
     * 获取订单的下一节点工艺
     * @param orderTaskId
     * @return
     */
    FormulaItemDO getNewFormulaItemByOldFormulaId(Integer orderTaskId);
}
