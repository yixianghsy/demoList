package com.hsy.dutyPattern;
import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * 面向对象四大特性：封装、继承、多态、抽象
 * 第一步：将请求封装成对象，是对象就要考虑属性和行为【方法】
 */
public abstract class Request {
    //申请人
    protected String name;
    //申请时间
    protected LocalDateTime applyTime;
    //申请原因
    protected String reason;
    //申请天数
    protected int nums;

    public Request(){

    }
    public Request(String name, LocalDateTime applyTime, String reason, int nums) {
        this.name = name;
        this.applyTime = applyTime;
        this.reason = reason;
        this.nums = nums;
    }

    public int getNums() {
        return nums;
    }

    /**
     * 申请类型
     */
    public abstract String applyType();

    @Override
    public String toString() {
        return new StringJoiner(", ", Request.class.getSimpleName() + "[", "]")
                .add("申请人='" + name + "'")
                .add("申请时间=" + applyTime)
                .add("申请原因='" + reason + "'")
                .add("申请数量=" + nums)
                .toString();
    }
}
