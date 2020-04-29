package com.shadows.sqlSession;

import com.shadows.configuration.BoundSql;
import com.shadows.pojo.Configuration;
import com.shadows.pojo.MapperStatement;
import com.shadows.utils.GenericTokenParser;
import com.shadows.utils.ParameterMapping;
import com.shadows.utils.ParameterMappingTokenHandler;

import javax.sql.DataSource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuhongwu
 * @date 2020/4/26
 */
public class SimpleExecutor implements Executor {
    @Override
    public <E> List<E> query(Configuration configuration, MapperStatement mapperStatement, Object... params) throws Exception {

        //注册驱动 获取连接

        DataSource dataSource = configuration.getDataSource();
        Connection connection = dataSource.getConnection();
        //获取sql
        String sql = mapperStatement.getSql();
        //转换sql语句，并对#{}中的值进行解析存储
        //思想->等待调试
        BoundSql boundSql = getBoundSql(sql);

        //获取预处理对象

        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getParseSql());


        //设置参数 利用反射
        String paramterType = mapperStatement.getParamterType();
        Class<?> paramClass = getClassType(paramterType);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            if (paramClass == null) {
                throw new RuntimeException("缺少paramterType");
            }
            String content = parameterMappings.get(i).getContent();
            Field declaredField = paramClass.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1, o);
        }

        //封装返回参数
        String resultType = mapperStatement.getResultType();
        Class<?> resultClassType = getClassType(resultType);


        ResultSet resultSet = preparedStatement.executeQuery();
        List result = new ArrayList<>();
        while (resultSet.next()) {
            Object o = resultClassType.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {

                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(columnName);

                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultClassType);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, value);

            }
            result.add(o);

        }

        return result;


    }

    @Override
    public int doUpData(Configuration configuration, MapperStatement mapperStatement, Object... params) throws SQLException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {

        //获取数据连接
        DataSource dataSource = configuration.getDataSource();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            //获取sql
            String sql = mapperStatement.getSql();
            //转换sql语句，并对#{}中的值进行解析存储

            BoundSql boundSql = getBoundSql(sql);

            //获取预处理
            PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getParseSql());

            String paramterType = mapperStatement.getParamterType();
            Class<?> paramClass = getClassType(paramterType);
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            for (int i = 0; i < parameterMappings.size(); i++) {
                if (paramClass == null) {
                    throw new RuntimeException("缺少paramterType");
                }
                String content = parameterMappings.get(i).getContent();
                Field declaredField = paramClass.getDeclaredField(content);
                declaredField.setAccessible(true);
                Object o = declaredField.get(params[0]);
                preparedStatement.setObject(i + 1, o);
            }

            preparedStatement.execute();

            int updateCount = preparedStatement.getUpdateCount();

            return updateCount;


        } catch (Exception e) {

            try {

                connection.rollback();
            } catch (Exception e1) {
                throw e;
            }


        } finally {
            try {

                if (!connection.getAutoCommit()) {
                    connection.commit();
                }

            } catch (Exception e) {
                throw e;
            }

        }


        return 0;
    }

    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        if (null != paramterType || "".equals(paramterType)) {
            Class<?> aClass = Class.forName(paramterType);
            return aClass;
        }
        return null;


    }

    private BoundSql getBoundSql(String sql) {
        //标记处理类：配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //解析出来的sql
        String parseSql = genericTokenParser.parse(sql);
        //#{}里面解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);
        return boundSql;
    }
}
