package cn.iocoder.yudao.module.wms.service.restingtask;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.copier.Copier;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.api.notify.NotifyMessageSendApi;

import cn.iocoder.yudao.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.OutboundDTO;
import cn.iocoder.yudao.module.wms.enums.common.Constants;
import cn.iocoder.yudao.module.wms.service.common.WmsService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.wms.controller.admin.restingtask.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.restingtask.RestingTaskDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.wms.convert.restingtask.RestingTaskConvert;
import cn.iocoder.yudao.module.wms.dal.mysql.restingtask.RestingTaskMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wms.enums.ErrorCodeConstants.*;

/**
 * 静置任务信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
@Slf4j
public class RestingTaskServiceImpl implements RestingTaskService {

    @Resource
    private RestingTaskMapper restingTaskMapper;

    @Resource
    private WmsService wmsService;

    @Resource
    private NotifyMessageSendApi notifyMessageSendApi;

    @Override
    public Integer createRestingTask(RestingTaskCreateReqVO createReqVO) {
        RestingTaskDO restingTask = RestingTaskConvert.INSTANCE.convert(createReqVO);
        restingTask.setStatus((byte) Constants.restingTaskStatus.SETTING.ordinal());
        //静置任务用mysql存储,用定时器的方式监测是否静置完成
        int insert = restingTaskMapper.insert(restingTask);
        return insert;
    }

    @Override
    public void updateRestingTask(RestingTaskUpdateReqVO updateReqVO) {
        // 校验存在
        validateRestingTaskExists(updateReqVO.getId());
        // 更新
        RestingTaskDO updateObj = RestingTaskConvert.INSTANCE.convert(updateReqVO);
        restingTaskMapper.updateById(updateObj);
    }

    @Override
    public void deleteRestingTask(Integer id) {
        // 校验存在
        validateRestingTaskExists(id);
        // 删除
        restingTaskMapper.deleteById(id);
    }

    private void validateRestingTaskExists(Integer id) {
        if (restingTaskMapper.selectById(id) == null) {
            throw exception(RESTING_TASK_NOT_EXISTS);
        }
    }

    @Override
    public RestingTaskDO getRestingTask(Integer id) {
        return restingTaskMapper.selectById(id);
    }

    @Override
    public List<RestingTaskDO> getRestingTaskList(Collection<Integer> ids) {
        return restingTaskMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<RestingTaskDO> getRestingTaskPage(RestingTaskPageReqVO pageReqVO) {
        return restingTaskMapper.selectPage(pageReqVO);
    }

    @Override
    public List<RestingTaskDO> getRestingTaskList(RestingTaskExportReqVO exportReqVO) {
        return restingTaskMapper.selectList(exportReqVO);
    }

    @Override
    public void executeTask() {
        List<RestingTaskDO> restingTaskS = getRestingTaskDOS(Constants.restingTaskStatus.SETTING.ordinal());

        List<RestingTaskDO> filteredList = restingTaskS.stream()
                .filter(restingTask -> {
                    LocalDateTime createTime = LocalDateTimeUtil.of(restingTask.getCreateTime());
                    LocalDateTime now = LocalDateTimeUtil.of(DateUtil.date());
                    long restingTime = Long.parseLong(restingTask.getRestingTime())*3600; // 假设restingTime为长整型字符串，表示秒数
                    long timeDifference = ChronoUnit.SECONDS.between(createTime, now);
                    return timeDifference > restingTime;
                })
                .collect(Collectors.toList());

        //TODO 后面考虑多线程执行出库，使用推荐库位来当分布式锁
        for (int i = 0; i < filteredList.size(); i++) {
            RestingTaskDO restingTaskDO = filteredList.get(i);
            OutboundDTO outboundDTO = new OutboundDTO();
            outboundDTO.setTray(restingTaskDO.getTrayNo());
            wmsService.outbound(outboundDTO);
        }
    }

    private List<CompletableFuture<Void>> getTasksFutures(List<RestingTaskDO> overTimeTasks) {
        List<CompletableFuture<Void>> overTimeTasksFutures = overTimeTasks.stream().map(task -> {
            // 出库
            CompletableFuture<Void> overTimeTasksFuture = CompletableFuture.runAsync(() -> {
                try {
                    wmsService.outbound(new OutboundDTO().setTray(task.getTrayNo()));
                } catch (Exception e) {
                    // 异常处理逻辑(异常任务手工处理)
                    log.error("出库异常：{}",e.getMessage());
                    updateTask(new LambdaUpdateWrapper<RestingTaskDO>()
                            .eq(RestingTaskDO::getId, task.getId())
                            .set(RestingTaskDO::getStatus, Constants.restingTaskStatus.ERROR)
                            .set(RestingTaskDO::getErrorInfo, e.getMessage()), task);
                    //发送站内信
                    //准备参数
                    List<Long> userIds = new ArrayList<>(); // 示例中写死，你可以改成你业务中的 userId 噢
                    userIds.add(1L);
                    userIds.add(2L);
                    userIds.add(3L);
                    String templateCode = "test_01"; // 站内信模版，记得在【站内信管理】中配置噢
                    Map<String, Object> templateParams = new HashMap<>();
                    templateParams.put("key1", "奥特曼");
                    templateParams.put("key2", "变身");

                    // 2. TODO 发送站内信
                 /*   notifyMessageSendApi.sendSingleMessageToAdmin(new NotifySendSingleToUserReqDTO()
                            .setUserIds(userIds).setTemplateCode(templateCode).setTemplateParams(templateParams));*/
                }
            });
            updateTask(new LambdaUpdateWrapper<RestingTaskDO>()
                    .eq(RestingTaskDO::getId, task.getId())
                    .set(RestingTaskDO::getRestingTime, DateUtil.now())
                    .set(RestingTaskDO::getStatus, Constants.restingTaskStatus.OUTBOUND), task);
            return overTimeTasksFuture;
        }).collect(Collectors.toList());
        return overTimeTasksFutures;
    }

    /**
     * 更新静置任务
     * @param lambdaUpdateWrapper
     * @param task
     */
    private void updateTask(LambdaUpdateWrapper<RestingTaskDO> lambdaUpdateWrapper, RestingTaskDO task) {
        LambdaUpdateWrapper<RestingTaskDO> restingTaskWrapper = lambdaUpdateWrapper;
        restingTaskMapper.update(task,restingTaskWrapper);
    }

    /**
     * 获取静置状态超时任务
     * @return
     */
    private List<RestingTaskDO> getRestingTaskDOS(Integer status) {
        LambdaQueryWrapperX<RestingTaskDO> restingTaskDOLambdaQueryWrapperX = new LambdaQueryWrapperX<RestingTaskDO>()
                .eqIfPresent(RestingTaskDO::getStatus, status);
        List<RestingTaskDO> restingTaskDOS = restingTaskMapper.selectList(restingTaskDOLambdaQueryWrapperX);
        return restingTaskDOS;
    }

}
