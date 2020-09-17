package com.thinkerwolf.dtx.seata.bank1.service;

import com.thinkerwolf.dtx.seata.bank1.client.Account2Client;
import com.thinkerwolf.dtx.seata.bank1.mapper.IAccountInfoMapper;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
public class AccountInfoService implements IAccountInfoService {

    @Autowired
    Account2Client account2Client;

    @Autowired
    IAccountInfoMapper accountInfoMapper;

    @Override
    @Transactional
    @GlobalTransactional(name = "account-transfer-tx-group")
    public Map<String, Object> updateAccountBalance(String fromAccountNo, String toAccountNo, Double amount) {
        Map<String, Object> res = new LinkedHashMap<>();
        if (amount == null || amount <= 0) {
            res.put("state", -1);
            res.put("msg", "金额不对");
            return res;
        }
        accountInfoMapper.updateAccountBalance(fromAccountNo, -1 * amount);
        boolean suc = account2Client.transfer(toAccountNo, amount);
        if (!suc) {
            // 必须抛出
            res.put("state", -1);
            res.put("msg", "远程转账失败");
            return res;
//            throw new RuntimeException("远程转账失败");
        }
        res.put("state", 1);
        return res;
    }

}
