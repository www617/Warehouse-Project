package com.neuedu.yyzx.controller;



import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.neuedu.yyzx.pojo.User;
import com.neuedu.yyzx.service.UserService;
import com.neuedu.yyzx.utils.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wyh
 * @since 2023-11-02
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(tags = "用户管理")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name = "username", value = "用户登录账号",required =true),
            @ApiImplicitParam(dataType = "string",name = "password", value = "用户登录密码",required =true)
    })
    @GetMapping("/login")
    public ResultVo<User> login(String username,String password) throws Exception {
        return userService.login(username,password);
    }


    @ApiOperation("添加用户")
    @PostMapping("/addUser")
    public ResultVo addUser(User user) throws  Exception{
        user.setIsDeleted(0);
        userService.save(user);
        return ResultVo.ok("添加成功");
    }

    @ApiOperation("修改用户")
    @PostMapping("/updateUser")
    public ResultVo updateUser(User user) throws  Exception{
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>();
        updateWrapper.eq("id",user.getId());
        userService.update(user,updateWrapper);
        return ResultVo.ok("修改成功");
    }

    @ApiOperation("删除用户")
    @GetMapping("/delUser")
    public ResultVo delUser(Integer id) throws  Exception{
        userService.removeById(id);
        return ResultVo.ok("删除成功");
    }
}
