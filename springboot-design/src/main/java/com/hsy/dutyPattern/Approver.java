package com.hsy.dutyPattern;



/**
 * 审批人
 * 核心目标：
 *  1.要实现链条，必须知道下个一个，所以要内置后续审批人属性
 *  2.审批人有审批通过和不通过，满足条件通过则结束，不通过则交个下一个，该实现逻辑由子类去实现
 *    父类只定义方法参数，父类的方法参数如果是对象类型，则该参数一般也是抽象类或接口，这叫面向抽象/接口编程
 */
public abstract class Approver {
    //后续审批人
    protected Approver successor;
    //审批人姓名
    protected String name;

    public Approver(String name) {
        this.name = name;
    }

    public void setSuccessor(Approver successor) {
        this.successor = successor;
    }

    /**
     * 处理申请请求的抽象方法 有请假申请、有提薪申请等等，所以这里应该使用抽象解耦
     * 面向对象、面向接口、面向抽象、面向切面编程
     * @param request
     */
    public abstract void doRequest(Request request);
}
