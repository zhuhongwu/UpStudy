package com.shadows.configuration;

import com.shadows.pojo.Configuration;
import com.shadows.pojo.MapperStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author zhuhongwu
 * @date 2020/4/26
 */
public class XMLMapperBuilder {
    private Configuration configuration;


    private static final String[] opTypes;

    static {
        opTypes = new String[]{"select", "insert", "delete", "update"};
    }

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();

        String namespace = rootElement.attributeValue("namespace");


        //解析 节点 增加操作类型

        for (String opType : XMLMapperBuilder.opTypes) {

            List<Element> list = rootElement.selectNodes("//" + opType);
            list.forEach(element -> {
                String id = element.attributeValue("id");
                String resultType = element.attributeValue("resultType");
                String paramterType = element.attributeValue("paramterType");
                String sql = element.getTextTrim();
                MapperStatement mapperStatement = new MapperStatement().setId(id).setResultType(resultType).setParamterType(paramterType).setSql(sql).setOpType(opType);

                configuration.getMapperStatementMap().put(namespace + "." + id, mapperStatement);

            });
        }


    }
}
