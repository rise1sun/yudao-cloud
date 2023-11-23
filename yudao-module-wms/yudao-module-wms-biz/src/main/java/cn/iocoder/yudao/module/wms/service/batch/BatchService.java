package cn.iocoder.yudao.module.wms.service.batch;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wms.controller.admin.batch.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.batch.BatchDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 批次 Service 接口
 *
 * @author 管理员
 */
public interface BatchService {

    /**
     * 创建批次
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBatch(@Valid BatchCreateReqVO createReqVO);

    /**
     * 更新批次
     *
     * @param updateReqVO 更新信息
     */
    void updateBatch(@Valid BatchUpdateReqVO updateReqVO);

    /**
     * 删除批次
     *
     * @param id 编号
     */
    void deleteBatch(Long id);

    /**
     * 获得批次
     *
     * @param id 编号
     * @return 批次
     */
    BatchDO getBatch(Long id);

    /**
     * 获得批次列表
     *
     * @param ids 编号
     * @return 批次列表
     */
    List<BatchDO> getBatchList(Collection<Long> ids);

    /**
     * 获得批次分页
     *
     * @param pageReqVO 分页查询
     * @return 批次分页
     */
    PageResult<BatchDO> getBatchPage(BatchPageReqVO pageReqVO);

    /**
     * 获得批次列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 批次列表
     */
    List<BatchDO> getBatchList(BatchExportReqVO exportReqVO);

    /**
     * 获取批次工艺信息
     *
     * @return
     */
    Map<String, Object> getBatchAndFormulaInfo();

    /**
     * 获取当前使用中的批次信息
     * @return
     */
    BatchDO getBatch();
}
