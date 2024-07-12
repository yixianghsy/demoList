package com.hsy;

import com.hsy.decorator.ChickenBurger;
import com.hsy.decorator.Chilli;
import com.hsy.decorator.Humburger;
import com.hsy.decorator.Lettuce;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DecoratorTest {
    @Test
    void  contextLoads() {
        Humburger humburger = new ChickenBurger();
        System.out.println(humburger.getName()+"  价钱："+humburger.getPrice());
        Lettuce lettuce = new Lettuce(humburger);
        System.out.println(lettuce.getName()+"  价钱："+lettuce.getPrice());
        Chilli chilli = new Chilli(humburger);
        System.out.println(chilli.getName()+"  价钱："+chilli.getPrice());
        Chilli chilli2 = new Chilli(lettuce);
        System.out.println(chilli2.getName()+"  价钱："+chilli2.getPrice());
    }
}
