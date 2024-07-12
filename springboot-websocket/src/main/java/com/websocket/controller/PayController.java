package com.websocket.controller;

import com.alipay.api.AlipayApiException;
import com.websocket.bean.PayRequestDto;
import com.websocket.entity.AlipayEntity;
import com.websocket.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController {

    @Autowired
    private AlipayService alipayService;

    @PostMapping("/api/pay")
    public String pay(@RequestBody AlipayEntity requestDto) throws AlipayApiException {
        String payUrl = alipayService.aliPay(requestDto);
        return payUrl;
    }
}
