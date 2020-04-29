package com.shadows.mybatis.test;

import com.github.pagehelper.PageHelper;
import com.shadows.mybatis.mapper.IUserMapper;
import com.shadows.mybatis.pojo.User;
import com.shadows.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhuhongwu
 * @date 2020/4/27
 */
public class MyTest {


    @Test
    public void testMybatis() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> all = mapper.findAll();
        System.out.println(all);


    }

    @Test
    public void insertUser() throws IOException {

        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession();


        UserMapper mapper = sqlSession.getMapper(UserMapper.class);


        mapper.insertUser(new User().setId(4).setUsername("zzz"));

        //此处  mysql引擎 innodb 没有提交事务 数据依旧能够查询到  因为是sqlsession 处于同一事务当中
        // sqlSession.commit();
        List<User> all = mapper.findAll();
        all.forEach(System.out::println);
        sqlSession.close();


    }


    @Test
    public void testVar() throws IOException {

        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User one = mapper.findOne(1);

        //

        System.out.println(one);


    }

    @Test
    public void testForeach() throws IOException {


        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int[] ids = {1, 2};
        List<User> list = mapper.findByCondition(ids);
        list.forEach(System.out::println);

    }

    @Test
    public void testCache() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User one = mapper.findOne(1);
        System.out.println(one);
        System.out.println("------");
        System.out.println(mapper.findOne(1) == one);


    }


    @Test
    public void testSecCache() throws IOException {

        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession1 = build.openSession();
        SqlSession sqlSession2 = build.openSession();

        UserMapper mapper = sqlSession1.getMapper(UserMapper.class);


        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);

        User one = mapper.findOne(1);

        sqlSession1.close();

        User one1 = mapper2.findOne(1);
        System.out.println(one == one1);


    }


    @Test
    public void testPageHelp() throws IOException {

        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);


        PageHelper.startPage(1, 1);
        List<User> all = mapper.findAll();
        all.forEach(System.out::println);


    }


    @Test
    public void testCondition() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);


        User user = new User();
        user.setId(1);


        User oneByUser = mapper.findOneByUser(user);

        System.out.println(oneByUser);


    }



}
