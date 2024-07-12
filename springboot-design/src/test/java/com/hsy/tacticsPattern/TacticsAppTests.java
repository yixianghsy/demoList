package com.hsy.tacticsPattern;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class TacticsAppTests {

    @Resource
    VendorPaymentService vendorPaymentService;

    @Test
    void contextLoads() {

        PayOrder payOrder = new PayOrder();
        payOrder.setChannel("alipay");
        payOrder.setMete("100");
        payOrder.setPhone("123456");

        PayResult payResult = vendorPaymentService.pay(payOrder);
        System.out.println(payResult);
    }


}
