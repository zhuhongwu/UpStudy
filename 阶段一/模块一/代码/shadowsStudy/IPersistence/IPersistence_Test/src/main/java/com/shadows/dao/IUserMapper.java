package com.shadows.dao;

import com.shadows.pojo.User;

import java.util.List;

/**
 * @author zhuhongwu
 * @date 2020/4/27
 */
public interface IUserMapper {

    List<User> findAll();

    User findOne(User user);


    int insertUser(User user);

    void updateUser(User user);

    void deleterUser(User user);
}
