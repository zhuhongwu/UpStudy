package com.shadows.design.factory;

/**
 * @author zhuhongwu
 * @date 2020/4/29
 */
public class ComputorFactory {


    public static Computor create(String type) {
        Computor computor = null;
        switch (type) {
            case "lenovo":
                computor = new LenovoComputor();
                break;
            case "HP":
                computor = new HpComputor();
                break;
            default:
                computor = new LenovoComputor();
                break;

        }
        return computor;

    }
}
