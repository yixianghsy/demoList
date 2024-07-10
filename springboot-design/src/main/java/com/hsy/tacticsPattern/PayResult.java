package com.hsy.tactics;

import lombok.Data;

/**
 * 支付结果
 *
 * @Author Liurb
 * @Date 2022/11/26
 */
@Data
public class PayResult {

    /**
     * 订单号
     */
    private String order;

    /**
     * 支付结果
     * 1：成功
     */
    private Integer code;

}