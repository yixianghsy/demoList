package com.hsy.tacticsAddTemplate;

import org.springframework.stereotype.Service;

@Service
public class OrderCode extends CodeProcessAbstract {
    @Override
    protected String generateCode(String preCode) {
        // 模拟序号生成编号
        System.out.println("生成新的编号：20230504002");
        return "2";
    }


}