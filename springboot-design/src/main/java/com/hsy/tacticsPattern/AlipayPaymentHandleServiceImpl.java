package com.hsy.tacticsPattern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 支付宝支付处理
 *
 * @Author Liurb
 * @Date 2022/11/26
 */
@Slf4j
@Payment("alipay")
@Service
public class AlipayPaymentHandleServiceImpl implements PaymentHandleService {

    @Override
    public PayResult pay(PayOrder payOrder) {

        PayResult result = new PayResult();
        result.setOrder("alipay_202211261234567890");
        result.setCode(1);

        log.info("支付宝支付处理 订单信息:{} 支付结果:{}", payOrder, result);

        return result;
    }
}