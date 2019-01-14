package com.mine.service;

import com.mine.common.ServerResponse;

/**
 * @author 杜晓鹏
 * @create 2019-01-07 14:50
 */
public interface CategoryService {
    /**
     * 获取品类之类别（平级）
     */
    ServerResponse get_category(Integer categoryId);

    /**
     * 增加类别
     */
    ServerResponse add_category(Integer parentId,String categoryName);

    /**
     * 修改类别
     */
    ServerResponse set_category_name(Integer categoryId, String categoryName);

    /**
     * 获取当前分类id并递归子节点categoryId
     */
    ServerResponse get_deep_category(Integer categoryId);
}
