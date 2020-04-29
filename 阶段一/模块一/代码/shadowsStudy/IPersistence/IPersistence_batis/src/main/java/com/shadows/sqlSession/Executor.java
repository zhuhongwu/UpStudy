package com.shadows.sqlSession;

import com.shadows.pojo.Configuration;
import com.shadows.pojo.MapperStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zhuhongwu
 * @date 2020/4/26
 */
public interface Executor {

    <E> List<E> query(Configuration configuration, MapperStatement mapperStatement, Object... params) throws SQLException, Exception;

    int doUpData(Configuration configuration, MapperStatement mapperStatement, Object... params) throws SQLException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException;
}
