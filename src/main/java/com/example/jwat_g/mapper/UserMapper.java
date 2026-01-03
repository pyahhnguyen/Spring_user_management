package com.example.jwat_g.mapper;

import com.example.jwat_g.model.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    List<User> getAllUser();

    User getUserById(@Param("id") Long id);

    void insertUser(User user);

    void updateUser(User user);

    void deleteUser(@Param("id") Long id);

    boolean isEmailExist(@Param("email") String email);

    List<User> searchUsers(@Param("search") String search,
            @Param("offset") int offset,
            @Param("limit") int limit);

    long countUsers(@Param("search") String search);
}
