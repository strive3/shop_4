package com.mine.service.impl;

import com.mine.common.Const;
import com.mine.common.ServerResponse;
import com.mine.dao.UserInfoMapper;
import com.mine.pojo.UserInfo;
import com.mine.redis.RedisApi;
import com.mine.service.UserService;
import com.mine.utils.MD5Utils;

import com.mine.utils.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author 杜晓鹏
 * @create 2019-01-04 19:41
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    RedisApi redisApi;
    /**
     * 登陆
     */
    @Override
    public ServerResponse login(String username, String password) {
        //非空判断
        if (username == null || username.equals(""))
            return ServerResponse.serverResponseError("用户名不能为空");
        if (password == null || password.equals(""))
            return ServerResponse.serverResponseError("密码不能为空");
        //查看当前用户存不存在
        int result = userInfoMapper.checkUsername(username);
        if (result == 0)
            return ServerResponse.serverResponseError("用户名不存在");
        //根据用户名和密码找到当前用户
        UserInfo userInfo = userInfoMapper.selectUserByUsernameAndPassword(username, MD5Utils.getMD5(password));
        if (userInfo == null) {
            return ServerResponse.serverResponseError("密码错误");
        }

        //返回结果
        return ServerResponse.serverResponseSuccess(userInfo);
    }
    /**
     * 更新用户的token   用于自动登录
     */
    public ServerResponse updateToken(String token, Integer userId){

        int i = userInfoMapper.updateToken(token, userId);
        if (i>0)
            return ServerResponse.serverResponseSuccess();
        return ServerResponse.serverResponseError("更新token失败");
    }
    /**
     * 注册
     */
    @Override
    public ServerResponse register(UserInfo userInfo) {
        //非空验证
        if (userInfo == null)
            return ServerResponse.serverResponseError("请填写用户信息");
        //检查用户名是否存在
        if (userInfoMapper.checkUsername(userInfo.getUsername()) > 0)
            return ServerResponse.serverResponseError("用户名已存在");
        //检查邮箱是否存在
        if (userInfoMapper.checkEmail(userInfo.getEmail()) > 0)
            return ServerResponse.serverResponseError("邮箱已存在");
        //给用户设置角色
        userInfo.setRole(Const.RoleEnum.ROLE_CUSTOMER.getCode());
        //给密码进行md5加密
        userInfo.setPassword(MD5Utils.getMD5(userInfo.getPassword()));
        //添加用户
        int result = userInfoMapper.insert(userInfo);
        if (result > 0)
            return ServerResponse.serverResponseSuccess("注册成功");
        return ServerResponse.serverResponseError("注册失败");
    }

    /**
     * 忘记密码的情况下  根据用户名 查找问题
     */
    @Override
    public ServerResponse forget_get_question(String username) {
        //非空验证
        if (username == null || username.equals(""))
            return ServerResponse.serverResponseError("用户名不能为空");
        //检查用户名存不存在
        if (userInfoMapper.checkUsername(username) == 0)
            return ServerResponse.serverResponseError("用户名不存在,请重新输入");

        String question = userInfoMapper.selectQuestionByUsername(username);
        if (question.equals("") || question == null)
            return ServerResponse.serverResponseError("密保问题为空");

        return ServerResponse.serverResponseSuccess(question);
    }

    /**
     * 检查用户输入的问题答案是否正确 ，并且生成一个token 防止横向越权
     */
    @Override
    public ServerResponse forget_check_answer(String username, String question, String answer) {
        //非空验证
        if (username == null || username.equals(""))
            return ServerResponse.serverResponseError("用户名不能为空");
        if (question == null || question.equals(""))
            return ServerResponse.serverResponseError("问题不能为空");
        if (answer == null || answer.equals(""))
            return ServerResponse.serverResponseError("答案不能为空");
        //根据 用户名  问题 答案 进行查询
        int result = userInfoMapper.selectByUsernameAndQuestionAndAnswer(username, question, answer);
        if (result == 0)
            ServerResponse.serverResponseError("答案错误");
        //服务端生成一个token保存并将token返回给客户端          这里的token是防止横向越权
        String forgetToken = UUID.randomUUID().toString();
        //Guava
        //TokenCache.set(username, forgetToken);
        //Redis
        redisApi.set(username,forgetToken);

        return ServerResponse.serverResponseSuccess(forgetToken);
    }

    /**
     * 忘记密码的情况下 修改密码
     */
    @Override
    public ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken) {
        //非空验证
        if (username == null || username.equals(""))
            return ServerResponse.serverResponseError("用户名不能为空");
        if (passwordNew == null || passwordNew.equals(""))
            return ServerResponse.serverResponseError("密码不能为空");
        if (forgetToken == null || forgetToken.equals(""))
            return ServerResponse.serverResponseError("token不能为空");
        //检查用户名是否存在
        if (userInfoMapper.checkUsername(username) == 0)
            return ServerResponse.serverResponseError("用户名不存在");
        //检查token是否过期
        if (TokenCache.get(username) == null || TokenCache.get(username).equals(""))
            return ServerResponse.serverResponseError("token已过时");
        //判断用户输入的token是否正确
        if (!forgetToken.equals(redisApi.get(username)))
            return ServerResponse.serverResponseError("token错误");

        //调用dao方法修改用户密码
        int result = userInfoMapper.updatePassword(username, MD5Utils.getMD5(passwordNew));
        if (result > 0)
            return ServerResponse.serverResponseSuccess();

        return ServerResponse.serverResponseError("密码修改失败");
    }

    /**
     * 登录状态下修改密码
     */
    @Override
    public ServerResponse login_reset_password(String username, String passwordOld, String passwordNew) {
        //非空验证
        if (username == null || username.equals(""))
            return ServerResponse.serverResponseError("用户未登陆");
        if (passwordOld == null || passwordOld.equals(""))
            return ServerResponse.serverResponseError("前密码不能为空");
        if (passwordNew == null || passwordNew.equals(""))
            return ServerResponse.serverResponseError("新密码不能为空");
        //查找到当前用户，  并且核实用户的用户名密码是否正确
        UserInfo userInfo = userInfoMapper.selectUserByUsernameAndPassword(username, MD5Utils.getMD5(passwordOld));
        if (userInfo == null)
            return ServerResponse.serverResponseError("密码错误，请重新输入");
        //对用户密码进行修改
        int result = userInfoMapper.updatePassword(username, MD5Utils.getMD5(passwordNew));
        if (result > 0)
            return ServerResponse.serverResponseSuccess();

        return ServerResponse.serverResponseError("密码修改失败");
    }

    @Override
    public ServerResponse check_valied(String str, String type) {
        //非空验证
        if (str == null || str.equals(""))
            return ServerResponse.serverResponseError("用户名或者邮箱不能为空");
        if (type == null || type.equals(""))
            return ServerResponse.serverResponseError("类型不能为空");

        if (type.equals("username")) {
            if (userInfoMapper.checkUsername(str) > 0)
                return ServerResponse.serverResponseError("用户名已存在");
            else
                return ServerResponse.serverResponseSuccess();
        } else if (type.equals("email")) {
            if (userInfoMapper.checkEmail(str) > 0)
                return ServerResponse.serverResponseError("邮箱已存在");
            else
                return ServerResponse.serverResponseSuccess();
        } else
            ServerResponse.serverResponseError("检验的类型错误");
        return ServerResponse.serverResponseError();
    }

    @Override
    public ServerResponse update_user_info(UserInfo user) {
        if (user == null)
            return ServerResponse.serverResponseError("参数不能为空");
        int result = userInfoMapper.updateUserBySelectActive(user);
        if (result > 0)
            return ServerResponse.serverResponseSuccess();
        return ServerResponse.serverResponseError("修改信息失败");
    }

    @Override
    public UserInfo selectById(int id){
        return userInfoMapper.selectByPrimaryKey(id);
    }


    //自动登录使用的 token
    @Override
    public UserInfo selectUserInfoByToken(String token){
        return userInfoMapper.selectUserInfoByToken(token);
    }
}
