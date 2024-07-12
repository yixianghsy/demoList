package com.mall.springbootmybatis.mapper;


import com.mall.springbootmybatis.domain.PmsProduct;
import com.mall.springbootmybatis.domain.PmsProductExample;

import java.util.List;

public interface PmsProductMapper {


    List<PmsProduct> selectByExample(PmsProductExample example);



}