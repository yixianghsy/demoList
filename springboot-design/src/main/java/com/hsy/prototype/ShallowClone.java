package com.hsy.prototype;

public class ShallowClone extends Prototype {
    @Override
    public Prototype clone(){
        Prototype prototype = null;
        try {
            prototype = (Prototype)super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return prototype;
    }

    @Override
    public void show(){
        System.out.println("浅克隆");
    }
}