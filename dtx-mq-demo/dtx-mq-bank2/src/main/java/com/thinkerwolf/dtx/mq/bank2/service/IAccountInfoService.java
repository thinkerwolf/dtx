package com.thinkerwolf.dtx.mq.bank2.service;

import com.thinkerwolf.dtx.common.OpResult;
import com.thinkerwolf.dtx.mq.bank2.event.AccountTransEvent;

public interface IAccountInfoService {

    OpResult updateAccountBalance(AccountTransEvent event);

}
