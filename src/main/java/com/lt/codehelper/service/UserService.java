package com.lt.codehelper.service;

import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.User;



/**
 * @InterfaceName UserService
 * @Description 用户服务层接口
 * @Author lt
 * @Version 1.0
 **/
public interface UserService {

    // 注册
    boolean register(User user);

    // 登录
    User login(String username, String password, String userType);

    // 密码重置
    boolean resetPassword(String email,String newPassword);

    // 添加用户
    boolean addUser(User user);

    // 删除用户
    boolean deleteUser(long id);

    // 修改用户信息
    boolean updateUserById(User user);

    // 分页查询用户
    PageInfo<User> findUserByPage(int pageNum, int pageSize);

    // 按照id查找用户数据
    User findUserById(long id);
}
