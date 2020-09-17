package com.thinkerwolf.dtx.tcc.bank2.service;

import com.thinkerwolf.dtx.common.OpResult;
import com.thinkerwolf.dtx.tcc.bank2.mapper.IAccountInfoMapper;
import com.thinkerwolf.dtx.tcc.bank2.mapper.ITccLogMapper;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountInfoService implements IAccountInfoService {

    @Autowired
    IAccountInfoMapper accountInfoMapper;

    @Autowired
    ITccLogMapper tccLogMapper;

    @Hmily(confirmMethod = "confirm_transfer", cancelMethod = "cancel_transfer")
    @Override
    public OpResult transfer(String accountNo, Double amount) {
        if (amount == null || amount <= 0) {
            throw new RuntimeException("转账数量不对");
        }
        if (amount > 100) {
            throw new RuntimeException("单次转账不可超过100");
        }
        return OpResult.ok();
    }

    @Transactional
    public OpResult confirm_transfer(String accountNo, Double amount) {
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        if (tccLogMapper.isConfirmExists(txNo) > 0) {
            return OpResult.fail("事务已经确认");
        }
        int num = accountInfoMapper.updateAccountBalance(accountNo, amount);
        tccLogMapper.addConfirm(txNo);
        return OpResult.ok();
    }

    public OpResult cancel_transfer(String accountNo, Double amount) {
        return OpResult.ok();
    }

}
