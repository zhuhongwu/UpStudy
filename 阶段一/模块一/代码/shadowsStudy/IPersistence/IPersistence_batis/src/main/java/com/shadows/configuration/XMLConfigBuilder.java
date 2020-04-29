package com.shadows.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.shadows.io.Resource;
import com.shadows.pojo.Configuration;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.xml.parsers.SAXParser;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.*;

/**
 * @author zhuhongwu
 * @date 2020/4/26
 */
public class XMLConfigBuilder {

    /**
     * 后续可能会用到 所以定义在外部
     */
    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();

    }

    /**
     * 解析配置文件 封装到Configuration{@link com.shadows.pojo.Configuration}
     */
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(inputStream);
        //获取到Configuration
        Element rootElement = document.getRootElement();
        List<Element> list = rootElement.selectNodes("//property");

        //解析获取数据源
        Properties properties = new Properties();

        list.forEach(element -> {

            String name = element.attributeValue("name");
            String value = element.attributeValue("value");

            properties.setProperty(name, value);

        });

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("userName"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));

        configuration.setDataSource(comboPooledDataSource);


        //2.解析mapper

        List<Element> mapperList = rootElement.selectNodes("//mapper");


        for (Element element : mapperList) {
            String mapperPath = element.attributeValue("resource");

            InputStream mapperInputStream = Resource.getResourceAsStream(mapperPath);

            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(mapperInputStream);


        }


        return configuration;
    }


}
