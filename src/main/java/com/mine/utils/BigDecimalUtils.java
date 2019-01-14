package com.mine.utils;

import java.math.BigDecimal;

/**
 * @author 杜晓鹏
 * @create 2019-01-09 19:01
 */
public class BigDecimalUtils {

    /**
     * 相加
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal add(Double d1, Double d2){
        BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(d1));
        BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(d2));

        return bigDecimal1.add(bigDecimal2);
    }

    /**
     * 相减
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal sub(Double d1, Double d2){
        BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(d1));
        BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(d2));

        return bigDecimal1.subtract(bigDecimal2);
    }

    /**
     * 相除,保留两位小数，四舍五入
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal div(Double d1, Double d2){
        BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(d1));
        BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(d2));

        return bigDecimal1.divide(bigDecimal2,2,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 相乘
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal mul(Double d1, Double d2){
        BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(d1));
        BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(d2));

        return bigDecimal1.multiply(bigDecimal2);
    }
}
