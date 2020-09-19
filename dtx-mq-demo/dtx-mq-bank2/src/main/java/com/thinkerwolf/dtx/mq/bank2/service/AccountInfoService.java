package com.thinkerwolf.dtx.mq.bank2.service;

import com.thinkerwolf.dtx.common.OpResult;
import com.thinkerwolf.dtx.mq.bank2.event.AccountTransEvent;
import com.thinkerwolf.dtx.mq.bank2.mapper.IAccountInfoMapper;
import com.thinkerwolf.dtx.mq.bank2.mapper.IDeDuplicationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountInfoService implements IAccountInfoService {

    @Autowired
    IAccountInfoMapper accountInfoMapper;
    @Autowired
    IDeDuplicationMapper deDuplicationMapper;

    @Override
    @Transactional
    public OpResult updateAccountBalance(AccountTransEvent event) {
        if (deDuplicationMapper.isTxExists(event.getTxNo()) > 0) {
            return OpResult.fail("Already commit");
        }
        accountInfoMapper.updateAccountBalance(event.getToAccountNo(), event.getAmount());
        deDuplicationMapper.addTx(event.getTxNo());
        return OpResult.ok();
    }


}
