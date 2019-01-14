package com.mine.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 杜晓鹏
 * @create 2019-01-09 18:30
 */
@Data
public class CartProductVO {

    private Integer id;//
    private Integer userId;//
    private Integer productId;//
    private Integer quantity;//
    private String productName;//
    private String productSubtitle;//
    private String productMainImage;//
    private BigDecimal productPrice;//
    private Integer productStatus;//
    private BigDecimal productTotalPrice;//
    private Integer productStock;//
    private Integer productChecked;//
    private String limitQuantity;//


}
