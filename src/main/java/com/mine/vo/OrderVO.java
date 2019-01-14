package com.mine.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 杜晓鹏
 * @create 2019-01-11 17:10
 */
@Data
public class OrderVO implements Serializable {
    private Long orderNo;
    private BigDecimal payment;
    private Integer paymentType;
    private String paymentTypeDesc;
    private Integer postage;
    private Integer status;
    private String statusDesc;
    private String paymentTime;
    private String sendTime;
    private String endTime;
    private String closeTime;
    private String createTime;

    private List<OrderItemVO> orderItemVoList;
    private String  imageHost;
    private Integer shippingId;
    private String receiverName;
    private ShippingVO shippingVo;
}
