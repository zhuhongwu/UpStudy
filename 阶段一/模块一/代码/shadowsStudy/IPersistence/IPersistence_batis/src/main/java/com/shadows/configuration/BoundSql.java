package com.shadows.configuration;

import com.shadows.utils.ParameterMapping;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author zhuhongwu
 * @date 2020/4/26
 */
@Data
@AllArgsConstructor
public class BoundSql {
    private String parseSql;

    private List<ParameterMapping> parameterMappings;
}
