package com.example.shargingjdbcdemo;

import com.example.shargingjdbcdemo.mapper.TbUserMapper;
import com.example.shargingjdbcdemo.user.TbUser;
import com.example.shargingjdbcdemo.user.TbUserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ShargingJdbcDemoApplicationTests {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Test
    void contextLoads() {
    }

    @Test
    public void  selectAll(){
//        for (int i=0; i<5; i++){
        // 开始分页
        PageHelper.startPage(1,5);
        TbUserExample tbUserExample = new TbUserExample();
            List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
            PageInfo<TbUser> pageInfo = new PageInfo<>(tbUsers);
            System.out.println("selectAll : "+pageInfo.getList());
//        }
    }


}
