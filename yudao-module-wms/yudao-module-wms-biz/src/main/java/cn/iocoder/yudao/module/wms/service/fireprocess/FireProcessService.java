package cn.iocoder.yudao.module.wms.service.fireprocess;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wms.controller.admin.fireProcess.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.fireprocess.FireProcessDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 消防任务 Service 接口
 *
 * @author 管理员
 */
public interface FireProcessService {

    /**
     * 创建消防任务
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFireProcess(@Valid FireProcessCreateReqVO createReqVO);

    /**
     * 更新消防任务
     *
     * @param updateReqVO 更新信息
     */
    void updateFireProcess(@Valid FireProcessUpdateReqVO updateReqVO);

    /**
     * 删除消防任务
     *
     * @param id 编号
     */
    void deleteFireProcess(Long id);

    /**
     * 获得消防任务
     *
     * @param id 编号
     * @return 消防任务
     */
    FireProcessDO getFireProcess(Long id);

    /**
     * 获得消防任务列表
     *
     * @param ids 编号
     * @return 消防任务列表
     */
    List<FireProcessDO> getFireProcessList(Collection<Long> ids);

    /**
     * 获得消防任务分页
     *
     * @param pageReqVO 分页查询
     * @return 消防任务分页
     */
    PageResult<FireProcessDO> getFireProcessPage(FireProcessPageReqVO pageReqVO);

    /**
     * 获得消防任务列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 消防任务列表
     */
    List<FireProcessDO> getFireProcessList(FireProcessExportReqVO exportReqVO);

}
