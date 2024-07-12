package com.example.shargingjdbcdemo.user;
;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class COrder implements Serializable {

//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @TableId(value = "id",type = IdType.AUTO)
    private long id;

//    @Column(name = "is_del", columnDefinition = "COMMENT '是否被删除'")
    private Boolean isDel;

//    @Column(name = "user_id", columnDefinition = "COMMENT '用户id'")
    private Integer userId;

//    @Column(name = "company_id", columnDefinition = "COMMENT '公司id'")
    private Integer companyId;

//    @Column(name = "publish_user_id", columnDefinition = "COMMENT 'B端⽤用户id'")
    private Integer publishUserId;

//    @Column(name = "position_id", columnDefinition = "COMMENT '职位ID'")
    private long positionId;

//        @Column(name = "resume_type", columnDefinition = "COMMENT '简历类型:0附件 1在线'")
    private Integer resumeType;

  //  @Column(name = "status", columnDefinition = "COMMENT '投递状态 WAIT-待处理理 AUTO_FILTER-⾃自动过滤 PREPARE_CONTACT-待沟通 REFUSE-拒绝 ARRANGE_INTERVIEW-通知⾯面试'")
    private String status;

  //  @Column(name = "create_time", columnDefinition = "COMMENT '创建时间'")
    private Date createTime;

 //   @Column(name = "update_time", columnDefinition = "COMMENT '处理时间'")
    private Date updateTime;
}

