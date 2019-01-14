package com.mine.controller.backend;

import com.google.common.collect.Lists;
import com.mine.common.Const;
import com.mine.common.ServerResponse;
import com.mine.pojo.Product;
import com.mine.pojo.UserInfo;
import com.mine.service.ProductService;
import com.mine.utils.FTPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * @author 杜晓鹏
 * @create 2019-01-08 08:51
 */
@RestController
@RequestMapping("/manager/product")
public class ProductController {

    @Autowired
    ProductService productService;

    /**
     * 更新或者添加商品
     */
    @RequestMapping("/save.do")
    public ServerResponse saveOrUpdate(HttpSession session, Product product) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        //判断用户有没有普通管理员的权限
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode())
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(), Const.ResponseCodeEnum.NO_PRIVILEGE.getDescr());


        return productService.saveOrUpdate(product);
    }

    /**
     * 商品上下架
     */
    @RequestMapping("/set_sale_status.do")
    public ServerResponse set_sale_status(HttpSession session, Integer productId, Integer status) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        //判断用户有没有普通管理员的权限
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode())
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(), Const.ResponseCodeEnum.NO_PRIVILEGE.getDescr());


        return productService.set_sale_status(productId, status);
    }

    /**
     * 查看商品详情
     */
    @RequestMapping("/detail.do")
    public ServerResponse detail(HttpSession session, Integer productId) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        //判断用户有没有普通管理员的权限
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode())
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(), Const.ResponseCodeEnum.NO_PRIVILEGE.getDescr());


        return productService.detail(productId);
    }
    /**
     * 查看商品详情
     */
    @RequestMapping("/list.do")
    public ServerResponse detail(HttpSession session,
                                 @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                 @RequestParam(required = false,defaultValue = "10")Integer pageSize) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        //判断用户有没有普通管理员的权限
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode())
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(), Const.ResponseCodeEnum.NO_PRIVILEGE.getDescr());


        return productService.findAll(pageNum,pageSize);
    }

    /**
     * 产品搜索
     */
    @RequestMapping("/search.do")
    public ServerResponse detail(HttpSession session,
                                 @RequestParam(value = "productName",required = false)String productName,
                                 @RequestParam(value = "productId",required = false)Integer productId,
                                 @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize) {
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        //判断用户有没有普通管理员的权限
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode())
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(), Const.ResponseCodeEnum.NO_PRIVILEGE.getDescr());


        return productService.search(productName,productId,pageNum,pageSize);
    }



}
