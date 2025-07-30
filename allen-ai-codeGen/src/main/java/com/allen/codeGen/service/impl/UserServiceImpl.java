package com.allen.codeGen.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.allen.codeGen.model.entity.User;
import com.allen.codeGen.mapper.UserMapper;
import com.allen.codeGen.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户 服务层实现。
 *
 * @author <a href="https://github.com/KiveAllen">程序员小幽</a>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService{

}
