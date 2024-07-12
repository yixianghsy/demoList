package com.hsy.dutyPattern;
/**
 * 技术组长：申请天数4~6天交给技术组长
 */
public class GroupLeaderApprover extends Approver {

    public GroupLeaderApprover(String name) {
        super(name);
    }
    @Override
    public void doRequest(Request request) {
        if (request.getNums()>= 4 && request.getNums() <= 6) {//如果有审批资格
            System.out.println(String.format("%s,审批通过,审批人:%s",request.toString(),name));
        } else {//否则扔给下一个审批人
            if(successor!=null)
                successor.doRequest(request);
        }
    }
}

