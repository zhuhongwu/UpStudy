package com.shadows.sqlSession;

import com.shadows.io.Resource;
import com.shadows.pojo.Configuration;
import com.shadows.pojo.MapperStatement;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zhuhongwu
 * @date 2020/4/26
 */
public class DefaultSqlSession implements SqlSession {


    private Configuration configuration;

    DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {

        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);


        Executor executor = new SimpleExecutor();


        return executor.query(configuration, mapperStatement, params);
    }

    @Override
    public <E> E selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        int size = objects.size();

        if (size == 1) {
            return (E) objects.get(0);
        } else if (size == 0) {
            return null;
        }
        throw new RuntimeException("查询到多条数据");

    }

    @Override
    public <T> T getMapper(Class<?> classType) {

        //获取mapper  动态代理
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{classType}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                String nameSpace = method.getDeclaringClass().getName();


                //方法名
                String methodName = method.getName();
                //StatementId
                String statementId = nameSpace + "." + methodName;


                MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);

                String opType = mapperStatement.getOpType();


                switch (opType) {

                    case "insert":
                        return insert(statementId, args);
                    case "update":
                        return update(statementId, args);
                    case "delete":
                        return delete(statementId, args);
                    case "select":
                        //获取返回值类型
                        Type genericReturnType = method.getGenericReturnType();
                        if (genericReturnType instanceof ParameterizedType) {

                            return selectList(statementId, args);

                        }


                        return selectOne(statementId, args);
                    default:
                        throw new RuntimeException("操作标识错误【" + opType + "】");
                }


            }
        });
        return (T) proxyInstance;
    }

    @Override
    public int insert(String statementId, Object... params) throws
            ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {

        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);


        Executor executor = new SimpleExecutor();


        return executor.doUpData(configuration, mapperStatement, params);
    }

    @Override
    public int delete(String statementId, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);


        Executor executor = new SimpleExecutor();
        return executor.doUpData(configuration, mapperStatement, params);
    }

    @Override
    public int update(String statementId, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);


        Executor executor = new SimpleExecutor();
        return executor.doUpData(configuration, mapperStatement, params);
    }
}
