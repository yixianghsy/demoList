package com.example.shargingjdbcdemo.controller;


import com.example.shargingjdbcdemo.service.IUserService;
import com.example.shargingjdbcdemo.service.UmsAdminService;
import com.example.shargingjdbcdemo.service.UmsRoleService;
import com.example.shargingjdbcdemo.user.UmsMenu;
import com.example.shargingjdbcdemo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
public class HelloController {
    @Autowired
   private IUserService iUserService;
    @Autowired
    private UmsRoleService umsRoleService;

    @Autowired
    private UmsAdminService umsAdminService;
//    @RequestMapping("/")
//    public String index() {
//        return "Hello Spring Boot,Index!";
//    }
//
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        iUserService.addBatchData(10);
        return "Spring Boot Test Demo!";
    }
    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public String test1() {
        umsAdminService.getAdminByUsername("admin");
        return "Spring Boot Test1 Demo!";
    }
    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public String test2() {
        List<User> select = iUserService.select();
        System.out.println(select.toString());
        return "Spring Boot Test2 Demo!";
    }
    @RequestMapping(value = "/test3", method = RequestMethod.GET)
    public String test3() {
        List<UmsMenu> menuList = umsRoleService.getMenuList(3L);
        //方法1 集合类的通用遍历方式, 从很早的版本就有, 用迭代器迭代
        Iterator it1 = menuList.iterator();
        while(it1.hasNext()){
            System.out.println(it1.next().toString());
        }
        return "查询成功";
    }
}
