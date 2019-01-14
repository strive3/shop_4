package com.mine.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 杜晓鹏
 * @create 2019-01-11 17:09
 */
@Data
public class OrderItemVO implements Serializable {
    private Long  orderNo;
    private Integer productId;
    private String productName;
    private String productImage;
    private BigDecimal currentUnitPrice;
    private Integer  quantity;
    private BigDecimal totalPrice;
    private String createTime;
}
