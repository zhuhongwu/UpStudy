package com.shadows.sqlSession;

import com.shadows.configuration.XMLConfigBuilder;
import com.shadows.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author zhuhongwu
 * @date 2020/4/26
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream in) throws PropertyVetoException, DocumentException {

        //解析配置  封装到configuration
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(in);

        //创建sqlsession

        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);


        return defaultSqlSessionFactory;
    }
}

