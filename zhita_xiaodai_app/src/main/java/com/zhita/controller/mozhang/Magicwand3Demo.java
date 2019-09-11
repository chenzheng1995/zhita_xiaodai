package com.zhita.controller.mozhang;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 魔杖3.0
 * 使用须知
 * 1: 替换魔蝎分配给商户的appid
 * 2: 使用与商户上传到开放平台匹配的私钥
 */
public class Magicwand3Demo {

    public static void main(String[] args) throws Exception {
//        enhance(Magicwand3Constant.APP_ID, Magicwand3Constant.PRIVATE_Key);
//    	enhance("899a41b1a78546aaa81581a06ae97cac", "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCT6t4ud6svRtUBs3wjyyGNEM7jTIhmrc91UWK9p5EHZI9V+mpve7AutinGbVi+FCbXnrK+5vN90emF7iMWvI9RawexrDWJ3kNn/19LhDbjq56DKkO4ITH6c9id3Q9YsBteQLgx/jRrs4PTZyAAeDg+ys8Xlt6qC6OYSaQZpE3lcSV9o+uYLFWSW9kZ04vVVuVpVLFRyzNEvbAqmYwFgtGwsQL2OXQBA6jdglES3d52Yp09r4e/tzvXIIeqwTYuUpUS8ehhO++o6dqpfCHIAByCo6U3k3lLD6BuDZF7nkYCN04kGiULwvYN6Bo+As47FmnVIAQyAnX657/tOLOUyBAJAgMBAAECggEAcZ4i88Zv82yRCTF41XWeW+DhSa7rxxMTG6ZcFhm0SN1xTDWBtAhqGDdMd2JenAKWo2D/I6v6tFac7Ycx5LEJ1RxFWLBSn4fedu+tny/7iX210EKSqci1vw9lAD1hIFqFayyDT8NXOLM3OxcnebTeXz0hPZlhh3RRQRSvvBJ80xOcguw+hZKg5KQLZuz3PA7wSwnd/m41H/FKTIh5mt2JPtAnqgGZDNEPNTUJhu+Tkn3PaV7UhV1+db3aDdMRcz5lWm1FT7oKH7rvYWqQU9mPltGw+YkHLtcVW5nvQB6DYUE0kRs2QkrGsOht9ULlh+UdlbKp/6hJs6Le3fbOMogO0QKBgQDeT0ymLo7Yjc5Zm9laRDQ8oiCPO1ublJIPGYiVOPdoVO3lP1ng9S9J+Ywf/W+ZABJe8Oh6KXtA5Fi6SnLhGRAw4iVFrDFBIgtm3pOYN91LZPNpTAmwQOJskp/TkfTWVj7tzLmoou39gCfa6ouG/uy6jX3iL/Sqr9PjKsiqLZMP7wKBgQCqVXRALO4cNyh17862ZJN48d6XVeaiYIAJJU/99m61eahF1mWCQIYtWNLIhxL3Ez4RjWl4xCKddj9FG+QnCb8qB5LjCvfganyncDcbvxMygo5oKspoPACNNFiCz0pCk1ApaDNZ3JdUpGAZGG4XCAi0C7nP5a+a8QWmr+Xa2sbnhwKBgAUprkwFtMeOs4YdOnUANH5b/YmWBVCdqK61FxjwE77WihfS2tGAoDKFmx17E0ZoXATcb2m24Ofm5bCd3gEgReH8voTZtOMgWUdwT9kvIvKHS6fcXjU7rIZ8+T0+nIMFybxYDs+9yO0kNsi8vbTK4tCKTKweFP6Jys/xxGs/V7T9AoGBAJerh9wtJcjSBKEfa71VSUuEc2PmBXvI2blLFDNbX7EQJxookjKtKczvLqGIRQbkh3wxoxz56Ki1RhlPgJSSRTozjEPG3Hlj2KNvdGKsfXeYcR2oHJs78nHJjpbL5Z/qdJKqSi9WERo5U6iE7GJtsPJBDq9kIj2hKBsIvW9nJ325AoGBALC2bzB08ECPRoxtjB6WvzJNnfelRf+RUsALwufsOVEfwVDpAdZVF0UayBB73L73fcRX24yrRSOL3ZdWylxcSux6l/dP7TyVdf8+t1HhqtwYbL4h4JQyiR2PzcZ2z2TNTbdyl5k5iGFdqFHB5s7hQHJP4z4W/bRvM2lS+85lEJ8I");
    	Magicwand3Demo magicwand3Demo = new Magicwand3Demo();
    	magicwand3Demo.enhance("赵朝志","330381198411110915","13780132026","1");
    }


    public String enhance(String name,String idcard,String mobile,String user_id) throws Exception {
        /** 业务参数 */
        Map<String, Object> bizParams = new HashMap<String, Object>();
        String appId = "899a41b1a78546aaa81581a06ae97cac";
        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCT6t4ud6svRtUBs3wjyyGNEM7jTIhmrc91UWK9p5EHZI9V+mpve7AutinGbVi+FCbXnrK+5vN90emF7iMWvI9RawexrDWJ3kNn/19LhDbjq56DKkO4ITH6c9id3Q9YsBteQLgx/jRrs4PTZyAAeDg+ys8Xlt6qC6OYSaQZpE3lcSV9o+uYLFWSW9kZ04vVVuVpVLFRyzNEvbAqmYwFgtGwsQL2OXQBA6jdglES3d52Yp09r4e/tzvXIIeqwTYuUpUS8ehhO++o6dqpfCHIAByCo6U3k3lLD6BuDZF7nkYCN04kGiULwvYN6Bo+As47FmnVIAQyAnX657/tOLOUyBAJAgMBAAECggEAcZ4i88Zv82yRCTF41XWeW+DhSa7rxxMTG6ZcFhm0SN1xTDWBtAhqGDdMd2JenAKWo2D/I6v6tFac7Ycx5LEJ1RxFWLBSn4fedu+tny/7iX210EKSqci1vw9lAD1hIFqFayyDT8NXOLM3OxcnebTeXz0hPZlhh3RRQRSvvBJ80xOcguw+hZKg5KQLZuz3PA7wSwnd/m41H/FKTIh5mt2JPtAnqgGZDNEPNTUJhu+Tkn3PaV7UhV1+db3aDdMRcz5lWm1FT7oKH7rvYWqQU9mPltGw+YkHLtcVW5nvQB6DYUE0kRs2QkrGsOht9ULlh+UdlbKp/6hJs6Le3fbOMogO0QKBgQDeT0ymLo7Yjc5Zm9laRDQ8oiCPO1ublJIPGYiVOPdoVO3lP1ng9S9J+Ywf/W+ZABJe8Oh6KXtA5Fi6SnLhGRAw4iVFrDFBIgtm3pOYN91LZPNpTAmwQOJskp/TkfTWVj7tzLmoou39gCfa6ouG/uy6jX3iL/Sqr9PjKsiqLZMP7wKBgQCqVXRALO4cNyh17862ZJN48d6XVeaiYIAJJU/99m61eahF1mWCQIYtWNLIhxL3Ez4RjWl4xCKddj9FG+QnCb8qB5LjCvfganyncDcbvxMygo5oKspoPACNNFiCz0pCk1ApaDNZ3JdUpGAZGG4XCAi0C7nP5a+a8QWmr+Xa2sbnhwKBgAUprkwFtMeOs4YdOnUANH5b/YmWBVCdqK61FxjwE77WihfS2tGAoDKFmx17E0ZoXATcb2m24Ofm5bCd3gEgReH8voTZtOMgWUdwT9kvIvKHS6fcXjU7rIZ8+T0+nIMFybxYDs+9yO0kNsi8vbTK4tCKTKweFP6Jys/xxGs/V7T9AoGBAJerh9wtJcjSBKEfa71VSUuEc2PmBXvI2blLFDNbX7EQJxookjKtKczvLqGIRQbkh3wxoxz56Ki1RhlPgJSSRTozjEPG3Hlj2KNvdGKsfXeYcR2oHJs78nHJjpbL5Z/qdJKqSi9WERo5U6iE7GJtsPJBDq9kIj2hKBsIvW9nJ325AoGBALC2bzB08ECPRoxtjB6WvzJNnfelRf+RUsALwufsOVEfwVDpAdZVF0UayBB73L73fcRX24yrRSOL3ZdWylxcSux6l/dP7TyVdf8+t1HhqtwYbL4h4JQyiR2PzcZ2z2TNTbdyl5k5iGFdqFHB5s7hQHJP4z4W/bRvM2lS+85lEJ8I";

//        bizParams.put("user_id", "1");
        //用户注册的三要素信息
//        bizParams.put("name", "赵朝志");
//        bizParams.put("idcard", "330381198411110915");
//        bizParams.put("mobile", "13780132026");


        Magicwand3Handler handler = Magicwand3Handler.builder()
                .withAppId(appId)
                .withPrivateKey(privateKey)
                .withMethod(Magicwand3Method.magicwand3_standard)
                .withBizParams(bizParams)
                .build();

        return handler.executeGetMethod();
    }
}
