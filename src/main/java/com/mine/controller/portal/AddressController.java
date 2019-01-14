package com.mine.controller.portal;

import com.mine.common.Const;
import com.mine.common.ServerResponse;
import com.mine.pojo.Shipping;
import com.mine.pojo.UserInfo;
import com.mine.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author 杜晓鹏
 * @create 2019-01-10 19:59
 */
@RestController
@RequestMapping("/shipping")
public class AddressController {

    @Autowired
    AddressService addressService;

    @RequestMapping("/add.do")
    public ServerResponse add(HttpSession session, Shipping shipping) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        return addressService.add(userInfo.getId(),shipping);
    }
    @RequestMapping("/del.do")
    public ServerResponse del(HttpSession session, Integer shippingId) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        return addressService.del(userInfo.getId(),shippingId);
    }
    @RequestMapping("/update.do")
    public ServerResponse update(HttpSession session, Shipping shipping) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());
        shipping.setUserId(userInfo.getId());
        return addressService.update(shipping);
    }
    @RequestMapping("/select.do")
    public ServerResponse select(HttpSession session, Integer shippingId) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        return addressService.select(userInfo.getId(),shippingId);
    }
    @RequestMapping("/list.do")
    public ServerResponse list(HttpSession session,
                              @RequestParam(required = false,defaultValue = "0") Integer pageNum,
                              @RequestParam(required = false,defaultValue = "10") Integer pageSize) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        return addressService.list(pageNum,pageSize);
    }
}
