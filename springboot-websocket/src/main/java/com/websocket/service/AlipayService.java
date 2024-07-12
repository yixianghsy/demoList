package com.websocket.service;

import com.alipay.api.AlipayApiException;
import com.websocket.entity.AlipayEntity;


public interface AlipayService {

    /**
     * 支付宝支付接口
     * @param alipayEntity
     * @return
     * @throws AlipayApiException
     */
    String aliPay(AlipayEntity alipayEntity) throws AlipayApiException;


}

