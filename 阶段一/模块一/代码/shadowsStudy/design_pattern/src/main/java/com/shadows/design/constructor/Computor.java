package com.shadows.design.constructor;

import lombok.Data;
import lombok.ToString;

/**
 * @author zhuhongwu
 * @date 2020/4/29
 */
@Data
@ToString
public class Computor {

    /**
     * 显示器
     */
    private String displayer;

    /**
     * 主机
     */
    private String mainUnit;

    /**
     * 键盘
     */
    private String mouse;

    /**
     * 键盘
     */
    private String keyboard;
}

