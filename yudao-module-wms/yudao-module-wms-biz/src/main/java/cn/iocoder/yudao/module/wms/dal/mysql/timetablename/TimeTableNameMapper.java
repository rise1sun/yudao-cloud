package cn.iocoder.yudao.module.wms.dal.mysql.timetablename;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wms.dal.dataobject.timetablename.TimeTableNameDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wms.controller.admin.timetablename.vo.*;

/**
 * 月份条码 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TimeTableNameMapper extends BaseMapperX<TimeTableNameDO> {

    default PageResult<TimeTableNameDO> selectPage(TimeTableNamePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TimeTableNameDO>()
                .likeIfPresent(TimeTableNameDO::getTableName, reqVO.getTableName())
                .eqIfPresent(TimeTableNameDO::getMonth, reqVO.getMonth())
                .betweenIfPresent(TimeTableNameDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TimeTableNameDO::getId));
    }

    default List<TimeTableNameDO> selectList(TimeTableNameExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TimeTableNameDO>()
                .likeIfPresent(TimeTableNameDO::getTableName, reqVO.getTableName())
                .eqIfPresent(TimeTableNameDO::getMonth, reqVO.getMonth())
                .betweenIfPresent(TimeTableNameDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TimeTableNameDO::getId));
    }

    default TimeTableNameDO getNewestInfo() {
        List<TimeTableNameDO> timeTableNameDOS = selectList(new LambdaQueryWrapperX<TimeTableNameDO>()
                .orderByDesc(TimeTableNameDO::getId)
                .last("LIMIT 1"));
        return timeTableNameDOS.size() > 0 ? timeTableNameDOS.get(0) : null;
    }

    default TimeTableNameDO getTimeTableInfo(String month){
        return selectOne(new LambdaQueryWrapperX<TimeTableNameDO>()
                .eqIfPresent(TimeTableNameDO::getMonth,month));
    }
}
