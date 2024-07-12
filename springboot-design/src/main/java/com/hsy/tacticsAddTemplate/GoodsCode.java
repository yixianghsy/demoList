package com.hsy.tacticsAddTemplate;

import org.springframework.stereotype.Service;

@Service
public class GoodsCode extends CodeProcessAbstract {
    @Override
    protected String generateCode(String preCode){
        // 模拟商品规则生成
        System.out.println("生成新的编号：20230504002");
        return "20230504002";
    }

}