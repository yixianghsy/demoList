package com.hsy.FactoryMethodPattern.Simple;

import com.hsy.FactoryMethodPattern.Simple.BMW;
import com.hsy.FactoryMethodPattern.Simple.BMW320;
import com.hsy.FactoryMethodPattern.Simple.BMW523;

/**
 * 工厂类：
 */
public class Factory {
    public BMW createBMW(int type) {
        switch (type) {

            case 320:
                return new BMW320();

            case 523:
                return new BMW523();

            default:
                break;
        }
        return null;
    }
}