package com.thinkerwolf.dtx.mq.bank1.service;

import com.thinkerwolf.dtx.common.OpResult;
import com.thinkerwolf.dtx.common.util.JsonUtil;
import com.thinkerwolf.dtx.mq.bank1.event.AccountTransEvent;
import com.thinkerwolf.dtx.mq.bank1.mapper.IAccountInfoMapper;
import com.thinkerwolf.dtx.mq.bank1.mapper.IDeDuplicationMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class AccountInfoService implements IAccountInfoService {

    @Autowired
    IAccountInfoMapper accountInfoMapper;

    @Autowired
    RocketMQTemplate rocketMQTemplate;
    @Autowired
    IDeDuplicationMapper deDuplicationMapper;

    @Override
    public OpResult sendAccountBalance(String fromAccountNo, String toAccountNo, Double amount) {
        if (amount == null || amount <= 0) {
            return OpResult.fail("无效的转账金额");
        }
        AccountTransEvent accountTransEvent = new AccountTransEvent();
        String txNo = UUID.randomUUID().toString();
        accountTransEvent.setTxNo(txNo);
        accountTransEvent.setFromAccountNo(fromAccountNo);
        accountTransEvent.setToAccountNo(toAccountNo);
        accountTransEvent.setAmount(amount);

        try {
            Map<String, Object> data = new HashMap<>(1, 1.0f);
            data.put("event", accountTransEvent);
            String json = JsonUtil.toJsonString(data);
            Message<String> message = MessageBuilder.withPayload(json).build();
            TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction("producer_tx_msg_bank1", "topic_tx_msg", message, null);
            return OpResult.ok();
        } catch (Exception e) {
            return OpResult.fail("服务器错误");
        }
    }

    @Override
    @Transactional
    public OpResult updateAccountBalance(AccountTransEvent event) {
        if (deDuplicationMapper.isTxExists(event.getTxNo()) > 0) {
            return OpResult.fail("Already commit");
        }
        accountInfoMapper.decrementAccountBalance(event.getFromAccountNo(), event.getAmount());
        deDuplicationMapper.addTx(event.getTxNo());
        return OpResult.ok();
    }


}
