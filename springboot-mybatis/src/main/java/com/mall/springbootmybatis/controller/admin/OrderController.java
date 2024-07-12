package com.mall.springbootmybatis.controller.admin;

import com.github.pagehelper.PageInfo;

import com.mall.api.CommonPage;
import com.mall.api.CommonResult;
import com.mall.springbootmybatis.domain.OmsOrder;
import com.mall.springbootmybatis.dto.OrderListDTO;
import com.mall.springbootmybatis.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("find_by_id")
    public List<OmsOrder> findById (Long id){
        return orderService.findAll(id);
    }

    /**
     * return
     * {
     *     "code": 200,
     *     "message": "操作成功",
     *     "data": {
     *         "pageNum": 1,    //第几页pageNum
     *         "pageSize": 5,   //没页多少数据pageSize
     *         "totalPage": 3,  //总共几页pages
     *         "total": 11,     //总共多少条数据total
     *         "list": [      //数据集合
     *             {
     *             }
     *  }
     * @param page
     * @param size
     * @param id
     * @return
     */
    @GetMapping("getMyOrders")
    @ResponseBody
    public PageInfo<OrderListDTO> getMyOrders (@RequestParam(value = "page",defaultValue = "1")int page,
                                               @RequestParam(value = "size",defaultValue = "3")int size, Long id){

        PageInfo<OrderListDTO> myOrders = orderService.getMyOrders(page, size, id);
        System.out.println("//第几页:"+myOrders.getPageNum());
        System.out.println("//没页多少数据："+ myOrders.getPageSize());
        System.out.println("//没页实际多少数据："+ myOrders.getSize());
        System.out.println("//总共几页:"+myOrders.getPages());
        System.out.println("//总共多少条数据:"+myOrders.getTotal());
        System.out.println("//结果集:"+myOrders.getList().get(0).toString());
        return myOrders;
    }
    @GetMapping("getMyOrdersList")
    @ResponseBody
    public CommonResult<CommonPage<OrderListDTO>> getMyOrdersList (@RequestParam(value = "page",defaultValue = "1")int page,
                                                                   @RequestParam(value = "size",defaultValue = "3")int size, Long id){

        List<OrderListDTO> myOrders = orderService.getMyOrdersList(page, size, id);
        PageInfo<OrderListDTO> pageInfos = new PageInfo<>(myOrders);
        return CommonResult.success(CommonPage.restPage(pageInfos));
    }
    @GetMapping("getMyOrdersList2")
    @ResponseBody
    public CommonPage<OrderListDTO> getMyOrdersList2 (@RequestParam(value = "page",defaultValue = "1")int page,
                                                                   @RequestParam(value = "size",defaultValue = "3")int size, Long id){

//        List<OrderListDTO> myOrders = orderService.getMyOrdersList(page, size, id);
        PageInfo<OrderListDTO> myOrders = orderService.getMyOrders(page, size, id);
        return CommonPage.restPage(myOrders);
    }
}
