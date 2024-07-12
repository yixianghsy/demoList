package com.example.shargingjdbcdemo.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 商品查询参数
 * Created by macro on 2018/4/27.
 */
@Data
@EqualsAndHashCode
public class PmsProductQueryParam implements Serializable {

    private Integer publishStatus;

    private Integer verifyStatus;

    private String keyword;

    private String productSn;

    private Long productCategoryId;

    private Long brandId;
}
