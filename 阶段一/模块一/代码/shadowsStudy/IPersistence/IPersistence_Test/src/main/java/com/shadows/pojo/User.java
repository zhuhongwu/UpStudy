package com.shadows.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author zhuhongwu
 * @date 2020/4/25
 */
@Data
@Accessors(chain = true)
@ToString
@Setter
@Getter
public class User {

    private Integer id;

    private String username;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
