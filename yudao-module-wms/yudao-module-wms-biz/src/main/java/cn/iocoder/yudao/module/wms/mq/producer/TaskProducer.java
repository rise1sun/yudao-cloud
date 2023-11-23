package cn.iocoder.yudao.module.wms.mq.producer;

import cn.iocoder.yudao.module.wms.mq.message.TaskSendMessage;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jiangfeng
 * @date 2023/8/10
 */
@Component
public class TaskProducer {

    /*@Resource
    private StreamBridge streamBridge;*/

    public void sendTaskMessage(String trayNo, String startStorage, String startArea, Integer orderTaskId, String endStorage,String endArea) {
        TaskSendMessage message = new TaskSendMessage().setTrayNo(trayNo).setStartStorage(startStorage).setStartArea(startArea)
                        .setOrderTaskId(orderTaskId).setEndStorage(endStorage).setEndArea(endArea);
       // streamBridge.send("taskSend-out-0", message);
    }

}

