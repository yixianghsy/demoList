package com.hsy.FactoryMethodPattern.Method;

import com.hsy.FactoryMethodPattern.Method.FactoryBMW;
import com.hsy.FactoryMethodPattern.Simple.BMW523;

public class FactoryBMW523 implements FactoryBMW {
    @Override
    public BMW523 createBMW() {
        return new BMW523();
    }
}