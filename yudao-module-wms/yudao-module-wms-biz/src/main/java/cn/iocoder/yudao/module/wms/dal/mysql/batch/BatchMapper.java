package cn.iocoder.yudao.module.wms.dal.mysql.batch;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wms.dal.dataobject.batch.BatchDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wms.controller.admin.batch.vo.*;

/**
 * 批次 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface BatchMapper extends BaseMapperX<BatchDO> {

    default PageResult<BatchDO> selectPage(BatchPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BatchDO>()
                .eqIfPresent(BatchDO::getFormulaId, reqVO.getFormulaId())
                .likeIfPresent(BatchDO::getName, reqVO.getName())
                .eqIfPresent(BatchDO::getSpec, reqVO.getSpec())
                .eqIfPresent(BatchDO::getRemark, reqVO.getRemark())
                .eqIfPresent(BatchDO::getStatus, reqVO.getStatus())
                .eqIfPresent(BatchDO::getNumber, reqVO.getNumber())
                .betweenIfPresent(BatchDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BatchDO::getId));
    }

    default List<BatchDO> selectList(BatchExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<BatchDO>()
                .eqIfPresent(BatchDO::getFormulaId, reqVO.getFormulaId())
                .likeIfPresent(BatchDO::getName, reqVO.getName())
                .eqIfPresent(BatchDO::getSpec, reqVO.getSpec())
                .eqIfPresent(BatchDO::getRemark, reqVO.getRemark())
                .eqIfPresent(BatchDO::getStatus, reqVO.getStatus())
                .eqIfPresent(BatchDO::getNumber, reqVO.getNumber())
                .betweenIfPresent(BatchDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BatchDO::getId));
    }

    Map<String, Object> getBatchAndFormulaInfo();
}
