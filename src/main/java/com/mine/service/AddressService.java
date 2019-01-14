package com.mine.service;

import com.mine.common.ServerResponse;
import com.mine.pojo.Shipping;

/**
 * @author 杜晓鹏
 * @create 2019-01-10 20:00
 */
public interface AddressService {
    /**
     * 添加地址
     * @param userId    用户id
     * @param shipping  地址
     * @return
     */
    ServerResponse add(Integer userId, Shipping shipping);

    /**
     * 删除地址
     * @param userId       用户id
     * @param shippingId    地址id
     * @return
     */
    ServerResponse del(Integer userId, Integer shippingId);

    /**
     * 更新地址
     * @param shipping  地址
     * @return
     */
    ServerResponse update(Shipping shipping);

    /**
     * 按照id查找地址    传入一个用户id防止横向越权
     * @param userId        用户id
     * @param shippingId    地址id
     * @return
     */
    ServerResponse select(Integer userId, Integer shippingId);

    /**
     * 分页查询
     * @param pageNum  当前页数
     * @param pageSize  每页多少条数据
     * @return
     */
    ServerResponse list(Integer pageNum, Integer pageSize);
}
