package com.shadows.mybatis.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author zhuhongwu
 * @date 2020/4/27
 */
@Data
@Accessors(chain = true)
@Table(name = "user")
public class User  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;
}
