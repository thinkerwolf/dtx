package com.thinkerwolf.dtx.tcc.bank1.service;

import com.thinkerwolf.dtx.common.OpResult;

public interface IAccountInfoService {
    OpResult updateAccountBalance(String fromAccountNo, String toAccountNo, Double amount);
}
