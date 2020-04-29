package com.shadows.mybatis.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

/**
 * @author zhuhongwu
 * @date 2020/4/28
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class MyPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        System.out.println("能力增强代理");
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {


        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {


        System.out.println("配置信息是：" + properties);
    }
}
