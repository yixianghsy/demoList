package com.example.shargingjdbcdemo.controller;

import com.example.shargingjdbcdemo.mapper.COrderMapper;
import com.example.shargingjdbcdemo.mapper.TbUserMapper;
import com.example.shargingjdbcdemo.user.COrder;
import com.example.shargingjdbcdemo.user.TbUser;
import com.example.shargingjdbcdemo.user.TbUserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private COrderMapper cOrderMapper;
    @RequestMapping("/listAll")
    public  List<TbUser>   listAll(int pageNum, int pageSize ) {
        PageHelper.startPage(pageNum,pageSize);
        TbUserExample tbUserExample = new TbUserExample();
        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
        PageInfo<TbUser> pageInfo = new PageInfo<>(tbUsers);
        return  pageInfo.getList();
    }
    @RequestMapping("/add")
    public  void  add(){
        Random random =new Random();
        for (Integer l = 1; l <= 20; l++) {
            TbUser tbUser = new TbUser();
            int companyId = random.nextInt(100+l);
            int userId = random.nextInt(100+l);
                tbUser.setUsername("zhangsan" +l);
                tbUser.setPassword("55555555");
                tbUser.setCreated(new Date());
                tbUser.setUpdated(new Date());
                tbUser.setCompany_id(random.nextInt(4)+3);
                tbUser.setUser_id(random.nextInt(4)+3);
                tbUserMapper.insert(tbUser);

        }
    }
    @RequestMapping("/cOrderAll")
    public  List<COrder>   cOrderAll() {
        List<COrder> cOrder = cOrderMapper.selectAll();
        return  cOrder;
    }
    @RequestMapping("/cOrderOne")
    public  COrder  cOrderOne(Long id) {
        COrder cOrder = cOrderMapper.selectById(id);
        System.out.println(cOrder.toString());
        return  cOrder;
    }
}
