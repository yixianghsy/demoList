package com.example.shargingjdbcdemo.mapper;



import com.example.shargingjdbcdemo.domain.PmsProduct;
import com.example.shargingjdbcdemo.domain.PmsProductExample;

import java.util.List;

public interface PmsProductMapper {


    List<PmsProduct> selectByExample(PmsProductExample example);



}