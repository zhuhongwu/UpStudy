传统jdbc:
		1.硬编码
		2.
自定义持久层框架：
		配置文件：
			sqlMapConfig.xml:存放数据库配置信息，mapper.xml全路径
			mapper.xml:存放sql配置信息。
		(1)加载配置文件：根据配置文件路径，形成字节流，存储在内存当中
			创建Resource类，InputStream getResourceAsStream(String path)
		(2) 创建两个javaBean，存放配置文件解析出的内容。
				Configuration:核心配置类->存放sqlMapConfig.xml的内容
				MapperStatement：映射配置类->存放mapper.xml解析出来的内容。
		（3）解析配置文件（dom4j）
				创建类：SqlSessionFactoryBuilder 方法： build(InputStream in)
				1.使用dom4j解析配置文件，将解析出来的内容封装到容器对象中。
				2.创建SqlSessionFactory对象。生产SqlSession对象：会话对象（采用工厂模式）
		（4）创建SqlSessionFactory接口以及实现类DefaultSqlSessionFactory
				1.openSession() 生产sqlSession
		（5）创建SqlSession 接口以及实现类DefaultSqlSession
				定义数据库的Crud操作。
		（6）创建Executor接口以及实现类SimpleExecutor
				query(Configuration ,MapperStatement, Object... params)指定sql语句

dao层存在statementId硬编码，通过jdk动态代理来解决。
Proxy类

起别名

	可以用typeAlias给全限定类
		<typeAliases>
			<typeAlias />
		</typeAliases>
或者
		<typeAliases>
			<package />
		</typeAliases>
动态sql

条件是数组 int[] 时，paramterType="list"也可以是paramterType = "int[]"

一级缓存：
		针对同一个sqlsession，相同条件的查询。做增删改操作时，并提交事务后，会刷新缓存。
		
二级缓存：针对于不同的sqlsession ，针对数据进行缓存到内存或硬盘中。执行增删改并提交事务会刷新二级缓存。
		可以实现分布式缓存  在<cache type = "自定义类"/>  mybatis-redis
		
mybatis插件原理：
		jdk动态代理（相当于做的一个拦截器）,主要针对Executor，StatementHandler,ParameterHandler,ResultSetHandler进行的拦截处理  
		
SqlSessionFactoryBuilder、SqlSessionFactory、SqlSession 三者最佳使用范围在方法级，即每个方面中均操作一次。其中sqlsession不能在不同的线程之前共享  线程不安全
	SqlSession在被spring事务管理的时候可以使用同一个sqlsession  不会产生线程不安全的问题。
		TransactionSynchronizationManager.getResource(sessionFactory)
　				这个方法，他的作用主要是在当前线程的事务管理中获取一个session的持有者。

Executor 
 三个常用的执行器：
			SimpleExecutor:默认
			BathchExecutor:批量执行更新 
			ResueExecutor: 和缓存相关
			
			
设计模式：

	构建者模式  builder ：将一个复杂复杂拆分成一个个简单对象构建起来。


