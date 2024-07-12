package com.hsy.tacticsPattern;

import java.lang.annotation.*;

/**
 * 支付方式注解
 *
 * @Author Liurb
 * @Date 2022/11/26
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Payment {

    String value() default "";

}