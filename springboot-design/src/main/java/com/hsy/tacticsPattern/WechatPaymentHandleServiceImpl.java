package com.hsy.tacticsPattern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Payment("wechat")
@Service
public class WechatPaymentHandleServiceImpl implements PaymentHandleService{
    @Override
    public PayResult pay(PayOrder payOrder) {
        PayResult result = new PayResult();
        result.setOrder("wechat_202211261234567890");
        result.setCode(1);
        log.info("微信支付处理 订单信息:{} 支付结果:{}", payOrder, result);

        return result;
    }
}
