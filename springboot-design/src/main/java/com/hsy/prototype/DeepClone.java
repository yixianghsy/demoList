package com.hsy.prototype;

import java.util.ArrayList;

public class DeepClone extends Prototype {
    @SuppressWarnings("unchecked")
    @Override
    public Prototype clone() {
        Prototype prototype = null;
        try {
            prototype = (Prototype)super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        prototype.list = (ArrayList<String>) this.list.clone();
        return prototype;
    }

    @Override
    public void show(){
        System.out.println("深克隆");
    }
}