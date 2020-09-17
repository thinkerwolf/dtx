package com.thinkerwolf.dtx.tcc.bank2.service;

import com.thinkerwolf.dtx.common.OpResult;

public interface IAccountInfoService {

    OpResult transfer(String accountNo, Double amount);

}
