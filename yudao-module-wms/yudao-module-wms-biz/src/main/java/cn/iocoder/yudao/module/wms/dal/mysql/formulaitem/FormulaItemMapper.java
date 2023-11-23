package cn.iocoder.yudao.module.wms.dal.mysql.formulaitem;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaitem.FormulaItemDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wms.controller.admin.formulaitem.vo.*;

/**
 * 工艺流程节点 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface FormulaItemMapper extends BaseMapperX<FormulaItemDO> {

    default PageResult<FormulaItemDO> selectPage(FormulaItemPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FormulaItemDO>()
                .eqIfPresent(FormulaItemDO::getSerialNumber, reqVO.getSerialNumber())
                .eqIfPresent(FormulaItemDO::getFormulaId, reqVO.getFormulaId())
                .likeIfPresent(FormulaItemDO::getName, reqVO.getName())
                .eqIfPresent(FormulaItemDO::getCode, reqVO.getCode())
                .eqIfPresent(FormulaItemDO::getRestingTime, reqVO.getRestingTime())
                .eqIfPresent(FormulaItemDO::getStatus, reqVO.getStatus())
                .eqIfPresent(FormulaItemDO::getRuleId, reqVO.getRuleId())
                .betweenIfPresent(FormulaItemDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FormulaItemDO::getArea, reqVO.getArea())
                .eqIfPresent(FormulaItemDO::getRetestNumber, reqVO.getRetestNumber())
                .orderByDesc(FormulaItemDO::getId));
    }

    default List<FormulaItemDO> selectList(FormulaItemExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FormulaItemDO>()
                .eqIfPresent(FormulaItemDO::getSerialNumber, reqVO.getSerialNumber())
                .eqIfPresent(FormulaItemDO::getFormulaId, reqVO.getFormulaId())
                .likeIfPresent(FormulaItemDO::getName, reqVO.getName())
                .eqIfPresent(FormulaItemDO::getCode, reqVO.getCode())
                .eqIfPresent(FormulaItemDO::getRestingTime, reqVO.getRestingTime())
                .eqIfPresent(FormulaItemDO::getStatus, reqVO.getStatus())
                .eqIfPresent(FormulaItemDO::getRuleId, reqVO.getRuleId())
                .betweenIfPresent(FormulaItemDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FormulaItemDO::getArea, reqVO.getArea())
                .eqIfPresent(FormulaItemDO::getRetestNumber, reqVO.getRetestNumber())
                .orderByDesc(FormulaItemDO::getId));
    }

    default List<FormulaItemDO> selectLists(){
        return selectList(new LambdaQueryWrapperX<FormulaItemDO>()
                .eqIfPresent(FormulaItemDO::getFormulaId,"example").orderByAsc(FormulaItemDO::getSerialNumber));
    }

    FormulaItemDO getFormulaItemByorderTaskId(Integer orderTaskId);
}
