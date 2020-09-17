package com.thinkerwolf.dtx.tcc.bank1.service;

import com.thinkerwolf.dtx.common.OpResult;
import com.thinkerwolf.dtx.tcc.bank1.client.Account2Client;
import com.thinkerwolf.dtx.tcc.bank1.mapper.IAccountInfoMapper;
import com.thinkerwolf.dtx.tcc.bank1.mapper.ITccLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountInfoService implements IAccountInfoService {

    @Autowired
    Account2Client account2Client;

    @Autowired
    IAccountInfoMapper accountInfoMapper;

    @Autowired
    ITccLogMapper tccLogMapper;

    /**
     * Try 幂等校验
     * Try 悬挂处理
     *
     * @param fromAccountNo 发起转账账号
     * @param toAccountNo   接受转账账号
     * @param amount        转账钱数
     * @return 操作结果
     */
    @Override
    @Transactional
    // 被@Hmily注解的方法就是Try方法
    @Hmily(confirmMethod = "confirm_updateAccountBalance", cancelMethod = "cancel_updateAccountBalance")
    public OpResult updateAccountBalance(String fromAccountNo, String toAccountNo, Double amount) {
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();

        // Try幂等校验
        if (tccLogMapper.isTryExists(txNo) > 0) {
            return OpResult.fail("Try已经执行过了,txNo:" + txNo);
        }
        // Try悬挂校验
        if (tccLogMapper.isConfirmExists(txNo) > 0 || tccLogMapper.isCancelExists(txNo) > 0) {
            return OpResult.fail("第二阶段已经完成,txNo:" + txNo);
        }

        if (accountInfoMapper.decrementAccountBalance(fromAccountNo, amount) <= 0) {
            throw new RuntimeException("Try扣减失败,txNo:" + txNo);
        }
        // 增加Try操作记录
        tccLogMapper.addTry(txNo);

        // 远程调用
        account2Client.transfer(toAccountNo, amount);

        return OpResult.ok();
    }

    /**
     * Confirm方法
     */
    public OpResult confirm_updateAccountBalance(String fromAccountNo, String toAccountNo, Double amount) {
//        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
//        // Confirm 幂等校验
//        if (tccLogMapper.isConfirmExists(txNo) > 0) {
//            return OpResult.fail("Confirm已经执行过了,txNo:" + txNo);
//        }
//        tccLogMapper.addConfirm(txNo);
        return OpResult.ok();
    }

    /**
     * Cancel方法
     */
    @Transactional
    public OpResult cancel_updateAccountBalance(String fromAccountNo, String toAccountNo, Double amount) {
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();

        // Cancel幂等校验
        if (tccLogMapper.isCancelExists(txNo) > 0) {
            return OpResult.fail("Cancel已经执行过了,txNo:" + txNo);
        }

        // 空回滚
        if (tccLogMapper.isTryExists(txNo) <= 0) {
            return OpResult.fail("Try还未执行过,txNo:" + txNo);
        }
        accountInfoMapper.updateAccountBalance(fromAccountNo, amount);

        tccLogMapper.addCancel(txNo);

        return OpResult.ok();
    }

}
