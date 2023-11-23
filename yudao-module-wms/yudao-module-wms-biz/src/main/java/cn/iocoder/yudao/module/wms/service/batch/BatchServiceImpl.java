package cn.iocoder.yudao.module.wms.service.batch;

import cn.iocoder.yudao.module.wms.enums.batch.BatchStatusEnum;
import cn.iocoder.yudao.module.wms.enums.common.Constants;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.wms.controller.admin.batch.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.batch.BatchDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.wms.convert.batch.BatchConvert;
import cn.iocoder.yudao.module.wms.dal.mysql.batch.BatchMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wms.enums.ErrorCodeConstants.*;

/**
 * 批次 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class BatchServiceImpl implements BatchService {

    @Resource
    private BatchMapper batchMapper;

    @Override
    public Long createBatch(BatchCreateReqVO createReqVO) {
        if(BatchStatusEnum.RUNNING.getStatus().byteValue() == (createReqVO.getStatus())){
            //只允许一个批次状态为使用中
            LambdaUpdateWrapper<BatchDO> batchDOLambdaUpdateWrapper = new LambdaUpdateWrapper<BatchDO>().set(BatchDO::getStatus, BatchStatusEnum.NORMAL.getStatus())
                    .eq(BatchDO::getStatus, BatchStatusEnum.RUNNING.getStatus());
            batchMapper.update(new BatchDO(),batchDOLambdaUpdateWrapper);
        }
        // 插入
        BatchDO batch = BatchConvert.INSTANCE.convert(createReqVO);
        batchMapper.insert(batch);
        // 返回
        return batch.getId();
    }

    @Override
    public void updateBatch(BatchUpdateReqVO updateReqVO) {
        // 校验存在
        validateBatchExists(updateReqVO.getId());

        //更新
        BatchDO updateObj = BatchConvert.INSTANCE.convert(updateReqVO);
        if(BatchStatusEnum.RUNNING.getStatus().byteValue() == (updateReqVO.getStatus())){
            //只允许一个批次状态为使用中
            LambdaUpdateWrapper<BatchDO> batchDOLambdaUpdateWrapper = new LambdaUpdateWrapper<BatchDO>().set(BatchDO::getStatus, BatchStatusEnum.NORMAL.getStatus())
                    .eq(BatchDO::getStatus, BatchStatusEnum.RUNNING.getStatus());
            batchMapper.update(new BatchDO(),batchDOLambdaUpdateWrapper);
        }
        batchMapper.updateById(updateObj);
    }

    @Override
    public void deleteBatch(Long id) {
        // 校验存在
        validateBatchExists(id);
        // 删除
        batchMapper.deleteById(id);
    }

    private void validateBatchExists(Long id) {
        if (batchMapper.selectById(id) == null) {
            throw exception(BATCH_NOT_EXISTS);
        }
    }

    @Override
    public BatchDO getBatch(Long id) {
        return batchMapper.selectById(id);
    }

    @Override
    public List<BatchDO> getBatchList(Collection<Long> ids) {
        return batchMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<BatchDO> getBatchPage(BatchPageReqVO pageReqVO) {
        return batchMapper.selectPage(pageReqVO);
    }

    @Override
    public List<BatchDO> getBatchList(BatchExportReqVO exportReqVO) {
        return batchMapper.selectList(exportReqVO);
    }

    @Override
    public Map<String, Object> getBatchAndFormulaInfo() {
        return batchMapper.getBatchAndFormulaInfo();
    }

    @Override
    public BatchDO getBatch() {
        LambdaQueryWrapper<BatchDO> lambdaQueryWrapper = new LambdaQueryWrapper<BatchDO>().eq(BatchDO::getStatus, Constants.USE_BATCH);
        BatchDO batchDO = batchMapper.selectOne(lambdaQueryWrapper);
        return batchDO;
    }

}
