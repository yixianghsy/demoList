package com.websocket.service.impl;

import com.alipay.api.AlipayApiException;

import com.websocket.entity.AlipayEntity;
import com.websocket.service.AlipayService;
import com.websocket.utils.pay.Alipay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private Alipay alipay;

    @Override
    public String aliPay(AlipayEntity alipayEntity) throws AlipayApiException {
        return alipay.pay(alipayEntity);
    }
}

