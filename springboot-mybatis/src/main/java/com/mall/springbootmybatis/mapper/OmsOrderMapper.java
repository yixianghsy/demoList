package com.mall.springbootmybatis.mapper;

import com.mall.springbootmybatis.domain.OmsOrder;
import com.mall.springbootmybatis.domain.OmsOrderExample;
import com.mall.springbootmybatis.dto.OrderListDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsOrderMapper {
    long countByExample(OmsOrderExample example);

    int deleteByExample(OmsOrderExample example);

    int deleteByPrimaryKey(Long id);

    Long insert(OmsOrder record);

    int insertSelective(OmsOrder record);

    List<OmsOrder> selectByExample(OmsOrderExample example);

    OmsOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OmsOrder record, @Param("example") OmsOrderExample example);

    int updateByExample(@Param("record") OmsOrder record, @Param("example") OmsOrderExample example);

    int updateByPrimaryKeySelective(OmsOrder record);

    int updateByPrimaryKey(OmsOrder record);
    List<OrderListDTO> getMyOrders(@Param("memberId") Long memberId);

}