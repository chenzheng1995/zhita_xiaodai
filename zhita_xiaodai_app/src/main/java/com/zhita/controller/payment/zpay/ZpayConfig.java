package com.zhita.controller.payment.zpay;

/**
 * @author chenyun
 * @create 2018-09-19 17:06
 */
public class ZpayConfig {


    /**
     * 点对点支付
     * MERCHANT_NO : 商户号（后期替换自己）
     * MD5_KEY : 加签key (后期替换自己)
     */
    public final static String MERCHANT_NO="M20190301QH910U";
    public final static String MD5_KEY="XHWRLW8G9BJJmzHgBg24VEgWjtN971gv";

    //点对点——异步通知url
    public static final String RECHARGE_NOTIFY="https://z.ibwin.cn:4433/api/gateway";

    /**
     * 企业支付
     * NEW_MERCHANT_NO : 商户号（后期替换自己）
     * NEW_MD5_KEY : 加签key (后期替换自己)
     */
    public final static String NEW_MERCHANT_NO="M20190301QH910U";
    public final static String NEW_MD5_KEY="XHWRLW8G9BJJmzHgBg24VEgWjtN971gv";
    
    public final static String GATEWAY_URL="https://z.ibwin.cn:4433/api/gateway";
   // https://z.ibwin.cn:4433/api/gateway
    //企业支付——异步通知url
    public static final String RECHARGE_NOTIFY_NEW="https://mdbhk.rong51dai.com/newpay/callbackpay";//测试服放款

}
