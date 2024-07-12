package com.hsy.FactoryPattern.Abstraction;

//创建工厂的接口
public interface AbstractFactory {
    //制造发动机
    public Engine createEngine();
    //制造空调 
    public Aircondition createAircondition();
}  
