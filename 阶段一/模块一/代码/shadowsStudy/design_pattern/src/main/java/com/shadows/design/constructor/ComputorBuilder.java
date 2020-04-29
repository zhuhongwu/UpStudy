package com.shadows.design.constructor;

/**
 * @author zhuhongwu
 * @date 2020/4/29
 */
public class ComputorBuilder {

    private Computor computor = new Computor();

    private void installDispalyer(String dispalyer) {
        computor.setDisplayer(dispalyer);
    }

    private void installMainUnit(String mainUnit) {
        computor.setMainUnit(mainUnit);
    }

    private void installMouse(String mouse) {
        computor.setMouse(mouse);
    }

    private void installKeyBoard(String keyBoard) {
        computor.setKeyboard(keyBoard);
    }

    public Computor build() {
        return computor;
    }
}
