package cn.iocoder.yudao.module.wms.dal.mysql.restingtask;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wms.dal.dataobject.restingtask.RestingTaskDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wms.controller.admin.restingtask.vo.*;

/**
 * 静置任务信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface RestingTaskMapper extends BaseMapperX<RestingTaskDO> {

    default PageResult<RestingTaskDO> selectPage(RestingTaskPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RestingTaskDO>()
                .eqIfPresent(RestingTaskDO::getTrayNo, reqVO.getTrayNo())
                .eqIfPresent(RestingTaskDO::getArea, reqVO.getArea())
                .eqIfPresent(RestingTaskDO::getStorage, reqVO.getStorage())
                .eqIfPresent(RestingTaskDO::getRestingTime, reqVO.getRestingTime())
                .eqIfPresent(RestingTaskDO::getOrderTask, reqVO.getOrderTask())
                .eqIfPresent(RestingTaskDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RestingTaskDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RestingTaskDO::getId));
    }

    default List<RestingTaskDO> selectList(RestingTaskExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RestingTaskDO>()
                .eqIfPresent(RestingTaskDO::getTrayNo, reqVO.getTrayNo())
                .eqIfPresent(RestingTaskDO::getArea, reqVO.getArea())
                .eqIfPresent(RestingTaskDO::getStorage, reqVO.getStorage())
                .eqIfPresent(RestingTaskDO::getRestingTime, reqVO.getRestingTime())
                .eqIfPresent(RestingTaskDO::getOrderTask, reqVO.getOrderTask())
                .eqIfPresent(RestingTaskDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RestingTaskDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RestingTaskDO::getId));
    }

}
