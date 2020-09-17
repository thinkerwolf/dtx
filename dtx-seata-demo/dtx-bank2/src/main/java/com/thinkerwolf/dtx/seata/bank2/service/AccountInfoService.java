package com.thinkerwolf.dtx.seata.bank2.service;

import com.thinkerwolf.dtx.seata.bank2.mapper.IAccountInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountInfoService implements IAccountInfoService {

    @Autowired
    IAccountInfoMapper accountInfoMapper;

    @Transactional
    @Override
    public boolean transfer(String accountNo, Double amount) {
        if (amount == null || amount < 0) {
            return false;
        }
        if (amount > 100) {
            throw new RuntimeException("单次转账不可超过100");
        }
        accountInfoMapper.updateAccountBalance(accountNo, amount);
        return true;
    }
}
