package com.thinkerwolf.dtx.seata.bank1.service;

import java.util.Map;

public interface IAccountInfoService {
    Map<String, Object> updateAccountBalance(String fromAccountNo, String toAccountNo, Double amount);
}
