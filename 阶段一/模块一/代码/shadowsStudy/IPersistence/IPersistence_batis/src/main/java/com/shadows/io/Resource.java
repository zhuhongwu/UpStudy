package com.shadows.io;

import java.io.InputStream;

/**
 * @author zhuhongwu
 * @date 2020/4/26
 */
public class Resource {

    private Resource(){}

    public static InputStream getResourceAsStream(String path){
        return Resource.class.getClassLoader().getResourceAsStream(path);

    }

    public static void main(String[] args) {
        Resource.getResourceAsStream("");
    }
}
