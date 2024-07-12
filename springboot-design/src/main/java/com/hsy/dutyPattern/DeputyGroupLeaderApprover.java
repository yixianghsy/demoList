package com.hsy.dutyPattern;
/**
 * 技术副组长：申请天数1~3天交给技术副组长
 */
public class DeputyGroupLeaderApprover extends Approver {

    public DeputyGroupLeaderApprover(String name) {
        super(name);
    }

    @Override
    public void doRequest(Request request) {
        if (request.getNums()>= 1 && request.getNums() <= 3) {//如果有审批资格
            System.out.println(String.format("%s,审批通过,审批人:%s",request.toString(),name));
        } else {//否则扔给下一个审批人
            if(successor!=null)
                successor.doRequest(request);
        }
    }
}

