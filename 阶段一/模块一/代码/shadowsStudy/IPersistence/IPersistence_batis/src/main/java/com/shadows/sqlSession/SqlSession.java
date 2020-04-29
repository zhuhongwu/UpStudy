package com.shadows.sqlSession;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zhuhongwu
 * @date 2020/4/26
 */
public interface SqlSession {
    /**
     * 查询所有
     */
    <E> List<E> selectList(String statementId, Object... params) throws Exception;

    <E> E selectOne(String statementId, Object... params) throws Exception;

    <T> T getMapper(Class<?> classType);

    int insert(String statementId, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException;

    int delete(String statementId, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException;

    int update(String statementId, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException;


}
