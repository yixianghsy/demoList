package cn.bugstack.springframework.test.twentyOne.dao;

import cn.bugstack.springframework.test.twentyOne.po.User;

public interface IUserDao {

     User queryUserInfoById(Long id);

}
