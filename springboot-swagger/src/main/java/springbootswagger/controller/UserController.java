package springbootswagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springbootswagger.pojo.User;

/**
 * @ProjectName:springboot-swagger2
 * @Author:BraveZeng
 * @Description: 用户控制器类
 * @Date:Created in 2022/7/20 14:37
 * @Modified By:
 */
@RestController
@RequestMapping("/user")
@Api(value = "测试接口",tags = "用户管理相关接口")
public class UserController {

    /**
     * 保存用户数据
     * @param user
     * @return
     */
    @PostMapping("/save")
    //方法参数说明，name参数名；value参数说明,备注；dataType参数类型；required是否必传，默认false；
    //@ApiOperation(value = “接口说明”, httpMethod = “接口请求方式”,
    // response =“接口返回参数类型”, notes = “接口发布说明”；
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户编号",required = true),
            @ApiImplicitParam(name = "username", value = "用户名",required = true),
            @ApiImplicitParam(name = "address", value = "用户地址")
    })
    @ApiOperation(value = "添加用户",httpMethod ="POST",response =String.class,notes = "添加用户")
    public String saveUser(User user){
        return "保存用户成功！";
    }


    @GetMapping("/findUserById")
    @ApiImplicitParam(name="id",value = "用户编号",required = true)
    @ApiOperation(value = "根据用户id查找指定用户",httpMethod ="GET",response =User.class,notes = "根据用户id查找指定用户")
    public User getUserById(int id){
        return new User();
    }

    @GetMapping("/removeUserById")
    @ApiImplicitParam(name="id",value = "用户编号",required = true)
    @ApiOperation(value = "根据用户id删除指定用户",httpMethod ="GET",response =String.class,notes = "根据用户id删除指定用户" )
    public String deleteUserById(int id){
        return "保存用户成功！";
    }

}
