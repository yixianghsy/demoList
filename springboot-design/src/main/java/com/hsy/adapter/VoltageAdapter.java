package com.hsy.adapter;

/**
 * 电压适配类（VoltageAdapter）
 */
public class VoltageAdapter extends SourceVoltage implements TargetVoltage {
    /**
     * 通过实现接口的方法将电压转化
     */
    @Override
    public int targetMethod() {
        // 获取到220V电压
        int volts = sourceMethod();
        System.out.println("适配前，输出电压 = " + volts + "V");

        // 将220V电压转成20V
        volts = volts / 11;
        System.out.println("适配后，转化电压 = " + volts + "V");
        return volts;
    }
}