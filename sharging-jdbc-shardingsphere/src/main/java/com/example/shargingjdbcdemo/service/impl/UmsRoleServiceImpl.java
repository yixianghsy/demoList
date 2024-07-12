package com.example.shargingjdbcdemo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.shargingjdbcdemo.confg.datasource.dynamic.DataSourceConfig;
import com.example.shargingjdbcdemo.mapper.UmsRoleDao;
import com.example.shargingjdbcdemo.service.UmsRoleService;
import com.example.shargingjdbcdemo.user.UmsMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台角色管理Service实现类
 * Created by macro on 2018/9/30.
 */

@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleDao roleDao;
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS(DataSourceConfig.SHARDING_DATA_SOURCE_NAME)
    public List<UmsMenu> getMenuList(Long adminId) {
        return roleDao.getMenuList(adminId);
    }


}