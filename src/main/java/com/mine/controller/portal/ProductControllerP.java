package com.mine.controller.portal;

import com.mine.common.ServerResponse;
import com.mine.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杜晓鹏
 * @create 2019-01-09 10:56
 */
@RestController
@RequestMapping("/product")
public class ProductControllerP {
    @Autowired
    ProductService productService;

    /**
     * 前台查看商品详情
     *
     * @param productId id
     */
    @RequestMapping("/detail.do")
    public ServerResponse detail_portal(Integer productId) {

        return productService.detail_portal(productId);
    }

    /**
     * 产品搜索及动态排序
     */
    @RequestMapping("/list.do")
    public ServerResponse searchAndOrderBy(@RequestParam(required = false) Integer categoryId,
                                           @RequestParam(required = false) String keyword,
                                           @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                           @RequestParam(required = false, defaultValue = "") String orderBy) {


        return productService.searchAndOrderBy(categoryId, keyword, pageNum, pageSize, orderBy);
    }
}
