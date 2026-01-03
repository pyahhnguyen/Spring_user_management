package com.example.jwat_g.mapper;

import com.example.jwat_g.model.RefreshToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RefreshTokenMapper {

    void save(RefreshToken refreshToken);

    RefreshToken findByToken(@Param("token") String token);

    void deleteByAccountId(@Param("accountId") Long accountId);

    void deleteByToken(@Param("token") String token);

    void deleteExpiredTokens();
}
