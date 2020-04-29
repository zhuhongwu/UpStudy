package com.shadows.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhuhongwu
 * @date 2020/4/26
 */
@Data
@Accessors(chain = true)
public class MapperStatement {


    /**
     * 操作标识
     */
    private String opType;
    /**
     * id标识
     */
    private String id;
    /**
     * 返回值类型
     */
    String resultType;
    /**
     * 参数值类型
     */
    String paramterType;
    /**
     * sql语句
     */
    String sql;


}
