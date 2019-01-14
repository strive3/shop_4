package com.mine.vo;

import com.mine.vo.CartProductVO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 杜晓鹏
 * @create 2019-01-09 18:29
 *
 * 购物车实体类VO
 */
@Data
public class CartVO implements Serializable {
    private List<CartProductVO> cartProductVOList;
    /**
     * 是否全选
     */
    private boolean isAllChecked;
    /**
     * 总价
     */
    private BigDecimal cartTotalPrice;


}
