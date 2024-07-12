package com.hsy;

import com.hsy.appearance.Computer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppearanceTest {
    @Test
    void contextLoads3() {
        Computer computer = new Computer();
        computer.startup();
        computer.shutdown();
    }
}
