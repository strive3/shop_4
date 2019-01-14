package com.mine.service.impl;

import com.google.common.collect.Lists;
import com.mine.common.Const;
import com.mine.common.ResponseCode;
import com.mine.common.ServerResponse;
import com.mine.dao.CartMapper;
import com.mine.dao.ProductMapper;
import com.mine.pojo.Cart;
import com.mine.pojo.Product;
import com.mine.service.CartService;
import com.mine.utils.BigDecimalUtils;
import com.mine.vo.CartProductVO;
import com.mine.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 杜晓鹏
 * @create 2019-01-09 19:01
 */
@Service
public class CartServiceImpl implements CartService {


    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductMapper productMapper;

    @Override
    public ServerResponse add(Integer userId, Integer productId, Integer count) {
        //step1:非空验证
        if (productId == null || count == null)
            return ServerResponse.serverResponseError("参数不能为空");
        if (productMapper.selectByPrimaryKey(productId) == null)
            return ServerResponse.serverResponseSuccess("要添加的商品不存在");

        //step2:根据productId和userId 查询购物信息
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        //如果cart为空，那么就是添加购物车
        if (cart == null) {
            Cart cart1 = new Cart();
            cart1.setUserId(userId);
            cart1.setProductId(productId);
            //添加商品时 默认选中
            cart1.setChecked(Const.CartCheckedEnum.PRODUCT_CHECKED.getCode());
            cart1.setQuantity(count);
            cartMapper.insert(cart1);
        } else {
            cart.setQuantity(count);
            cart.setProductId(productId);
            cart.setUserId(userId);
            cartMapper.updateByPrimaryKey(cart);
        }
        CartVO cartVO = getCartVOLimit(userId);

        return ServerResponse.serverResponseSuccess(cartVO);
    }

    @Override
    public ServerResponse list(Integer userId) {
        CartVO cartVO = getCartVOLimit(userId);

        return ServerResponse.serverResponseSuccess(cartVO);
    }

    @Override
    public ServerResponse update(Integer userId, Integer productId, Integer count) {
        if (productId == null)
            return ServerResponse.serverResponseError("商品id不能为空");

        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        if (cart == null)
            return ServerResponse.serverResponseError("购物车不能为空");
        //设置cart中产品的数量
        cart.setQuantity(count);
        int result = cartMapper.updateByPrimaryKey(cart);
            if (result <= 0 )
                return ServerResponse.serverResponseError("更新失败");
        CartVO cartVO = getCartVOLimit(userId);
        return ServerResponse.serverResponseSuccess(cartVO);
    }

    @Override
    public ServerResponse delete_product(Integer userId, String productIds) {
        if (productIds == null || productIds.equals(""))
            return ServerResponse.serverResponseError("没有选中所要删除的商品...");

        String[] strings = productIds.split(",");
        List<String> stringList = null;
        if (strings != null || strings.length > 0)
            stringList = Lists.newArrayList(strings);

        int delete = cartMapper.deleteProduct(userId, stringList);
        if (delete > 0)
            return ServerResponse.serverResponseSuccess();
        return ServerResponse.serverResponseError("删除失败");

    }

    @Override
    public ServerResponse select(Integer userId, Integer productId, Integer check) {
        //如果全选 或者全不选时，传入的productId为空
        if (productId == null) {
            //先判断购物车是否为空
            List<Cart> carts = cartMapper.selectCartByUserId(userId);
            if (carts == null || carts.size() == 0)
                return ServerResponse.serverResponseError("购物车为空");

            int select = cartMapper.selectOrUnselectProduct(userId, productId, check);
            if (select > 0)
                return ServerResponse.serverResponseSuccess(getCartVOLimit(userId));
            return ServerResponse.serverResponseError("操作失败");
        }
        //选择某个商品时
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        if (cart == null)
            return ServerResponse.serverResponseError("购物车为空");

        int result = cartMapper.selectOrUnselectProduct(userId, productId, check);
        if (result > 0)
            return ServerResponse.serverResponseSuccess(getCartVOLimit(userId));

        return ServerResponse.serverResponseError("操作失败");
    }

    @Override
    public ServerResponse get_cart_product_count(Integer userId) {
        int cart_product_count = cartMapper.get_cart_product_count(userId);
        return ServerResponse.serverResponseSuccess(ResponseCode.SUCCESS,cart_product_count);
    }


    private CartVO getCartVOLimit(Integer userId) {
        CartVO cartVO = new CartVO();
        //根据userId查询cart集合
        List<Cart> carts = cartMapper.selectCartByUserId(userId);
        //购物车总价格
        BigDecimal cartTotalPrice = new BigDecimal("0");
        //将Cart集合转化为CartProductVO集合
        List<CartProductVO> cartProductVOList = Lists.newArrayList();
        if (carts != null && carts.size() > 0) {
            for (Cart cart : carts) {
                CartProductVO cartProductVO = new CartProductVO();
                cartProductVO.setId(cart.getId());
                cartProductVO.setQuantity(cart.getQuantity());
                cartProductVO.setUserId(userId);
                cartProductVO.setProductChecked(cart.getChecked());
                //查询商品
                Product product = productMapper.selectByPrimaryKey(cart.getProductId());
                if (product != null) {
                    //设置值
                    cartProductVO.setProductId(cart.getProductId());
                    cartProductVO.setProductMainImage(product.getMainImage());
                    cartProductVO.setProductName(product.getName());
                    cartProductVO.setProductPrice(product.getPrice());
                    cartProductVO.setProductStatus(product.getStatus());
                    cartProductVO.setProductStock(product.getStock());
                    cartProductVO.setProductSubtitle(product.getSubtitle());
                    int stock = product.getStock();
                    int limitProductCount = 0;
                    if (stock >= cart.getQuantity()) {
                        limitProductCount = cart.getQuantity();
                        cartProductVO.setLimitQuantity("LIMIT_NUM_SUCCESS");
                    } else {//商品库存不足
                        limitProductCount = stock;
                        //更新购物车中商品的数量
                        Cart cart1 = new Cart();
                        cart1.setId(cart.getId());
                        cart1.setQuantity(stock);
                        cart1.setProductId(cart.getProductId());
                        cart1.setChecked(cart.getChecked());
                        cart1.setUserId(userId);
                        cartMapper.updateByPrimaryKey(cart1);

                        cartProductVO.setLimitQuantity("LIMIT_NUM_FAIL");
                    }
                    //计算商品总价格  商品价格*商品数量
                    cartProductVO.setQuantity(limitProductCount);
                    cartProductVO.setProductTotalPrice(BigDecimalUtils.mul(product.getPrice().doubleValue(), Double.valueOf(cartProductVO.getQuantity())));
                }
                //计算出购物车的总价
                if (cartProductVO.getProductChecked() == Const.CartCheckedEnum.PRODUCT_CHECKED.getCode()) {//被选中，计算总价
                    cartTotalPrice = BigDecimalUtils.add(cartTotalPrice.doubleValue(), cartProductVO.getProductTotalPrice().doubleValue());
                }
                cartProductVOList.add(cartProductVO);
            }
        }

        cartVO.setCartProductVOList(cartProductVOList);
        //step3: 给购物车设置总价格
        cartVO.setCartTotalPrice(cartTotalPrice);

        //step4:判断购物车是否全选
        int count = cartMapper.isCheckedAll(userId);
        if (count > 0) {
            cartVO.setAllChecked(false);
        } else {
            cartVO.setAllChecked(true);
        }

        //step5:返回结果
        return cartVO;
    }
}
