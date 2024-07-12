package com.hsy;

import com.hsy.TemplatePattern.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TemplateClient {
    /**
     * 模板模式调用
     */
    @Test
     void contextLoads() {
        AbstractClass abstractClass1 = new ConcreteClass1();
        AbstractClass abstractClass2 = new ConcreteClass2();
        applyTemplate(abstractClass1);
        applyTemplate(abstractClass2);
    }

    /**
     * 利用回调函数代替子类继承
     */
    @Test
    void contextLoads2() {
        Template template = new Template();
        applyTemplate(template);
    }

    public static void applyTemplate(AbstractClass abstractClass){
        abstractClass.templateMethod();
    }

    public static void applyTemplate(Template template){
        Callback callback = new SubCallback();
        template.templateMethod(callback);
    }
}
