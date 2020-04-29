一、简单题

1、**Mybatis动态sql是做什么的？都有哪些动态sql？简述一下动态sql的执行原理？**
**答**：动态sql在mapperxml中配置动态标签如<if/> <where/>等 ，可以让让我们更具不同的标签组装不同的sql。
	动态sql：<sql></sql>
			<if></if>
			<where></where>
			<foreach></foreach>
			<trim></trim>
			<bind></bin>
			<choose></choose>
			<include></include>
			<set></set>
	原理：
			动态sql根据xml中动态标签解析到不同的sqlNode中并封装到SqlSource中，SqlSource传递到执行器中，执行器根据参数以及不同的占位符，使用
			标记处理类对sql进行解析封装成可执行sql。

2、**Mybatis是否支持延迟加载？如果支持，它的实现原理是什么？**
**答**：支持延迟加载（只针对于association关联对象和collection关联集合对象的延迟加载）
 实现原理：动态代理拦截后执行invoke方法。

3、**Mybatis都有哪些Executor执行器？它们之间的区别是什么？**
**答**：SimpleExecutor：每次执行一次Update或Query时都会创建一个Statement，并在结束操作后关闭Statement;
    ResueExecutor：每次执行Update或者Query时会先根据sql判断时候缓存有Statement，如果有直接使用，没有的话就创建;
	BatchExecutor：执行Update的时候会将Statement统一存储起来，并把sql统一缓存，然后执行addBatch(),Query时候没有批量处理，和SimpleExecutor类似。

4、**简述下Mybatis的一级、二级缓存（分别从存储结构、范围、失效场景。三个方面来作答）？**





**答**：一级缓存和二级缓存存储结构均为HashMap。
				                

|          | 范围             | 失效场景                                           |
| -------- | ---------------- | -------------------------------------------------- |
| 一级缓存 | 同一个SqlSession | 执行了增删改操作，进行了事务提交或者SqlSession关闭 |
| 二级缓存 | 同一个NameSpace  | 执行了增删改操作，进行了事务提交                   |
|          |                  |                                                    |

 	 

5、**简述Mybatis的插件运行原理，以及如何编写一个插件？**
**答**：原理：	
		JDK动态代理，针对mybatis的ParamterHandler、ResultSetHandler、Executor、Statement四个接口进行的拦截，为需要拦截的接口生成代理对象以及实现接口拦截功能。
		每次执行需要拦截的接口方法的时候，就会进去拦截方法，具体执行InvocationHandler的invoke方法。

	实现自定义插件，需要实现Plugin接口，并在自定义插件类上写上注解，具体到需要拦截的接口，方法，参数等。并在配置文件配置好自定义插件信息。
二、编程题

请完善自定义持久层框架IPersistence，在现有代码基础上添加、修改及删除功能。【需要采用getMapper方式】





无视频讲解；



设计思路：



  在解析mapper.xml的时候我们加入操作类型的标识，存入MapperStatement中，在getMapper中

通过statementId获取到相应的MapperStatement  获取操作类型  进入不同的操作。其中增删改都是执行exceute。所以在sqlsession中可以共用执行器中的一个方法实现就可以。



具体代码实现在IPersistence_batis工程中

 