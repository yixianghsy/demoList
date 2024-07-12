package com.hsy.TemplatePattern;

/**
 * 模板类
 * 声明为 final，无法被继承
 */
public final class Template {
    private void baseOperation(){
        System.out.println("模板类公共操作");
    }

    public void templateMethod(Callback callback){
        baseOperation();
        callback.customOperation();
    }
}