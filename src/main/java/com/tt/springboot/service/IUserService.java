package com.tt.springboot.service;

import com.tt.springboot.controller.dto.UserDto;
import com.tt.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 图哥
 * @since 2022-04-09
 */
public interface IUserService extends IService<User> {
    //登录
    UserDto login(UserDto userDto);
    //注册
    User register(UserDto userDto);
}
