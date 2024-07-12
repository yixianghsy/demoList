package com.hsy;

import com.hsy.prototype.DeepClone;
import com.hsy.prototype.ShallowClone;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class prototypeTest {
    @Test
    void contextLoads() {
        ShallowClone cp = new ShallowClone();
        ShallowClone clonecp = (ShallowClone) cp.clone();
        clonecp.show();
        System.out.println(clonecp.list == cp.list);

        DeepClone cp2 = new DeepClone();
        DeepClone clonecp2 = (DeepClone) cp2.clone();
        clonecp2.show();
        System.out.println(clonecp2.list == cp2.list);
    }
}
