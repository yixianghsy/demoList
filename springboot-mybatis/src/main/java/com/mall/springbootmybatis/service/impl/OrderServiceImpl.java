package com.mall.springbootmybatis.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.springbootmybatis.domain.OmsOrder;
import com.mall.springbootmybatis.domain.OmsOrderExample;
import com.mall.springbootmybatis.domain.OmsOrderItem;
import com.mall.springbootmybatis.domain.Video;
import com.mall.springbootmybatis.dto.OrderListDTO;
import com.mall.springbootmybatis.mapper.OmsOrderMapper;
import com.mall.springbootmybatis.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl  implements OrderService {
    @Autowired
    private OmsOrderMapper orderMapper;
    @Override
    public List<OmsOrder> findAll(Long  id) {
        OmsOrderExample omsOrderExample = new OmsOrderExample();
        omsOrderExample.createCriteria().andIdEqualTo(id);
        return orderMapper.selectByExample(omsOrderExample);
    }

    @Override
    public PageInfo<OrderListDTO> getMyOrders(int page,int size,Long memberId) {
        PageHelper.startPage(page,size);
        List<OrderListDTO> list = orderMapper.getMyOrders(memberId);
        PageInfo<OrderListDTO> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List<OrderListDTO> getMyOrdersList(int page, int size, Long memberId) {
        PageHelper.startPage(page, size);
        List<OrderListDTO> list = orderMapper.getMyOrders(memberId);
        return list;
    }
}
