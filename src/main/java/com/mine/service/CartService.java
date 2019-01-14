package com.mine.service;

import com.mine.common.ServerResponse;

/**
 * @author 杜晓鹏
 * @create 2019-01-09 19:00
 */
public interface CartService {

    /**
     * 像购物车中添加商品
     */
    ServerResponse add(Integer userId, Integer productId, Integer count);

    /**
     * 用户查看购物车详情
     */
    ServerResponse list(Integer userId);

    /**
     * 修改购物车中某个商品的数量
     */
    ServerResponse update(Integer userId, Integer productId, Integer count);

    /**
     * 删除购物车中被选中的产品
     * @param userId        用户id
     * @param productIds    被选中的产品id
     * @return
     */
    ServerResponse delete_product(Integer userId, String productIds);

    ServerResponse select(Integer userId,  Integer productId,Integer check);

    ServerResponse get_cart_product_count(Integer userId);
}
