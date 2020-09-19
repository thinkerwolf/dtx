package com.thinkerwolf.dtx.mq.bank2.message;

import com.thinkerwolf.dtx.common.util.JsonUtil;
import com.thinkerwolf.dtx.mq.bank2.event.AccountTransEvent;
import com.thinkerwolf.dtx.mq.bank2.service.IAccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "consumer_group_tx_msg_bank2", topic = "topic_tx_msg")
@Slf4j
public class TxMsgConsumerListener implements RocketMQListener<String> {

    @Autowired
    IAccountInfoService accountInfoService;

    @Override
    public void onMessage(String message) {
        try {
            AccountTransEvent event = JsonUtil.toObject(message, "event", AccountTransEvent.class);
            accountInfoService.updateAccountBalance(event);
        } catch (Exception e) {
            log.error("Error when handle message", e);
        }

    }
}
