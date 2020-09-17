package com.thinkerwolf.dtx.tcc.bank2.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ITccLogMapper {

    @Select("select count(tx_no) as n from local_try_log where tx_no = #{txNo}")
    int isTryExists(@Param("txNo") String txNo);

    @Select("select count(tx_no) as n from local_confirm_log where tx_no = #{txNo}")
    int isConfirmExists(@Param("txNo") String txNo);

    @Select("select count(tx_no) as n from local_cancel_log where tx_no = #{txNo}")
    int isCancelExists(@Param("txNo") String txNo);

    @Insert("insert into local_try_log values(#{txNo},now());")
    int addTry(String txNo);

    @Insert("insert into local_confirm_log values(#{txNo},now());")
    int addConfirm(String txNo);

    @Insert("insert into local_cancel_log values(#{txNo},now());")
    int addCancel(String txNo);

}
