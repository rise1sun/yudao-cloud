package cn.iocoder.yudao.module.wms.job.restingTaskJob;

import cn.iocoder.yudao.framework.tenant.core.job.TenantJob;
import cn.iocoder.yudao.module.wms.service.restingtask.RestingTaskService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jiangfeng
 * @date 2023/7/24
 */
@Component
public class RestingTaskJob {

    @Resource
    private RestingTaskService restingTaskService;

    @XxlJob("restingTaskJob")
    public void execute() {
        restingTaskService.executeTask();
    }
}

