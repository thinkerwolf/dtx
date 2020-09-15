package com.thinkerwolf.dtx.bank1.client;

public class Account2ClientFallback implements Account2Client {
    @Override
    public boolean transfer(String accountNo, Double amount) {
        return false;
    }
}
