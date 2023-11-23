package cn.iocoder.yudao.module.wms.service.aspect;

import cn.iocoder.yudao.module.wms.dal.dataobject.tray.TrayDO;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author jiangfeng
 * @date 2023/7/14
 */
@Component
@Aspect
public class TrayAspect {

    /*@Before("execution(* cn.iocoder.yudao.module.wms.service.tray.TrayService.trayOccupy(..))")
    public void dobefore(JoinPoint joinPoint) {
        String tray = (String) joinPoint.getArgs()[0];
        System.out.println("方法名：" + joinPoint.getSignature().getName());
        System.out.println("参数列表：" + Arrays.toString(joinPoint.getArgs()));
        System.out.println("校验托盘信息");
    }*/

    @After("execution(* cn.iocoder.yudao.module.wms.service.tray.TrayService.trayOccupy(..))")
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("组托后，更新托盘缓存");
       // TrayDO trayDO = (TrayDO) joinPoint.getArgs()[0];
        // * String tray = (String) joinPoint.getArgs()[0];*//*
    }
}
