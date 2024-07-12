package com.example.shargingjdbcdemo.user;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/01/13 10:11
 */
@Data
//@EqualsAndHashCode(callSuper = true)
public class User implements Serializable {


    private Long userId;

    private String username;


    private String password;

    /**
     * sex值为空时，MP更新数据库时不忽略此字段值
     */

    private Byte sex;


    private String remark;


    private Integer page;


    private Integer pageSize;

}
