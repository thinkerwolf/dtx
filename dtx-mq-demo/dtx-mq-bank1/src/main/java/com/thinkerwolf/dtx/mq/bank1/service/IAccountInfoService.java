package com.thinkerwolf.dtx.mq.bank1.service;

import com.thinkerwolf.dtx.common.OpResult;
import com.thinkerwolf.dtx.mq.bank1.event.AccountTransEvent;

public interface IAccountInfoService {
    OpResult sendAccountBalance(String fromAccountNo, String toAccountNo, Double amount);


    OpResult updateAccountBalance(AccountTransEvent event);

}
