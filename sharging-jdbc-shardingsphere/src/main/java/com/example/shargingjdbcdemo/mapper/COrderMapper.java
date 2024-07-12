package com.example.shargingjdbcdemo.mapper;

import com.example.shargingjdbcdemo.user.COrder;

import java.util.List;
import java.util.Map;

public interface COrderMapper {
    List<COrder> selectAll();

    COrder selectById(Long id);
}
