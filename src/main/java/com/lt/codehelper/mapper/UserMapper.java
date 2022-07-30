package com.lt.codehelper.mapper;

import com.lt.codehelper.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @InterfaceName UserMapper
 * @Description 用户持久层接口
 * @Author lt
 * @Version 1.0
 **/
@Repository
public interface UserMapper {

    // 新增一个用户
    boolean insertUser(User user);

    // 按照用户id删除一个用户
    boolean deleteUserById(long id);

    // 按照用户id修改用户信息
    boolean updateUserById(User user);

    // 按照用户id修改用户的密码
    boolean updateUserPasswordById(long id, String newPassword, Date updateTime);

    // 查询所有用户信息
    List<User> selectAllUser();

    // 按照用户id查询用户信息
    User selectUserById(long id);

    // 按照用户名查询用户信息
    User selectUserByUsername(String username);

    // 按照邮箱查询用户信息
    User selectUserByEmail(String email);
}
