package com.hsy.tacticsPattern;

/**
 * 支付处理服务
 *
 * @Author Liurb
 * @Date 2022/11/26
 */
public interface PaymentHandleService {

    /**
     * 付款
     *
     * @param payOrder
     * @return
     */
    PayResult pay(PayOrder payOrder);

}