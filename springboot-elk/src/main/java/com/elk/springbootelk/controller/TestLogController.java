package com.elk.springbootelk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 测试日志的Controller
 * </p>
 *
 * @author XiaoHH
 * @version 1.0.0
 * @date 2023-04-26 22:59:13
 * @file TestLogController.java
 */
@RestController
public class TestLogController {

    /**
     * 获取日志输出对象
     */
    private static final Logger log = LoggerFactory.getLogger(TestLogController.class);

    /**
     * 测试输出log的访问方法
     */
    @GetMapping("/testLog")
    public String testLog() {
        log.error("测试输出一个日志");
        return "success";
    }
}
