package com.mine.controller.portal;

import com.mine.common.Const;
import com.mine.common.ServerResponse;
import com.mine.pojo.UserInfo;
import com.mine.service.UserService;
import com.mine.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 杜晓鹏
 * @create 2019-01-04 18:50
 *
 * 前台用户模块
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 登陆
     */
    @RequestMapping("/login.do")
    public ServerResponse login(HttpSession session, String username, String password, HttpServletResponse response) {


        ServerResponse serverResponse = userService.login(username, password);

        if (serverResponse.isSuccess()) {//登陆成功
            UserInfo userInfo = (UserInfo) serverResponse.getData();
            //如果登陆成功  将token 保存在cookie中
            String token = MD5Utils.getMD5(username+password);
            //token保存到数据库
            userService.updateToken(token,userInfo.getId());
            Cookie cookie = new Cookie(Const.CookieEnum.TOKEN.getDesc(),token);
            cookie.setMaxAge(Const.CookieEnum.MIX_AGE.getCode());  //7天
            cookie.setHttpOnly(true);
            //设置cookie的路径
            cookie.setPath("/business");
            response.addCookie(cookie);

            userInfo.setPassword("");
            session.setAttribute(Const.CURRENTUSER, userInfo);
        }
        return serverResponse;
    }

    /**
     * 注册
     */
    @RequestMapping("/register.do")
    public ServerResponse register(UserInfo userInfo) {
        //调用service中的添加方法
        ServerResponse serverResponse = userService.register(userInfo);
        return serverResponse;
    }

    /**
     * 根据用户名查找密保问题
     */
    @RequestMapping("/forget_get_question.do")
    public ServerResponse forget_get_question(String username) {

        ServerResponse serverResponse = userService.forget_get_question(username);
        return serverResponse;
    }

    /**
     * 提交问题答案
     */
    @RequestMapping("/forget_check_answer.do")
    public ServerResponse forget_check_answer(String username, String question, String answer) {
        ServerResponse serverResponse = userService.forget_check_answer(username, question, answer);
        return serverResponse;
    }

    /**
     * 忘记密码的重置密码
     */
    @RequestMapping("/forget_reset_password.do")
    public ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken) {

        ServerResponse serverResponse = userService.forget_reset_password(username, passwordNew, forgetToken);
        return serverResponse;
    }

    /**
     * 用户在登陆状态下修改密码
     */
    @RequestMapping("/login_reset_password.do")
    public ServerResponse login_reset_password(HttpSession session, String passwordOld, String passwordNew) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        String username = userInfo.getUsername();

        ServerResponse serverResponse = userService.login_reset_password(username, passwordOld, passwordNew);
        return serverResponse;
    }

    /**
     * 检查用户名或者邮箱是否有效
     */
    @RequestMapping("/check_valied.do")
    public ServerResponse check_valied(String str, String type) {

        ServerResponse serverResponse = userService.check_valied(str, type);
        return serverResponse;
    }

    /**
     * 获取登陆用户的信息
     */
    @RequestMapping("/get_user_info.do")
    public ServerResponse get_user_info(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //如果用户为空   说明用户未登陆
        if (userInfo == null)
            return ServerResponse.serverResponseError("用户未登陆");

        userInfo.setPassword("");
        return ServerResponse.serverResponseSuccess(userInfo);
    }

    /**
     * 登陆状态更新个人信息
     */
    @RequestMapping("/update_user_info.do")
    public ServerResponse update_user_info(HttpSession session, UserInfo user) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //如果用户为空   说明用户未登陆
        if (user == null) {
            return ServerResponse.serverResponseError("用户未登陆");
        }

        user.setId(userInfo.getId());
        ServerResponse serverResponse = userService.update_user_info(user);
        if (serverResponse.isSuccess()){
            //更新session中的用户信息
            UserInfo userInfo1 = userService.selectById(user.getId());
            session.setAttribute(Const.CURRENTUSER,userInfo1);
        }
        return serverResponse;
    }

    /**
     * 退出登陆
     */
    @RequestMapping("/logout.do")
    public ServerResponse logout(HttpSession session){
        session.removeAttribute(Const.CURRENTUSER);
        return ServerResponse.serverResponseSuccess("退出成功");
    }
}
