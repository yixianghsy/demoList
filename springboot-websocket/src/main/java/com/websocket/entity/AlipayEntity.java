package com.websocket.entity;

import lombok.Data;

@Data
public class AlipayEntity {

    /**
     * 商户订单号，必填
     *
     */
    private String out_trade_no;
    /**
     * 订单名称，必填
     */
    private String subject;
    /**
     * 付款金额，必填
     * 根据支付宝接口协议，必须使用下划线
     */
    private String total_amount;
    /**
     * 商品描述，可空
     */
    private String body;
    /**
     * 超时时间参数
     */
    private String timeout_express= "10m";
    /**
     * 产品编号
     */
    private String product_code= "FAST_INSTANT_TRADE_PAY";

}

