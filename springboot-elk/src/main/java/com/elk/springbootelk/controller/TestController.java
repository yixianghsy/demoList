package com.elk.springbootelk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final static Logger logger= LoggerFactory.getLogger(TestController.class);
    @RequestMapping("/myTest")
    public void test(){
        logger.info("日志开始"+System.currentTimeMillis());
        logger.info("日志结束"+System.currentTimeMillis());
    }
}
