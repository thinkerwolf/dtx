package com.thinkerwolf.dtx.seata.bank1.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "dtx-bank2", fallback = Account2ClientFallback.class)
public interface Account2Client {

    /**
     * 向某个用户转账
     *
     * @param accountNo
     * @param amount
     * @return
     */
    @RequestMapping("/bank2/transfer")
    boolean transfer(@RequestParam("accountNo") String accountNo, @RequestParam("amount") Double amount);

}
