package com.neuedu.yyzx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neuedu.yyzx.pojo.User;
import com.neuedu.yyzx.mapper.UserMapper;
import com.neuedu.yyzx.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.yyzx.utils.ResultVo;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wyh
 * @since 2023-11-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    public ResultVo<User> login(String username, String password) throws Exception {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", username);
        qw.eq("password", password);
        User user = getOne(qw);

        if (user != null) {
            if (user.getIsDeleted() == 0) {
                HashMap<String, Object> map = new HashMap<>();
                // 生成JWT令牌
                JwtBuilder builder = Jwts.builder();
                String token = builder.setSubject(username) // 主题
                        .setIssuedAt(new Date()) // token生成时间
                        .setId(user.getId().toString()) // 用户ID
                        .setClaims(map) // 可以存放其他用户信息
                        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 过期时间
                        .signWith(SignatureAlgorithm.HS256, "neuedu123456") // 加密方式和密钥
                        .compact();
                return ResultVo.ok(user, token);
            }
            return ResultVo.fail("无权限，请联系管理员");
        }
        return ResultVo.fail("账号密码错误");
    }
}