package com.example.shargingjdbcdemo.service;

import com.example.shargingjdbcdemo.domain.PmsProduct;
import com.example.shargingjdbcdemo.domain.PmsProductQueryParam;

import java.util.List;

public interface PmsProductService {

    List<PmsProduct> list(PmsProductQueryParam productQueryParam, Integer pageSize, Integer pageNum);
}
