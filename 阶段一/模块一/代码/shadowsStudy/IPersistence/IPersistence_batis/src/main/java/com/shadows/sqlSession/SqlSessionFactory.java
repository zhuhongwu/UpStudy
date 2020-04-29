package com.shadows.sqlSession;

/**
 * @author zhuhongwu
 * @date 2020/4/26
 */
public interface SqlSessionFactory {

    public SqlSession openSession();
}
