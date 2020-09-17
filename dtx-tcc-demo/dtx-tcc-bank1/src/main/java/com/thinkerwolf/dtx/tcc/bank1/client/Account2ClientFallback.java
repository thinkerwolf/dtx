package com.thinkerwolf.dtx.tcc.bank1.client;

import com.thinkerwolf.dtx.common.OpResult;

public class Account2ClientFallback implements Account2Client {
    @Override
    public OpResult transfer(String accountNo, Double amount) {
        return OpResult.fail("转账Fallback");
    }
}
