package com.hsy.tacticsPattern;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 支付处理上下文
 *
 * @Author Liurb
 * @Date 2022/11/26
 */
@Service
public class PaymentContextServiceImpl implements PaymentContextService {

    /**
     * 自动注入所有具体策略实现类
     */
    @Resource
    List<PaymentHandleService> handleServiceList;

    /**
     * 额外定义一个不支持的渠道支付方式实现类
     */
    @Resource(name = "NonsupportPaymentHandleServiceImpl")
    PaymentHandleService nonsupportService;

    @Override
    public PaymentHandleService getContext(String channel) {

        if (StrUtil.isEmpty(channel)) {
            return nonsupportService;
        }

        //策略实现类上都会打上 Payment 注解，并定义支付方式的值，用于适配订单的渠道值
        PaymentHandleService handleService = handleServiceList.stream()
                .filter(f -> StrUtil.equals(channel, f.getClass().getAnnotation(Payment.class).value()))
                .findFirst()
                .orElse(nonsupportService);

        return handleService;
    }

}