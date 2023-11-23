package cn.iocoder.yudao.module.wms.dal.mysql.formulaordertask;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaitem.FormulaItemDO;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaordertask.FormulaOrderTaskDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wms.controller.admin.formulaordertask.vo.*;

/**
 * 工艺流程订单任务 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface FormulaOrderTaskMapper extends BaseMapperX<FormulaOrderTaskDO> {

    default PageResult<FormulaOrderTaskDO> selectPage(FormulaOrderTaskPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FormulaOrderTaskDO>()
                .eqIfPresent(FormulaOrderTaskDO::getFormulaId, reqVO.getFormulaId())
                .likeIfPresent(FormulaOrderTaskDO::getFormulaName, reqVO.getFormulaName())
                .eqIfPresent(FormulaOrderTaskDO::getFormulaItemId, reqVO.getFormulaItemId())
                .likeIfPresent(FormulaOrderTaskDO::getFormulaItemName, reqVO.getFormulaItemName())
                .eqIfPresent(FormulaOrderTaskDO::getOrderTask, reqVO.getOrderTask())
                .eqIfPresent(FormulaOrderTaskDO::getTrayNo, reqVO.getTrayNo())
                .betweenIfPresent(FormulaOrderTaskDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FormulaOrderTaskDO::getId));
    }

    default List<FormulaOrderTaskDO> selectList(FormulaOrderTaskExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FormulaOrderTaskDO>()
                .eqIfPresent(FormulaOrderTaskDO::getFormulaId, reqVO.getFormulaId())
                .likeIfPresent(FormulaOrderTaskDO::getFormulaName, reqVO.getFormulaName())
                .eqIfPresent(FormulaOrderTaskDO::getFormulaItemId, reqVO.getFormulaItemId())
                .likeIfPresent(FormulaOrderTaskDO::getFormulaItemName, reqVO.getFormulaItemName())
                .eqIfPresent(FormulaOrderTaskDO::getOrderTask, reqVO.getOrderTask())
                .eqIfPresent(FormulaOrderTaskDO::getTrayNo, reqVO.getTrayNo())
                .betweenIfPresent(FormulaOrderTaskDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FormulaOrderTaskDO::getId));
    }

    FormulaItemDO getNewFormulaItemByOldFormulaId(Integer orderTaskId);
}
