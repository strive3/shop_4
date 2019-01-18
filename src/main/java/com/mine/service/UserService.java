package com.mine.service;

import com.mine.common.ServerResponse;
import com.mine.pojo.UserInfo;
import com.mine.utils.MD5Utils;

/**
 * @author 杜晓鹏
 * @create 2019-01-04 19:40
 */
public interface UserService {
    /**
     * 登陆
     *
     * @param username
     * @param password
     * @return
     */
    ServerResponse login(String username, String password);

    /**
     * 注册
     *
     * @param userInfo
     * @return
     */
    ServerResponse register(UserInfo userInfo);

    /**
     * 获取密保问题
     */
    ServerResponse forget_get_question(String username);

    /**
     * 检查密保问题是否正确
     *
     * @param username
     * @param question
     * @param answer
     * @return
     */
    ServerResponse forget_check_answer(String username, String question, String answer);

    /**
     * 修改密码
     *
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken);

    /**
     * 登陆状态下 修改密码
     */
    ServerResponse login_reset_password(String username, String passwordOld, String passwordNew);

    /**
     * 检查用户名或者邮箱是否有效
     */
    ServerResponse check_valied(String str, String type);

    /**
     * 登陆状态下 修改个人信息
     */
    ServerResponse update_user_info(UserInfo user);

    /**
     * 根据id 查询用户
     */
    UserInfo selectById(int id);


    //将  token保存在数据库中 ，免登陆
    ServerResponse updateToken(String token , Integer userId);

    UserInfo selectUserInfoByToken(String token);
}
