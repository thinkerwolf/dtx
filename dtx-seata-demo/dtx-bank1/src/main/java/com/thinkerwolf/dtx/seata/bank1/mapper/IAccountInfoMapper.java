package com.thinkerwolf.dtx.seata.bank1.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface IAccountInfoMapper {

    @Update("update account_info set account_balance = account_balance + #{amount} where account_no= #{accountNo}")
    int updateAccountBalance(@Param("accountNo") String accountNo, @Param("amount") Double
            amount);

}
