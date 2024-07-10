package com.hsy.tactics;

import org.springframework.stereotype.Service;

/**
 * 不支持的业务处理实现
 *
 * @Author Liurb
 * @Date 2022/11/26
 */
@Payment("nonsupport")
@Service("NonsupportPaymentHandleServiceImpl")
public class NonsupportPaymentHandleServiceImpl implements PaymentHandleService {

    @Override
    public PayResult pay(PayOrder payOrder) {
        PayResult result = new PayResult();
        result.setCode(-1);
        return result;
    }
}