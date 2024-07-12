package com.hsy.FactoryPattern.Method;

import com.hsy.FactoryPattern.Simple.BMW523;

public class FactoryBMW523 implements FactoryBMW {
    @Override
    public BMW523 createBMW() {
        return new BMW523();
    }
}