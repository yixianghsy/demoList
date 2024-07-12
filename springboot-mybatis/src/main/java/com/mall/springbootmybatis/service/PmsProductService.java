package com.mall.springbootmybatis.service;

import com.github.pagehelper.PageInfo;
import com.mall.springbootmybatis.domain.PmsProduct;
import com.mall.springbootmybatis.domain.PmsProductQueryParam;
import com.mall.springbootmybatis.dto.OrderListDTO;
import com.mall.springbootmybatis.dto.ProductConditionDTO;

import java.util.List;

public interface PmsProductService {

    List<PmsProduct> list(PmsProductQueryParam productQueryParam, Integer pageSize, Integer pageNum);
}
