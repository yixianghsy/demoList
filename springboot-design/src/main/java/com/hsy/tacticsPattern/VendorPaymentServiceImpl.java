package com.hsy.tacticsPattern;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 支付处理服务统一入口
 *
 * @Author Liurb
 * @Date 2022/11/26
 */
@Service
public class VendorPaymentServiceImpl implements VendorPaymentService {

    @Resource
    PaymentContextService paymentContextService;

    @Override
    public PayResult pay(PayOrder payOrder) {
        //获取订单中的渠道
        String channel = payOrder.getChannel();
        //根据渠道，具体选择所使用的支付处理类
        PaymentHandleService handleService = paymentContextService.getContext(channel);
        //调用该支付处理类的支付方法
        return handleService.pay(payOrder);
    }

}