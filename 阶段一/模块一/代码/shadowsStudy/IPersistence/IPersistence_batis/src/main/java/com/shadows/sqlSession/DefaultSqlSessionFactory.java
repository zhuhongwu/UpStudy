package com.shadows.sqlSession;

import com.shadows.pojo.Configuration;

/**
 * @author zhuhongwu
 * @date 2020/4/26
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
