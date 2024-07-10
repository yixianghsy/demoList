package com.hsy.duty;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class DutyAppTests {
    @Test
    void contextLoads() {
                Approver deputy= new DeputyGroupLeaderApprover("技术副组长");
                Approver group= new GroupLeaderApprover("技术组长");
                Approver small= new SmallLeaderApprover("小领导");
                Approver big= new BigLeaderApprover("大领导");
                //设置后续申请人
                deputy.setSuccessor(group);
                group.setSuccessor(small);
                small.setSuccessor(big);
                big.setSuccessor(null);
                //发起申请
                Request request = new AskForLeaveRequest("张三", LocalDateTime.now(),"生病请假",9);
                deputy.doRequest(request);
                //由于我们使用的是面向抽象编程，
                // 后期如果张三有调薪的请求，只需要新增一个调薪类，然后实体类替换即可
                //原有的代码不用改动，只需要替换一处，体现了开闭原则

    }
}
