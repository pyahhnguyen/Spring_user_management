package com.example.jwat_g.mapper;

import com.example.jwat_g.model.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapperDb {

    List<User> getAllUser();

    User getUserById(@Param("id") Long id);

    void insertUser(User user);

    void updateUser(User user);

    void deleteUser(@Param("id") Long id);

    boolean isUsernameExist(@Param("username") String username);

    boolean isEmailExist(@Param("email") String email);

}
