package com.hsy.tactics;

/**
 * 支付处理上下文
 *
 * @Author Liurb
 * @Date 2022/11/26
 */
public interface PaymentContextService {

    /**
     * 获取处理上下文
     *
     * @param channel
     * @return
     */
    PaymentHandleService getContext(String channel);

}