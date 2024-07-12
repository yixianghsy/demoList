package com.example.shargingjdbcdemo.service;

import com.example.shargingjdbcdemo.user.UmsMenu;

import java.util.List;


public interface UmsRoleService {


    /**
     * 根据管理员ID获取对应菜单
     */
    List<UmsMenu> getMenuList(Long adminId);

}
