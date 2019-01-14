package com.mine.controller.backend;

import com.mine.common.Const;
import com.mine.common.ServerResponse;
import com.mine.pojo.UserInfo;
import com.mine.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.directory.SearchResult;
import javax.servlet.http.HttpSession;

/**
 * @author 杜晓鹏
 * @create 2019-01-07 14:11
 */
@RestController
@RequestMapping("/manager/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    /**
     * 获取品类之类别（平级）
     */
    @RequestMapping("/get_category.do")
    public ServerResponse get_category(HttpSession session, Integer categoryId){
        //先判断有没有用户登陆
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null)
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NEED_LOGIN.getCode(),Const.ResponseCodeEnum.NEED_LOGIN.getDescr());

        //判断用户有没有普通管理员的权限
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode())
            return ServerResponse.serverResponseError(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ResponseCodeEnum.NO_PRIVILEGE.getDescr());

        ServerResponse service_category = categoryService.get_category(categoryId);
        return service_category;
    }

    /**
     * 增加节点
     */
    @RequestMapping("/add_category.do")
    public ServerResponse add_category(@RequestParam(required = false,defaultValue = "0") Integer parentId, String categoryName){
        return categoryService.add_category(parentId,categoryName);
    }

    /**
     * 修改类别
     */
    @RequestMapping("/set_category_name.do")
    public ServerResponse set_category_name(Integer categoryId, String categoryName){
        return categoryService.set_category_name(categoryId,categoryName);
    }

    /**
     * 获取当前分类id并递归子节点categoryId
     */
    @RequestMapping("/get_deep_category.do")
    public ServerResponse get_deep_category(@RequestParam(required = false,defaultValue = "0") Integer categoryId){
        return categoryService.get_deep_category(categoryId);
    }
}

