package com.thinkerwolf.dtx.seata.bank1.controller;

import com.thinkerwolf.dtx.seata.bank1.service.IAccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/bank1")
public class AccountInfoController {

    @Autowired
    IAccountInfoService accountInfoService;

    @GetMapping("/transfer")
    @ResponseBody
    public Map<String, Object> transfer(@RequestParam("fromAccountNo") String fromAccountNo,
                                        @RequestParam("toAccountNo") String toAccountNo,
                                        @RequestParam("amount") Double amount) {
        return accountInfoService.updateAccountBalance(fromAccountNo, toAccountNo, amount);
    }


}
