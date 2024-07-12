package com.example.shargingjdbcdemo.controller.product;

import com.example.shargingjdbcdemo.domain.PmsProduct;
import com.example.shargingjdbcdemo.domain.PmsProductQueryParam;
import com.example.shargingjdbcdemo.service.PmsProductService;
import com.github.pagehelper.PageInfo;

import com.mall.api.CommonPage;
import com.mall.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2021-02-26
 */
@RestController
@RequestMapping("/product")
public class PmsProductController {
    @Autowired
    PmsProductService productService;
    /**
     *
     url:'/product/list',
     method:'get',
     data:          axios 如果设置的是data属性就是以json的方式传递
     params:{       axios 如果设置的是params属性就是以url参数的方式传递
     如果传递是URLSearchParams  会以formdata的方式传递
     keyword: null,
     pageNum: 1,
     pageSize: 5,
     publishStatus: null,
     verifyStatus: null,
     productSn: null,
     productCategoryId: null,
     brandId: null
     };
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsProduct>> getList(PmsProductQueryParam productQueryParam,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProduct> productList = productService.list(productQueryParam, pageSize, pageNum);
        PageInfo<PmsProduct> pageInfos = new PageInfo<>(productList);
        System.out.println("//第几页:"+pageInfos.getPageNum());
        System.out.println("//没页多少数据："+ pageInfos.getPageSize());
        System.out.println("//没页实际多少数据："+ pageInfos.getSize());
        System.out.println("//总共几页:"+pageInfos.getPages());
        System.out.println("//总共多少条数据:"+pageInfos.getTotal());
        System.out.println("//结果集:"+pageInfos.getList().get(0).toString());
        return CommonResult.success(CommonPage.restPage(pageInfos));

    }
    @RequestMapping(value = "/list2", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsProduct>> getList2(PmsProductQueryParam productQueryParam,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProduct> productList = productService.list(productQueryParam, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(productList));

    }
}
