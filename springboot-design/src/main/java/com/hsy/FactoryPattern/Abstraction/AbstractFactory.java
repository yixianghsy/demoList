package com.hsy.FactoryMethodPattern.Abstraction;

import com.hsy.FactoryMethodPattern.Abstraction.Aircondition;
import com.hsy.FactoryMethodPattern.Abstraction.Engine;

//创建工厂的接口
public interface AbstractFactory {
    //制造发动机
    public Engine createEngine();
    //制造空调 
    public Aircondition createAircondition();
}  
