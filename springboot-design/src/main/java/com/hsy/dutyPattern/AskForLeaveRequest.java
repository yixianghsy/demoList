package com.hsy.duty;

import java.time.LocalDateTime;

public class AskForLeaveRequest extends Request {

    public AskForLeaveRequest(String name, LocalDateTime applyTime, String reason, int nums) {
        super(name, applyTime, reason, nums);
    }

    @Override
    public String applyType() {
        return "请假成申请";
    }
}

