package com.mine.service;

import com.mine.common.ServerResponse;
import com.mine.pojo.Product;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 杜晓鹏
 * @create 2019-01-08 13:48
 */
public interface ProductService {
    /**
     * 添加或者更新商品
     *
     * @param product
     * @return
     */
    ServerResponse saveOrUpdate(Product product);

    /**
     * 更新商品的状态
     *
     * @param productId
     * @param status
     * @return
     */
    ServerResponse set_sale_status(Integer productId, Integer status);

    /**
     * 查看商品详情
     */
    ServerResponse detail(Integer productId);
    /**
     * 查询所有商品
     * */
    ServerResponse findAll(Integer pageNum, Integer pageSize);

    /**
     * 对商品进行搜素
     */
    ServerResponse search(String productName, Integer productId, Integer pageNum, Integer pageSize);

    /**
     * 前台商品详情
     */
    ServerResponse detail_portal(Integer productId);

    /**
     *产品搜索及动态排序
     * @param categoryId    类别id
     * @param keyword   关键字
     * @param pageNum   当前页数
     * @param pageSize  一页显示多少个
     * @param orderBy  排序方式
     */
    ServerResponse searchAndOrderBy(Integer categoryId,
                                    String keyword,
                                    Integer pageNum,
                                    Integer pageSize,
                                    String orderBy);

    ServerResponse upload(MultipartFile file, String path);
}
