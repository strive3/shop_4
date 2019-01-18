package com.mine.common;

/**
 * @author 杜晓鹏
 * @create 2019-01-04 19:56
 */
public class Const {
    public static final String CURRENTUSER = "current_user";


    public  static final String TRADE_SUCCESS= "TRADE_SUCCESS";

    public static  final String AUTOLOGINTOKEN="autoLoginToken";
    /**
     * 定义一个枚举  存储状态码   2表示用户未登陆  3表示用户权限不足
     */
    public enum ResponseCodeEnum {
        NEED_LOGIN(2, "用户未登陆，请登陆"),
        NO_PRIVILEGE(3, "用户权限不足");
        private int code;
        private String descr;

        private ResponseCodeEnum(int code, String descr) {
            this.code = code;
            this.descr = descr;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }
    }
    /**
     * 定义一个枚举  存储状态码   1表示已选中  0表示未选中
     */
    public enum CartCheckedEnum {
        PRODUCT_CHECKED(1,"已选中"),
        PRODUCT_UNCHECK(0, "未选中");
        private int code;
        private String descr;

        private CartCheckedEnum(int code, String descr) {
            this.code = code;
            this.descr = descr;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }
    }

    /**
     * 用来存储商品的状态码
     */
    public enum ProductCodeEnum {
        PRODUCT_ONLINE(1, "上线"),
        PRODUCT_OFFLINE(2, "下线"),
        PRODUCT_DELETE(3, "删除");
        private int code;
        private String descr;

        private ProductCodeEnum(int code, String descr) {
            this.code = code;
            this.descr = descr;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }
    }

    /**
     * 定义一个枚举   0是管理员   1是普通用户
     */
    public enum RoleEnum {
        ROLE_ADMIN(0, "管理员"),
        ROLE_CUSTOMER(1, "普通用户");

        private int code;
        private String descr;

        private RoleEnum(int code, String descr) {
            this.code = code;
            this.descr = descr;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }
    }


    public  enum OrderStatusEnum{

        ORDER_CANCELED(0,"已取消"),
        ORDER_UN_PAY(10,"未付款"),
        ORDER_PAYED(20,"已付款"),
        ORDER_SEND(40,"已发货"),
        ORDER_SUCCESS(50,"交易成功"),
        ORDER_CLOSED(60,"交易关闭")
        ;
        private  int  code;
        private String desc;
        private OrderStatusEnum(int code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }


        public  static  OrderStatusEnum codeOf(Integer code){
            for(OrderStatusEnum orderStatusEnum: values()){
                if(code==orderStatusEnum.getCode()){
                    return  orderStatusEnum;
                }
            }
            return  null;
        }


    }

    public  enum PaymentEnum{

        ONLINE(1,"线上支付")
        ;
        private  int  code;
        private String desc;
        private PaymentEnum(int code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public  static  PaymentEnum codeOf(Integer code){
            for(PaymentEnum paymentEnum: values()){
                if(code==paymentEnum.getCode()){
                    return  paymentEnum;
                }
            }
            return  null;
        }


        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public  enum PaymentPlatformEnum{

        ALIPAY(1,"支付宝")
        ;
        private  int  code;
        private String desc;
        private PaymentPlatformEnum(int code,String desc){
            this.code=code;
            this.desc=desc;
        }




        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public  enum CookieEnum{

        TOKEN(0,"token"),
        MIX_AGE(60*60*24*7,"mix_age")
        ;
        private  int  code;
        private String desc;
        private CookieEnum(int code,String desc){
            this.code=code;
            this.desc=desc;
        }




        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
