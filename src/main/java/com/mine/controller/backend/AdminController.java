package com.mine.controller.backend;

import com.mine.common.Const;
import com.mine.common.ServerResponse;
import com.mine.pojo.UserInfo;
import com.mine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author 杜晓鹏
 * @create 2019-01-05 17:52
 *
 * 后台用户控制模块
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;
    /**
     * 登陆
     */
    @RequestMapping("/login.do")
    public ServerResponse login(HttpSession session, String username, String password) {

        ServerResponse serverResponse = userService.login(username, password);

        if (serverResponse.isSuccess()) {//登陆成功
            UserInfo userInfo = (UserInfo) serverResponse.getData();
            userInfo.setPassword("");
            //判断  如果登陆的用户 role 为1的话 不允许登陆
            if (userInfo.getRole() == Const.RoleEnum.ROLE_CUSTOMER.getCode())
                return ServerResponse.serverResponseError("无权限登陆");
            session.setAttribute(Const.CURRENTUSER, userInfo);
        }
        return serverResponse;
    }
}
