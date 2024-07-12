package com.mall.springbootmybatis.service;

import com.github.pagehelper.PageInfo;
import com.mall.springbootmybatis.domain.OmsOrder;
import com.mall.springbootmybatis.domain.Video;
import com.mall.springbootmybatis.dto.OrderListDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderService {
    List<OmsOrder> findAll(Long  id);
    PageInfo<OrderListDTO> getMyOrders(int page, int size, Long memberId);
    List<OrderListDTO> getMyOrdersList(int page, int size, Long memberId);
}
