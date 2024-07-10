package com.hsy.duty;
/**
 * 大领导：申请天数10~12天交给大领导
 */
public class BigLeaderApprover extends Approver {

    public BigLeaderApprover(String name) {
        super(name);
    }
    @Override
    public void doRequest(Request request) {
        if (request.getNums()>= 10 && request.getNums() <= 12) {
            System.out.println(String.format("%s,审批通过,审批人:%s",request.toString(),name));
        } else {//否则扔给下一个审批人
            if(successor!=null)
                successor.doRequest(request);
        }
    }
}
