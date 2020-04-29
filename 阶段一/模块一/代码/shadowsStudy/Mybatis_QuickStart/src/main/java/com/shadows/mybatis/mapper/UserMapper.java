package com.shadows.mybatis.mapper;


import com.shadows.mybatis.pojo.User;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author zhuhongwu
 * @date 2020/4/27
 */
public interface UserMapper {

    List<User> findAllByRowBounds(RowBounds rowBounds);

    List<User> findAll();

    Integer insertUser(User user);

    User findOne(Integer id);

    List<User> findByCondition(int[] ids);

    User findOneByUser(User user);
}
