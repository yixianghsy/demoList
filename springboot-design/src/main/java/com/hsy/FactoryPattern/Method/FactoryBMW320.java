package com.hsy.FactoryPattern.Method;


import com.hsy.FactoryPattern.Simple.BMW320;

public class FactoryBMW320 implements FactoryBMW {

    @Override
    public BMW320 createBMW() {
        return new BMW320();
    }

}