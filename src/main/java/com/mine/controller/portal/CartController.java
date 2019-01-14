package com.mine.controller.portal;

import com.mine.common.Const;
import com.mine.common.ServerResponse;
import com.mine.pojo.UserInfo;
import com.mine.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author 杜晓鹏
 * @create 2019-01-09 19:08
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    /**
     * 购物车中添加商品
     *
     * @param session
     * @param count     商品数量
     * @param productId 商品id
     * @return
     */
    @RequestMapping("/add.do")
    public ServerResponse add(HttpSession session, Integer count, Integer productId) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        return cartService.add(userInfo.getId(), productId, count);
    }

    /**
     * 更新购物车中某个产品的数量
     */
    @RequestMapping("/update.do")
    public ServerResponse update(HttpSession session, Integer count, Integer productId) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        return cartService.update(userInfo.getId(), productId, count);
    }

    /**
     * 用户查看自己的购物车
     */
    @RequestMapping("/list.do")
    public ServerResponse list(HttpSession session) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        return cartService.list(userInfo.getId());
    }

    /**
     * 移除购物车某个产品
     */
    @RequestMapping("/delete_product.do")
    public ServerResponse delete_product(HttpSession session, String productIds) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        return cartService.delete_product(userInfo.getId(), productIds);
    }

    /**
     * 购物车中选中某个商品
     */
    @RequestMapping("/select.do")
    public ServerResponse select(HttpSession session, Integer productId) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        return cartService.select(userInfo.getId(), productId,Const.CartCheckedEnum.PRODUCT_CHECKED.getCode());
    }

    /**
     * 购物车中选中某个商品
     */
    @RequestMapping("/un_select.do")
    public ServerResponse un_select(HttpSession session, Integer productId, Integer check) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        return cartService.select(userInfo.getId(), productId, Const.CartCheckedEnum.PRODUCT_UNCHECK.getCode());
    }

    /**
     * 购物车中选中某个商品
     */
    @RequestMapping("/select_all.do")
    public ServerResponse select_all(HttpSession session, Integer productId,Integer check) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        return cartService.select(userInfo.getId(), productId,Const.CartCheckedEnum.PRODUCT_CHECKED.getCode());
    }

    /**
     * 购物车中选中某个商品
     */
    @RequestMapping("/un_select_all.do")
    public ServerResponse un_select_all(HttpSession session, Integer productId,Integer check) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        return cartService.select(userInfo.getId(), productId,Const.CartCheckedEnum.PRODUCT_UNCHECK.getCode());
    }

    /**
     * 查询在购物车里的产品数量
     */
    @RequestMapping("/get_cart_product_count.do")
    public ServerResponse get_cart_product_count(HttpSession session) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        return cartService.get_cart_product_count(userInfo.getId());
    }
}
