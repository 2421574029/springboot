package com.tt.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tt.springboot.common.Constants;
import com.tt.springboot.controller.dto.UserDto;
import com.tt.springboot.entity.User;
import com.tt.springboot.exception.ServiceException;
import com.tt.springboot.mapper.UserMapper;
import com.tt.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tt.springboot.utils.TokenUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 图哥
 * @since 2022-04-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();

    //用户登录
    @Override
    public UserDto login(UserDto userDto) {
        User one = getUserInfo(userDto);
        if (one != null) {
            BeanUtil.copyProperties(one,userDto,true);
            //设置token
            String token = TokenUtils.genToken(one.getId().toString(),one.getPassword());
            userDto.setToken(token);
            return userDto;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }

    }

    //用户注册
    @Override
    public User register(UserDto userDto) {
        User one = getUserInfo(userDto);
        if (one == null){
            one = new User();
            BeanUtil.copyProperties(userDto,one,true);
            save(one);//把cope完之后的用户对象存储到数据库
        }else {
            throw new ServiceException(Constants.CODE_600, "用户已存在");
        }
        return one;
    }

    private User getUserInfo(UserDto userDto){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDto.getUsername());
        queryWrapper.eq("password", userDto.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);//从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }
}
