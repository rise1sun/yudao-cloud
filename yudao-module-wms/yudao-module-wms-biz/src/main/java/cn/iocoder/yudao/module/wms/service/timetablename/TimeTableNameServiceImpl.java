package cn.iocoder.yudao.module.wms.service.timetablename;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.wms.controller.admin.timetablename.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.timetablename.TimeTableNameDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.wms.convert.timetablename.TimeTableNameConvert;
import cn.iocoder.yudao.module.wms.dal.mysql.timetablename.TimeTableNameMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wms.enums.ErrorCodeConstants.*;

/**
 * 月份条码 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TimeTableNameServiceImpl implements TimeTableNameService {

    @Resource
    private TimeTableNameMapper timeTableNameMapper;

    @Override
    public Integer createTimeTableName(TimeTableNameCreateReqVO createReqVO) {
        // 插入
        TimeTableNameDO timeTableName = TimeTableNameConvert.INSTANCE.convert(createReqVO);
        timeTableNameMapper.insert(timeTableName);
        // 返回
        return timeTableName.getId();
    }

    @Override
    public void updateTimeTableName(TimeTableNameUpdateReqVO updateReqVO) {
        // 校验存在
        validateTimeTableNameExists(updateReqVO.getId());
        // 更新
        TimeTableNameDO updateObj = TimeTableNameConvert.INSTANCE.convert(updateReqVO);
        timeTableNameMapper.updateById(updateObj);
    }

    @Override
    public void deleteTimeTableName(Integer id) {
        // 校验存在
        validateTimeTableNameExists(id);
        // 删除
        timeTableNameMapper.deleteById(id);
    }

    private void validateTimeTableNameExists(Integer id) {
        if (timeTableNameMapper.selectById(id) == null) {
            throw exception(TIME_TABLE_NAME_NOT_EXISTS);
        }
    }

    @Override
    public TimeTableNameDO getTimeTableName(Integer id) {
        return timeTableNameMapper.selectById(id);
    }

    @Override
    public List<TimeTableNameDO> getTimeTableNameList(Collection<Integer> ids) {
        return timeTableNameMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<TimeTableNameDO> getTimeTableNamePage(TimeTableNamePageReqVO pageReqVO) {
        return timeTableNameMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TimeTableNameDO> getTimeTableNameList(TimeTableNameExportReqVO exportReqVO) {
        return timeTableNameMapper.selectList(exportReqVO);
    }

    @Override
    public TimeTableNameDO getNewestInfo() {
        return timeTableNameMapper.getNewestInfo();
    }

    @Override
    public List<TimeTableNameDO> getTimeTableNameList() {
        return timeTableNameMapper.selectList();
    }

    @Override
    public TimeTableNameDO getTimeTableInfo(String month) {
        return timeTableNameMapper.getTimeTableInfo(month);
    }

}
