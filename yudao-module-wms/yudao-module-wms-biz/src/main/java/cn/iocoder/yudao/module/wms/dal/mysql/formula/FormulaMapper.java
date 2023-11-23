package cn.iocoder.yudao.module.wms.dal.mysql.formula;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wms.dal.dataobject.formula.FormulaDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wms.controller.admin.formula.vo.*;

/**
 * 工艺流程 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface FormulaMapper extends BaseMapperX<FormulaDO> {

    default PageResult<FormulaDO> selectPage(FormulaPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FormulaDO>()
                .eqIfPresent(FormulaDO::getCode, reqVO.getCode())
                .likeIfPresent(FormulaDO::getName, reqVO.getName())
                .eqIfPresent(FormulaDO::getStatus, reqVO.getStatus())
                .eqIfPresent(FormulaDO::getTimeOut, reqVO.getTimeOut())
                .betweenIfPresent(FormulaDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FormulaDO::getId));
    }

    default List<FormulaDO> selectList(FormulaExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FormulaDO>()
                .eqIfPresent(FormulaDO::getCode, reqVO.getCode())
                .likeIfPresent(FormulaDO::getName, reqVO.getName())
                .eqIfPresent(FormulaDO::getStatus, reqVO.getStatus())
                .eqIfPresent(FormulaDO::getTimeOut, reqVO.getTimeOut())
                .betweenIfPresent(FormulaDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FormulaDO::getId));
    }

}
