package com.thinkerwolf.dtx.tcc.bank2.controller;

import com.thinkerwolf.dtx.common.OpResult;
import com.thinkerwolf.dtx.tcc.bank2.service.IAccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank2")
public class AccountInfoController {

    @Autowired
    IAccountInfoService accountInfoService;

    @RequestMapping("/transfer")
    public OpResult transfer(@RequestParam("accountNo") String accountNo, @RequestParam("amount") Double amount) {
        return accountInfoService.transfer(accountNo, amount);
    }


}
