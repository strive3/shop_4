package com.mine.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 杜晓鹏
 * @create 2019-01-08 14:53
 */
@Data
public class ProductDetailVO implements Serializable {
    private Integer id;
    private Integer categoryId;
    private Integer parentCategoryId;
    private String  name;//
    private String  subtitle;//oppo促销进行中",
    private String  imageHost;//http://img.business.com/",
    private String  mainImage;//mainimage.jpg",
    private String  subImages;//[\"business/aa.jpg\",\"business/bb.jpg\",\"business/cc.jpg\",\"business/dd.jpg\",\"business/ee.jpg\"]",
    private String  detail;//richtext",
    private BigDecimal price;// 2999.11,
    private Integer stock;// 71,
    private Integer status;// 1,
    private String createTime;// "2016-11-20 14:21:53",
    private String updateTime;//2016-11-20 14:21:53"


}
