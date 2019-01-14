package com.mine.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 杜晓鹏
 * @create 2019-01-11 17:19
 */
@Data
public class CartOrderItemVO implements Serializable {
    private List<OrderItemVO> orderItemVOList;
    private String imageHost;
    private BigDecimal totalPrice;
}
