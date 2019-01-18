package com.mine.filter;

import com.google.gson.Gson;
import com.mine.common.Const;
import com.mine.common.ServerResponse;
import com.mine.pojo.UserInfo;
import com.mine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author 杜晓鹏
 * @create 2019-01-17 11:42
 *
 * 用来对访问进行拦截
 * 实现自动登录
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("进行拦截判断。。。。");
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null){
            //先取出cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(Const.CookieEnum.TOKEN.getDesc())) {
                        //根据token查找用户  ，如果查询到  绑定到session中
                        String cookieValue = cookie.getValue();
                        UserInfo user = userService.selectUserInfoByToken(cookieValue);
                        if (user != null)
                            session.setAttribute(Const.CURRENTUSER, userInfo);
                        break;
                    }
                }
            }
        }

        //重构HttpServerletResponse
        if(userInfo==null){
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter printWriter=response.getWriter();
            if(userInfo==null){
                //未登录
                ServerResponse serverResponse=ServerResponse.serverResponseError("用户未登录");
                Gson gson=new Gson();
                String json=gson.toJson(serverResponse);
                printWriter.write(json);
            }else{
                //无权限操作
                ServerResponse serverResponse=ServerResponse.serverResponseError("无权限操作");
                Gson gson=new Gson();
                String json=gson.toJson(serverResponse);
                printWriter.write(json);
            }
            printWriter.flush();
            printWriter.close();
            return false;
        }

        //这里的return true 表示继续执行
        return true;
    }
}
