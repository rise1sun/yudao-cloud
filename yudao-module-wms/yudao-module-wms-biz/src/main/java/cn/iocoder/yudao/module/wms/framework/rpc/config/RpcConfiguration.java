package cn.iocoder.yudao.module.wms.framework.rpc.config;

/**
 * @author jiangfeng
 * @date 2023/7/31
 */

import cn.iocoder.yudao.module.system.api.dept.DeptApi;
import cn.iocoder.yudao.module.system.api.notify.NotifyMessageSendApi;
import cn.iocoder.yudao.module.system.api.oauth2.OAuth2TokenApi;
import cn.iocoder.yudao.module.system.api.permission.RoleApi;
import cn.iocoder.yudao.module.system.api.sms.SmsSendApi;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.wms.enums.api.tray.WmsApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {RoleApi.class, DeptApi.class, OAuth2TokenApi.class, AdminUserApi.class,
        SmsSendApi.class, WmsApi.class, NotifyMessageSendApi.class})
public class RpcConfiguration {
}