package com.mall.springbootmybatis.mapper;

import com.mall.springbootmybatis.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    /**
     * 批量插入用户
     * @param userList
     */
    void batchInsertUser(@Param("list") List<User> userList);
    /**
     * 新增单个用户
     * @param user
     */
    void insertUser(User user);

}