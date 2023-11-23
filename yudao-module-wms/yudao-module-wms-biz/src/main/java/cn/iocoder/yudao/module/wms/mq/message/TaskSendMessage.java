package cn.iocoder.yudao.module.wms.mq.message;

import cn.iocoder.yudao.framework.common.core.KeyValue;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author jiangfeng
 * @date 2023/9/28
 */
@Data
public class TaskSendMessage {

    /**
     * 托盘号
     */
    @NotNull(message = "托盘号不能为空")
    private String trayNo;
    /**
     * 起点库位
     */
    @NotNull(message = "起点库位不能为空")
    private String startStorage;

    /**
     * 起点区域
     */
    @NotNull(message = "起点区域不能为空")
    private String startArea;

    /**
     * 终点区域
     */
    @NotNull(message = "终点区域不能为空")
    private String endArea;
    /**
     * 终点库位
     */
    @NotNull(message = "终点库位不能为空")
    private String endStorage;
    /**
     * 订单任务号
     */
    @NotNull(message = "订单任务号不能为空")
    private Integer orderTaskId;
    /**
     * 模板参数
     */
   // private List<KeyValue<String, Object>> templateParams;

}

