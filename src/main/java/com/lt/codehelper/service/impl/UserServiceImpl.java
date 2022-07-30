package com.lt.codehelper.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.User;
import com.lt.codehelper.mapper.UserMapper;
import com.lt.codehelper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description 用户服务层实现类
 * @Author lt
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;


    /**
     * @MethodName addUser
     * @Description 用户注册
     * @Author lt
     * @Param [user]
     * @return boolean
     **/
    @Override
    public boolean register(User user) {
        user.setCreateTime(new Date());
        boolean registerFlag = userMapper.insertUser(user);
        return registerFlag;
    }

    /**
     * @MethodName login
     * @Description 用户登录
     * @Author lt
     * @Param [username, password, userType]
     * @return boolean
     **/
    @Override
    public User login(String username, String password, String userType) {
        // 按照用户名查找用户
        User user = userMapper.selectUserByUsername(username);
        // 将提交数据与数据库数据进行比对
        if ("管理员".equals(user.getUserType())){
            // 管理员同一个账号既可以登录前台也可以登录后台，所以管理员不用比对用户类型，只需判断用户名和密码是否对应
            if(user != null && password.equals(user.getPassword())){
                return user;
            }
        } else {
            if(user != null && password.equals(user.getPassword()) && userType.equals(user.getUserType())){
                return user;
            }
        }

        return null;
    }

    /**
     * @MethodName resetPassword
     * @Description 密码重置
     * @Author lt
     * @Param [email, newPassword]
     * @return boolean
     **/
    @Override
    public boolean resetPassword(String email,String newPassword) {
        // 按照邮箱查询用户信息
        User user = userMapper.selectUserByEmail(email);
        // 进行密码更改
        boolean resetPasswordFlag = userMapper.updateUserPasswordById(user.getId(),newPassword,new Date());
        return resetPasswordFlag;
    }


    /**
     * @MethodName addUser
     * @Description 添加用户
     * @Author lt
     * @Param [user]
     * @return boolean
     **/
    @Override
    public boolean addUser(User user) {
        boolean addUserFlag = userMapper.insertUser(user);
        return addUserFlag;
    }

    /**
     * @MethodName findUserByPage
     * @Description 分页查询用户信息
     * @Author lt
     * @Param [pageNumber, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.User>
     **/
    @Override
    public PageInfo<User> findUserByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> list = userMapper.selectAllUser();
        // 使用PageInfo对结果进行包装
        PageInfo<User> page = new PageInfo<User>(list);
        return page;
    }

    /**
     * @MethodName findUserById
     * @Description 按照用户id查找用户信息
     * @Author lt
     * @Param [id]
     * @return com.lt.codehelper.entity.User
     **/
    @Override
    public User findUserById(long id) {
        User user = userMapper.selectUserById(id);
        return user;
    }

    /**
     * @MethodName deleteUser
     * @Description 删除用户
     * @Author lt
     * @Param [id]
     * @return boolean
     **/
    @Override
    public boolean deleteUser(long id) {
        boolean deleteUserFlag = userMapper.deleteUserById(id);
        return deleteUserFlag;
    }

    /**
     * @MethodName updateUserById
     * @Description 修改用户信息
     * @Author lt
     * @Param [id]
     * @return boolean
     **/
    @Override
    public boolean updateUserById(User user) {
        boolean updateUserByIdFlag = userMapper.updateUserById(user);
        return updateUserByIdFlag;
    }

}
