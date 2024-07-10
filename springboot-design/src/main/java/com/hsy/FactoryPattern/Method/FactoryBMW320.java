package com.hsy.FactoryMethodPattern.Method;


import com.hsy.FactoryMethodPattern.Method.FactoryBMW;
import com.hsy.FactoryMethodPattern.Simple.BMW320;

public class FactoryBMW320 implements FactoryBMW {

    @Override
    public BMW320 createBMW() {
        return new BMW320();
    }

}