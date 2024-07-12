package com.mall.springbootmybatis.dto;


import com.mall.springbootmybatis.domain.OmsOrder;
import com.mall.springbootmybatis.domain.OmsOrderItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@Data
@EqualsAndHashCode(callSuper = false)

public class OrderListDTO extends OmsOrder {
    private List<OmsOrderItem> orderItemList;
}
