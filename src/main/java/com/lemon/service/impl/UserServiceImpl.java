package com.lemon.service.impl;

import com.lemon.pojo.User;
import com.lemon.mapper.UserMapper;
import com.lemon.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
