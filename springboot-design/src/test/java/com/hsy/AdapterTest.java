package com.hsy;

import com.hsy.adapter.VoltageAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 适配器设计模式
 * 个人理解就是把不相关的类和接口 通过继承和实现来关联起来，
 * 实际开发中经常用
 */
@SpringBootTest
public class AdapterTest {
    @Test
    void contextLoads() {
        VoltageAdapter voltageAdapter = new VoltageAdapter();
        voltageAdapter.targetMethod();

    }
}
