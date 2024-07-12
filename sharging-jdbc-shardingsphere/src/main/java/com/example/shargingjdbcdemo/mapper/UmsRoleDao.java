package com.example.shargingjdbcdemo.mapper;



import com.example.shargingjdbcdemo.user.UmsMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台角色管理自定义Dao
 * Created by macro on 2020/2/2.
 */
public interface UmsRoleDao {
    /**
     * 根据后台用户ID获取菜单
     */
    //使用shardingsphere中分表数据源

    List<UmsMenu> getMenuList(@Param("adminId") Long adminId);


}
