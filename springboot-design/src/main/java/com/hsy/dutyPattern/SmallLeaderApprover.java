package com.hsy.duty;
/**
 * 小领导：申请天数7~9天交给小领导
 */
public class SmallLeaderApprover extends Approver {

    public SmallLeaderApprover(String name) {
        super(name);
    }
    @Override
    public void doRequest(Request request) {
        if (request.getNums()>= 7 && request.getNums() <= 9) {//如果有审批资格
            System.out.println(String.format("%s,审批通过,审批人:%s",request.toString(),name));
        } else {//否则扔给下一个审批人
            if(successor!=null)
                successor.doRequest(request);
        }
    }
}
