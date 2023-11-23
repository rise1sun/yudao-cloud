package cn.iocoder.yudao.module.wms.service.fireprocess;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.wms.controller.admin.fireProcess.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.fireprocess.FireProcessDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.wms.convert.fireprocess.FireProcessConvert;
import cn.iocoder.yudao.module.wms.dal.mysql.fireprocess.FireProcessMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 消防任务 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class FireProcessServiceImpl implements FireProcessService {

    @Resource
    private FireProcessMapper fireProcessMapper;

    @Override
    public Long createFireProcess(FireProcessCreateReqVO createReqVO) {
        // 插入
        FireProcessDO fireProcess = FireProcessConvert.INSTANCE.convert(createReqVO);
        fireProcessMapper.insert(fireProcess);
        // 返回
        return fireProcess.getId();
    }

    @Override
    public void updateFireProcess(FireProcessUpdateReqVO updateReqVO) {
        // 校验存在
        validateFireProcessExists(updateReqVO.getId());
        // 更新
        FireProcessDO updateObj = FireProcessConvert.INSTANCE.convert(updateReqVO);
        fireProcessMapper.updateById(updateObj);
    }

    @Override
    public void deleteFireProcess(Long id) {
        // 校验存在
        validateFireProcessExists(id);
        // 删除
        fireProcessMapper.deleteById(id);
    }

    private void validateFireProcessExists(Long id) {
        if (fireProcessMapper.selectById(id) == null) {
            throw exception(404);
        }
    }

    @Override
    public FireProcessDO getFireProcess(Long id) {
        return fireProcessMapper.selectById(id);
    }

    @Override
    public List<FireProcessDO> getFireProcessList(Collection<Long> ids) {
        return fireProcessMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FireProcessDO> getFireProcessPage(FireProcessPageReqVO pageReqVO) {
        return fireProcessMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FireProcessDO> getFireProcessList(FireProcessExportReqVO exportReqVO) {
        return fireProcessMapper.selectList(exportReqVO);
    }

}
