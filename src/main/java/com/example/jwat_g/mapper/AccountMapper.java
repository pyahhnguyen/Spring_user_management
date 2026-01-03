package com.example.jwat_g.mapper;

import com.example.jwat_g.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {

    Account findByEmail(@Param("email") String email);

    Account getAccountById(@Param("id") Long id);

    void insertAccount(Account account);

    boolean isEmailExist(@Param("email") String email);
}
