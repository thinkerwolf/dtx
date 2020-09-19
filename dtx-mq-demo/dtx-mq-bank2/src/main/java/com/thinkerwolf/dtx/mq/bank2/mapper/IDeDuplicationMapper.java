package com.thinkerwolf.dtx.mq.bank2.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface IDeDuplicationMapper {

    @Select("select count(tx_no) as n from de_duplication where tx_no = #{txNo}")
    int isTxExists(String txNo);

    @Insert("insert into de_duplication values(#{txNo},now());")
    void addTx(String txNo);

}
