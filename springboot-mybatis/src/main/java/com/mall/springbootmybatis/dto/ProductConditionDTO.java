package com.mall.springbootmybatis.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 *
 *  keyword: null,
 *          pageNum: 1,
 *          pageSize: 5,
 *          publishStatus: null,   // 是否上架
 *          verifyStatus: null,
 *          productSn: null,
 *          productCategoryId: null,
 *          brandId: null
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductConditionDTO {

    private String keyword;
    private Integer pageNum;
    private Integer pageSize;
    private Integer publishStatus;
    private Integer verifyStatus;
    private String productSn;
    private Long productCategoryId;
    private Long brandId;
}
