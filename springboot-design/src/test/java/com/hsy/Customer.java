package com.hsy;
import com.hsy.FactoryPattern.Abstraction.FactoryBMW320;
import com.hsy.FactoryPattern.Abstraction.FactoryBMW523;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Customer {
    /**
     * 简单工厂模式
     */
//    @Test
//    void  contextLoads() {
//        Factory factory = new Factory();
//        BMW bmw320 = factory.createBMW(320);
//        BMW bmw523 = factory.createBMW(523);
//    }
    /**
     * 工厂方法模式：
     */
//    @Test
//    void contextLoads2() {
//        FactoryBMW320 factoryBMW320 = new FactoryBMW320();
//        BMW320 bmw320 = factoryBMW320.createBMW();
//
//        FactoryBMW523 factoryBMW523 = new FactoryBMW523();
//        BMW523 bmw523 = factoryBMW523.createBMW();
//
//    }
    /**
     * 抽象工厂模式：
     */
    @Test
    void contextLoads3() {
        //生产宝马320系列配件
        FactoryBMW320 factoryBMW320 = new FactoryBMW320();
        factoryBMW320.createEngine();
        factoryBMW320.createAircondition();

        //生产宝马523系列配件
        FactoryBMW523 factoryBMW523 = new FactoryBMW523();
        factoryBMW523.createEngine();
        factoryBMW523.createAircondition();


    }
}
