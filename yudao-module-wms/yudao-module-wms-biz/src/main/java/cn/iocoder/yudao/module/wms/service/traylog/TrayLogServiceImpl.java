package cn.iocoder.yudao.module.wms.service.traylog;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.wms.controller.admin.traylog.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.traylog.TrayLogDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.wms.convert.traylog.TrayLogConvert;
import cn.iocoder.yudao.module.wms.dal.mysql.traylog.TrayLogMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wms.enums.ErrorCodeConstants.TRAY_LOG_NOT_EXISTS;

/**
 * wms托盘日志记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrayLogServiceImpl implements TrayLogService {

    @Resource
    private TrayLogMapper trayLogMapper;

    @Override
    public Integer createTrayLog(TrayLogCreateReqVO createReqVO) {
        // 插入
        TrayLogDO trayLog = TrayLogConvert.INSTANCE.convert(createReqVO);
        trayLogMapper.insert(trayLog);
        // 返回
        return trayLog.getId();
    }

    @Override
    public void updateTrayLog(TrayLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateTrayLogExists(updateReqVO.getId());
        // 更新
        TrayLogDO updateObj = TrayLogConvert.INSTANCE.convert(updateReqVO);
        trayLogMapper.updateById(updateObj);
    }

    @Override
    public void deleteTrayLog(Integer id) {
        // 校验存在
        validateTrayLogExists(id);
        // 删除
        trayLogMapper.deleteById(id);
    }

    private void validateTrayLogExists(Integer id) {
        if (trayLogMapper.selectById(id) == null) {
            throw exception(TRAY_LOG_NOT_EXISTS);
        }
    }

    @Override
    public TrayLogDO getTrayLog(Integer id) {
        return trayLogMapper.selectById(id);
    }

    @Override
    public List<TrayLogDO> getTrayLogList(Collection<Integer> ids) {
        return trayLogMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<TrayLogDO> getTrayLogPage(TrayLogPageReqVO pageReqVO) {
        return trayLogMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TrayLogDO> getTrayLogList(TrayLogExportReqVO exportReqVO) {
        return trayLogMapper.selectList(exportReqVO);
    }

    @Override
    public void insertTaryLog(TrayLogDO trayLogDO) {
        trayLogMapper.insert(trayLogDO);
    }

    @Override
    public TrayLogDO getTrayLogByTrayAndStorage(String trayNO, String storage, int serviceType) {
        LambdaQueryWrapperX<TrayLogDO> query = new LambdaQueryWrapperX<TrayLogDO>()
                .eqIfPresent(TrayLogDO::getTrayNo, trayNO)
                .eqIfPresent(TrayLogDO::getStartStorage, storage)
                .eqIfPresent(TrayLogDO::getServiceType, serviceType)
                .orderByDesc(TrayLogDO::getCreateTime);
        TrayLogDO trayLogDO = trayLogMapper.selectOne(query);
        return trayLogDO;
    }

    @Override
    public TrayLogDO getTrayLogByTray(String tray) {
        return trayLogMapper.getTrayLogByTray(tray);
    }

}
