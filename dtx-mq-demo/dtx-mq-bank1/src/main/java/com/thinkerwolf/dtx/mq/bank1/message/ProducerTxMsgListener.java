package com.thinkerwolf.dtx.mq.bank1.message;

import com.thinkerwolf.dtx.common.util.JsonUtil;
import com.thinkerwolf.dtx.mq.bank1.event.AccountTransEvent;
import com.thinkerwolf.dtx.mq.bank1.mapper.IDeDuplicationMapper;
import com.thinkerwolf.dtx.mq.bank1.service.IAccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RocketMQTransactionListener(txProducerGroup = "producer_tx_msg_bank1")
@Slf4j
public class ProducerTxMsgListener implements RocketMQLocalTransactionListener {

    @Autowired
    IDeDuplicationMapper deDuplicationMapper;

    @Autowired
    IAccountInfoService accountInfoService;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {

        try {
            String json = new String((byte[]) msg.getPayload());
            AccountTransEvent event = JsonUtil.toObject(json, "event", AccountTransEvent.class);
            accountInfoService.updateAccountBalance(event);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("", e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    @Transactional
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        try {
            String json = new String((byte[]) msg.getPayload());
            AccountTransEvent event = JsonUtil.toObject(json, "event", AccountTransEvent.class);

            if (deDuplicationMapper.isTxExists(event.getTxNo()) > 0) {
                return RocketMQLocalTransactionState.COMMIT;
            } else {
                return RocketMQLocalTransactionState.UNKNOWN;
            }
        } catch (Exception e) {
            log.error("", e);
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }
}
