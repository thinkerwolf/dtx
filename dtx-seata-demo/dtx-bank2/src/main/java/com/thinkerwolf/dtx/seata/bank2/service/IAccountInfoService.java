package com.thinkerwolf.dtx.seata.bank2.service;

public interface IAccountInfoService {

    boolean transfer(String accountNo, Double amount);

}
