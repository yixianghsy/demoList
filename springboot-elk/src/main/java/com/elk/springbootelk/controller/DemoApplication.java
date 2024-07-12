package com.elk.springbootelk.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoApplication {

    Logger logger = LoggerFactory.getLogger("DemoApplication");

    @RequestMapping("/test/{name}")
    public String test1(@PathVariable String name){
        logger.info("用户名称注册成功66666, 用户名为: " +name);
        return name+"登录成功";
    }

}
