package com.neuedu.yyzx.service;

import com.neuedu.yyzx.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.yyzx.utils.ResultVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wyh
 * @since 2023-11-02
 */
public interface UserService extends IService<User> {
    public ResultVo<User> login(String username, String password)throws Exception;
}
