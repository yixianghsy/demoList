package com.hsy.tactics;

/**
 * 支付处理服务统一入口
 *
 * @Author Liurb
 * @Date 2022/11/26
 */
public interface VendorPaymentService {

    /**
     * 付款
     *
     * @param payOrder
     * @return
     */
    PayResult pay(PayOrder payOrder);

}