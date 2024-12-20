package cn.bugstack.springframework.test.thirteen;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.factory.config.BeanPostProcessor;
import cn.bugstack.springframework.context.support.ClassPathXmlApplicationContext;
import cn.bugstack.springframework.test.thirteen.bean.IUserService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *
 * 作者：DerekYRC https://github.com/DerekYRC/mini-spring
 * @description 单元测试
 * @date 2022/3/14
 *
 *
 */
public class ApiTest {

    @Test
    public void test_scan() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:thirteen/spring-scan.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }

    @Test
    public void test_property() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:thirteen/spring-property.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService);
    }

    @Test
    public void test_beanPost(){

        BeanPostProcessor beanPostProcessor = new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return null;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return null;
            }
        };

        List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();
        beanPostProcessors.add(beanPostProcessor);
        beanPostProcessors.add(beanPostProcessor);
        beanPostProcessors.remove(beanPostProcessor);

        System.out.println(beanPostProcessors.size());
    }

}