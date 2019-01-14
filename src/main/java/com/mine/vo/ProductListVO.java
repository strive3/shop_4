package com.mine.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 杜晓鹏
 * @create 2019-01-08 15:45
 */
@Data
public class ProductListVO implements Serializable {
    private Integer id;
    private Integer categoryId;
    private String  name;//
    private String  subtitle;//oppo促销进行中",
    private Integer status;// 1,
    private String  mainImage;//mainimage.jpg",
    private BigDecimal price;// 2999.11,


}
