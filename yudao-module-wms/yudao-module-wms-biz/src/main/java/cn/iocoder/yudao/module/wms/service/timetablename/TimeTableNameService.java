package cn.iocoder.yudao.module.wms.service.timetablename;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wms.controller.admin.timetablename.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.timetablename.TimeTableNameDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 月份条码 Service 接口
 *
 * @author 管理员
 */
public interface TimeTableNameService {

    /**
     * 创建月份条码
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createTimeTableName(@Valid TimeTableNameCreateReqVO createReqVO);

    /**
     * 更新月份条码
     *
     * @param updateReqVO 更新信息
     */
    void updateTimeTableName(@Valid TimeTableNameUpdateReqVO updateReqVO);

    /**
     * 删除月份条码
     *
     * @param id 编号
     */
    void deleteTimeTableName(Integer id);

    /**
     * 获得月份条码
     *
     * @param id 编号
     * @return 月份条码
     */
    TimeTableNameDO getTimeTableName(Integer id);

    /**
     * 获得月份条码列表
     *
     * @param ids 编号
     * @return 月份条码列表
     */
    List<TimeTableNameDO> getTimeTableNameList(Collection<Integer> ids);

    /**
     * 获得月份条码分页
     *
     * @param pageReqVO 分页查询
     * @return 月份条码分页
     */
    PageResult<TimeTableNameDO> getTimeTableNamePage(TimeTableNamePageReqVO pageReqVO);

    /**
     * 获得月份条码列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 月份条码列表
     */
    List<TimeTableNameDO> getTimeTableNameList(TimeTableNameExportReqVO exportReqVO);

    /**
     * 获取最新的一条分表记录，没有数据返回null
     * @return
     */
    TimeTableNameDO getNewestInfo();

    /**
     * 获取所有的tableName
     * @return
     */
    List<TimeTableNameDO> getTimeTableNameList();

    /**
     * 根据条码号或者分表信息
     * @param month
     * @return
     */
    TimeTableNameDO getTimeTableInfo(String month);
}
