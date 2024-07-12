package com.hsy.tacticsAddTemplate;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class CodeStrategy  {
    @Resource
    private  GoodsCode goodsCode;
    @Resource
    private  OrderCode orderCode;

    private final Map<String,ICodeService> instanceMap=new HashMap(2);


    // 项目启动即可注册实例
    @PostConstruct
    public void register(){
        instanceMap.put(CodeTypeEnum.GOODS_NO.getCode(),goodsCode);
        instanceMap.put(CodeTypeEnum.ORDER_NO.getCode(),orderCode);
    }

    // 获取实例
    public ICodeService getInstance(String codeType){
        return instanceMap.get(codeType);
    }

}