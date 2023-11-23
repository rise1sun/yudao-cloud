package cn.iocoder.yudao.module.wms.mq.consumer;

import cn.iocoder.yudao.module.wms.mq.message.TaskSendMessage;
import cn.iocoder.yudao.module.wms.service.task.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.function.Consumer;
import javax.annotation.Resource;

/**
 * @author jiangfeng
 * @date 2023/9/28
 */
@Component
@Slf4j
public class TaskSendConsumer implements Consumer<TaskSendMessage> {

    @Resource
    private TaskService taskService;


    @Override
    public void accept(TaskSendMessage taskSendMessage) {
        //TODO WCS调用物流线
        log.info("[accept][消息内容({})]", taskSendMessage);
    }
}
