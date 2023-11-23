package cn.iocoder.yudao.module.wms.dal.mysql.fireprocess;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wms.dal.dataobject.fireprocess.FireProcessDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wms.controller.admin.fireProcess.vo.*;

/**
 * 消防任务 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface FireProcessMapper extends BaseMapperX<FireProcessDO> {

    default PageResult<FireProcessDO> selectPage(FireProcessPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FireProcessDO>()
                .eqIfPresent(FireProcessDO::getArea, reqVO.getArea())
                .eqIfPresent(FireProcessDO::getStartStorage, reqVO.getStartStorage())
                .eqIfPresent(FireProcessDO::getContent, reqVO.getContent())
                .eqIfPresent(FireProcessDO::getState, reqVO.getState())
                .betweenIfPresent(FireProcessDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FireProcessDO::getId));
    }

    default List<FireProcessDO> selectList(FireProcessExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FireProcessDO>()
                .eqIfPresent(FireProcessDO::getArea, reqVO.getArea())
                .eqIfPresent(FireProcessDO::getStartStorage, reqVO.getStartStorage())
                .eqIfPresent(FireProcessDO::getContent, reqVO.getContent())
                .eqIfPresent(FireProcessDO::getState, reqVO.getState())
                .betweenIfPresent(FireProcessDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FireProcessDO::getId));
    }

}
