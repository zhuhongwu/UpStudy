package com.shadows.pojo;

import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuhongwu
 * @date 2020/4/26
 */
@Data
public class Configuration {
    /**
     * 数据源
     */
    private DataSource dataSource;

    private Map<String, MapperStatement> mapperStatementMap = new HashMap<>();

}
