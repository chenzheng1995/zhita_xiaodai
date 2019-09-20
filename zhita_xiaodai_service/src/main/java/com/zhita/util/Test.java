package com.zhita.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSONObject;

public class Test {
	

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//	RedisClientUtil redisClientUtil = new RedisClientUtil();
//	redisClientUtil.set("13586485199Key", "123456");
//	System.out.println(redisClientUtil.get("13586485199Key"));
//	System.out.println(redisClientUtil.getkeyAll());
//		String creationTime = System.currentTimeMillis()+"";
//		System.out.println(creationTime);
//		Map<String,Object> map = new HashMap<String,Object>();
//		Map<String,Object> map1 = new HashMap<String,Object>();
//		map1.put("String1", "String1");
//		map1.put("String2", "String2");
//		Map<String,Object> map2 = new HashMap<String,Object>();
//		map2.put("String3", "String3");
//		map2.put("String4", "String4");
//		map.put("map1", map1);
//		map.put("map2", map2);
//		map.put("String5", "String5");
//		map.put("String6", "String6");
		
//		PostAndGet pGet = new PostAndGet();
//		String code = "023zT3wB1P5bbd0w5quB1exowB1zT3wi";
//		 
//		String string = pGet.sendGet("https://api.weixin.qq.com/sns/jscode2session?js_code=" + code + "&appid=wxdea525a189135ccf" + "&secret=2810bb3053465c58e68125f41d3ca9b9" + "&grant_type=authorization_code");
//		JSONObject jsonObject = JSON.parseObject(string);
//		String openid =  (String) jsonObject.get("openid");
//		System.out.println(openid);
		
//		System.out.println(stampToDate("1548052959000"));
//		System.out.println("111");
		
//		Thread threads[]=new Thread[100];
//		for(int i=0;i<100;i++){
//			threads[i]=new Thread();
//			threads[i].start();
//			}
		
//		System.out.println(mobileEncrypt("13486070402"));
//		System.out.println(idEncrypt("330225199507155112"));
//	}
//
//    // 手机号码前三后四脱敏
//    public static String mobileEncrypt(String mobile) {
//        if (StringUtils.isEmpty(mobile) || (mobile.length() != 11)) {
//            return mobile;
//        }
//        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
//    }
//
//    //身份证前三后四脱敏
//    public static String idEncrypt(String id) {
//        if (StringUtils.isEmpty(id) || (id.length() < 8)) {
//            return id;
//        }
//        return id.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
//    }
	 
//	 public static String stampToDate(String s){
//	        String res;
//	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	        long lt = new Long(s);
//	        Date date = new Date(lt);
//	        res = simpleDateFormat.format(date);
//	        return res;
//	    }
//		String [] aStrings = {"多米记","xx1","xx2"};
//        String JSON_ARRAY_STR = "[\"多米记\",\"xx1\",\"xx2\"]";
//		JSONArray jsonArray = JSON.parseArray(JSON_ARRAY_STR);
//        int size = jsonArray.size();
//        for (int i = 0; i < size; i++){
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            System.out.println(jsonObject.getString("studentName")+":"+jsonObject.getInteger("studentAge"));
//        }
		
//		StringBuffer stringBuffer = new StringBuffer().append("a").append("b");
//		System.out.println(stringBuffer);
		
//		String string = "[\"多米记\",\"xx1\",\"xx2\"]";
//		string = string.replaceAll("\"", "").replace("[","").replace("]","");
//		System.out.println(string);
//		String [] stringArr= string.split(",");
//		for(int i=0;i<stringArr.length;i++){
//			System.out.println(stringArr[i]);
//			}
		
//		RedisClientUtil redisClientUtil = new RedisClientUtil();
//		redisClientUtil.set("czkey", "2019/2/25");
//		System.out.println(redisClientUtil.getkeyAll());
//		String SourceClick =redisClientUtil.get("123");
//		if(SourceClick==null) {
//			System.out.println(redisClientUtil.getkeyAll());
//			redisClientUtil.set("啦啦拉key","0");
//			System.out.println(redisClientUtil.getkeyAll());
//			System.out.println(redisClientUtil.getSourceClick("啦啦拉key"));
//			redisClientUtil.set("啦啦拉key","1");
//			System.out.println(redisClientUtil.getSourceClick("啦啦拉key"));
//		}else {
//			System.out.println(2);
//		}
//		
//		System.out.println(redisClientUtil.getkeyAll());
	
//		TuoMinUtil tuoMinUtil = new TuoMinUtil();
//		System.out.println(tuoMinUtil.nameEncrypt("陈峥"));
		
//		RedisClientUtil redisClientUtil = new RedisClientUtil();
//		redisClientUtil.set("融51yunying12019/02/26key","123");
//		System.out.println(redisClientUtil.getkeyAll());
//		System.out.println(redisClientUtil.get("融51yunying12019/02/26key"));
		
		
//		String[] x={"1","2","3","4"};
//		String a = x[1] ;
//		System.out.println(x[1]);
//		if(a=="2") {
//			System.out.println(1);
//		}else {
//			System.out.println(2);
//		}
		
//		String content = "今天是周五";
//		OssUtil ossUtil =new OssUtil();
//		ByteArrayInputStream InputStringStream = new ByteArrayInputStream(content.getBytes());
//		try {
//			String ossPath = ossUtil.uploadFile(InputStringStream, "news/article/"+"文章"+".txt");
//			System.out.println(ossPath);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		PostAndGet postAndGet = new PostAndGet();
//		String aString = postAndGet.sendGet1("https://wx-dc.oss-cn-zhangjiakou.aliyuncs.com/news/article/标题5.txt");
//		System.out.println(aString);
		
//		String aString  = "a"+"\n"+"\r"+"\n"+"\r"+"a";
//		System.out.println(aString);
//		String bString = aString.replaceAll("\r|\n","@newline");
//		System.out.println(bString);
 
		
//		File file = new File("E://demo");
//	       String[] strArray = file.list();
//	        for(String s : strArray){
//	            System.out.println(s);
//	        }

//		File file = new File("C:/Users/Administrator/Desktop/io流/袋多多/袋多多.png");
//		File file1 = new File("C:/Users/Administrator/Desktop/io流/袋多多1/袋多多.png");
//		BufferedInputStream iStream = new BufferedInputStream(new FileInputStream(file));
//		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file1));
//		byte [] b = new byte[1024];
//		int len =0;
//		try {
//			while ((len = iStream.read(b))!=-1) {
//				outputStream.write(b,0, len);	
//			}
//
//		} catch (Exception e) {
//System.out.println("复制图片出错");
//return;
//		}finally {
//			iStream.close();
//			outputStream.close();
//		}
//
//System.out.println(111111);
		
//		File file = new File("E:\\nginx-1.14.2\\html\\dist\\promote\\模板1\\index.html");
//		file.renameTo(new File("E:\\nginx-1.14.2\\html\\dist\\promote\\模板1\\index.txt"));
		
		
//		        String path="E:\\nginx-1.14.2\\html\\dist\\promote\\模板1\\index.txt";
//
//		        //待替换字符
//		        String aStr="shoplevel.html";
//		        //替换字符
//		        String bStr="../模板1/shoplevel.html";
//
//		        //读取文件
//		        File file=new File(path);
//
//		        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
//
//		        //内存流
//		        CharArrayWriter caw=new CharArrayWriter();
//
//		        //替换
//		        String line=null;
//
//		        //以行为单位进行遍历
//		        while((line=br.readLine())!=null){
//		            //替换每一行中符合被替换字符条件的字符串
//		            line=line.replaceAll(aStr, bStr);
//		            //将该行写入内存
//		            caw.write(line);
//		            //添加换行符，并进入下次循环
//		            caw.append(System.getProperty("line.separator"));
//		        }
//		        //关闭输入流
//		        br.close();
//
//		        //将内存中的流写入源文件
//		        FileWriter fw=new FileWriter(file);
//		        caw.writeTo(fw);
//		        fw.close();
		
//		int i =0;
//		try {
//        String a = null;
//        a.length();
//		} catch (Exception e) {
//         i = 1;
//		}
//		System.out.println(i);
//		    }
//		String apiKey = "798270c15e2c4dcaa15af86d2a8fc0b3";
//		int userId =1;
//		String phone ="13486070402";
//		String idcard ="330225199507155112";
//		String name = "陈峥";
//    	PostAndGet postAndGet = new PostAndGet();
//    	
////    	String result = postAndGet.sendGet("https://api.51datakey.com/h5/importV3/?apiKey="+apiKey+"&phone="+phone+"&idcard="+idcard+"&name="+name+"&userId="+userId);
//    		String result = postAndGet.sendGet("https://api.51datakey.com/h5/importV3/index.html#/carrier?apiKey="+apiKey+"&userId="+userId);
//    	System.out.println(result);

		
//		String aString = "香港/北京/上海/重庆";
//		String[] bStrings = aString.split("/");
//        for (int i = 0; i < bStrings.length; i++) {
//            System.out.println(bStrings[i]);
//        }
    	
		
//		PostUtil pUtil = new PostUtil();
//		String member_id ="8150735131";
//		String terminal_id ="8150735131";
//		String trans_id ="1";
//		String trade_date ="20190815120125";
//		String name ="陈峥";
//		String mobile ="13486070402";
//		String id_card ="330225199507155112";
//		String param = "{'member_id':'"+member_id+"','terminal_id':'"+terminal_id+"','trans_id':'"+trans_id+"','trade_date':'"+trade_date+"','name':'"+name+"','mobile':'"+mobile+"','id_card':'"+id_card+"'}";
//		String jsonString = pUtil.post("https://test.xinyan.com/operators/v2/authNormal", param);
//		System.out.println(jsonString);

//		String roatnptFractionalSegment = "302-800";
//        int roatnptFractionalSegmentSmall =Integer.parseInt(roatnptFractionalSegment.substring(0,roatnptFractionalSegment.indexOf("-")));
//        int roatnptFractionalSegmentBig =Integer.parseInt(roatnptFractionalSegment.substring(roatnptFractionalSegment.indexOf("-")+1,roatnptFractionalSegment.length()));
//System.out.println(roatnptFractionalSegmentBig);
		
//		String str = "13486070402";
//
//            // 生成一个MD5加密计算摘要
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            // 计算md5函数
//            md.update(str.getBytes("UTF-8"));
//            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
//            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
//            System.out.println(new BigInteger(1, md.digest()).toString(16)); 
		
		
//
//			
//			String newstr =null;
//			try {
//				MessageDigest md5 = MessageDigest.getInstance("MD5");
//				newstr = new String(Base64.encodeBase64(md5.digest("123456".getBytes("UTF-8"))), "utf-8");//MD5加密后对byte数组base64编码
//			} catch (NoSuchAlgorithmException e) {
//				try {
//					throw new Exception("md5加密失败，NoSuchAlgorithmException");
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//			} catch (UnsupportedEncodingException e) {
//				try {
//					throw new Exception("base64编码失败，UnsupportedEncoding");
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//			}
//
//System.out.println(newstr);
		
//		String refuseApplyProvince ="北京/上海北京/南京";
//		 String address = "上海市北京";
//		 String aS = address.substring(0,3);
//		 System.out.println(aS);
//		  String[] aString = refuseApplyProvince.split("/");
//	      for (int i = 0; i < aString.length; i++) {
//	    	  if(aS.indexOf(aString[i])!=-1) {
//System.out.println("不符合");
//	    	  }else {
//				System.out.println("符合");
//			}
//	      }
		
		
//		String aString = "1567672396000";
//		String bString = "1567672452000";
//		long a = Long.parseLong(aString);
//		long b = Long.parseLong(bString);
//		if(a>b) {
//			System.out.println("a大");
//		}else {
//			System.out.println("b大");
//		}
		
//		String aString  = "{'phoneMarket':'vivo','phoneModel':'qqq','phoneRes':'ios','phoneStand':{'lac':'gggg','loc':'bbb'},'uuid':'1111','wifiIP':'192.168.0.123','wifiMac':'35679','wifiName':'222','wrapName': [{'name':'qqq','time':'12893248668'}]}";
//		JSONObject jsonObject = JSONObject.parseObject(aString);
//		String phoneMarket = jsonObject.getString("phoneMarket");
//		String phoneModel = jsonObject.getString("phoneModel");
//		String phoneRes = jsonObject.getString("phoneRes");
//		String phoneStand = jsonObject.getString("phoneStand"); 
//		JSONObject jsonObject1 = JSONObject.parseObject(phoneStand);
//		String lac = jsonObject1.getString("lac");
//		String loc = jsonObject1.getString("loc");
//		String uuid = jsonObject.getString("uuid");
//		String wifiIP = jsonObject.getString("wifiIP");
//		String wifiMac = jsonObject.getString("wifiMac");
//		String wifiName = jsonObject.getString("wifiName");
//		String wrapName = jsonObject.getString("wrapName");
//		System.out.println("phoneMarket:"+phoneMarket);
//		System.out.println("phoneModel:"+phoneModel);
//		System.out.println("phoneRes:"+phoneRes);
//		System.out.println("phoneStand:"+phoneStand);
//		System.out.println("lac:"+lac);
//		System.out.println("loc:"+loc);
//		System.out.println("uuid:"+uuid);
//		System.out.println("wifiIP:"+wifiIP);
//		System.out.println("wifiMac:"+wifiMac);
//		System.out.println("wifiName:"+wifiName);
//		System.out.println("wrapName:"+wrapName);

//		String jsonString = "{'phoneMarket':'vivo','phoneModel':'qqq','phoneRes':'ios','phoneStand':{'lac':'gggg','loc':'bbb'},'uuid':'1111','wifiIP':'192.168.0.123','wifiMac':'35679','wifiName':'222','wrapName': [{'name':'qqq','time':'12893248668'}]}";
//		String phone = "15646541565";
//		PostAndGet pGet = new PostAndGet();
//		pGet.sendGet("http://192.168.0.102:8888/zhita_heitong_Fenkong/Anti/AddUserPhone?"+jsonString+"&"+phone);	
		
//		String username = "13487139655";
//		String password ="683037";
//		String identityName = "东新雨";
//		String identityNo = "420621199905157170";
//		String crawlerType = "Operator";
//		String appId = "8625";
//		String secret_key = "2260bc42b69e0bd65a73b2086fc4d412";
//		String string = "appId="+appId+"&crawlerType="+crawlerType+"&identityName="+identityName+"&identityNo="+identityNo+"&password="+password+"&username="+username+"&secret_key="+secret_key;
//		MD5Utils mUtils = new MD5Utils();		
//		String sign = mUtils.getMD5(string);
//		PostAndGet pGet = new PostAndGet();
////		pGet.sendGet("http://192.168.0.102:8888/zhita_heitong_Fengkong/Riskmanage/Risk_ReturnCode?phone="+"16516546546565");
//		String param = "{'username':'"+username+"','password':'"+password+"','identityName':'"+identityName+"','identityNo':'"+identityNo+"','crawlerType':'"+crawlerType+"','appId':'"+appId+"','secret_key':'"+secret_key+"','sign':'"+sign+"'}";
//		String str = pGet.sendPost("http://bbk.chao234.top/api/Gateway/index?username="+username+"&password="+password+"&identityName="+identityName+"&identityNo="+identityNo+"&crawlerType="+crawlerType+"&appId="+appId+"&secret_key="+secret_key+"&sign="+sign,"");
//		System.out.println(str);
		
		
		
		String crawlerId = "2019092017463016942306";
		String crawlerToken = "OkUQPP";
		String sms_verify_code ="044926";
		String appId = "8625";
		String secret_key = "2260bc42b69e0bd65a73b2086fc4d412";
		String crawlerType = "Operator";
		PostAndGet pGet = new PostAndGet();
	  //  String str = pGet.sendPost("http://bbk.chao234.top/api/Gateway/operate?crawlerId="+crawlerId+"&crawlerToken="+crawlerToken+"&sms_verify_code="+sms_verify_code+"&appId="+appId+"&crawlerType="+crawlerType+"&appId="+appId+"&secret_key="+secret_key+"&crawlerType="+crawlerType,"");
	    String str = pGet.sendPost("http://bbk.chao234.top/api/Gateway/operate?crawlerId="+crawlerId+"&crawlerToken="+crawlerToken+"&sms_verify_code="+sms_verify_code+"&appId="+appId+"&crawlerType="+crawlerType+"&secret_key="+secret_key,"");
		System.out.println(str);
		
		
//		String username = "13486070402";
//		String password ="656489";
//		String identityName = "陈峥";
//		String identityNo = "330225199507155112";
//		String crawlerType = "OperatorReport";
//		String appId = "8625";
//		String secret_key = "2260bc42b69e0bd65a73b2086fc4d412";
//		String string = "appId="+appId+"&crawlerType="+crawlerType+"&identityName="+identityName+"&identityNo="+identityNo+"&password="+password+"&username="+username+"&secret_key="+secret_key;
//		MD5Utils mUtils = new MD5Utils();		
//		String sign = mUtils.getMD5(string);
//		PostAndGet pGet = new PostAndGet();
////		pGet.sendGet("http://192.168.0.102:8888/zhita_heitong_Fengkong/Riskmanage/Risk_ReturnCode?phone="+"16516546546565");
//		String param = "{'username':'"+username+"','password':'"+password+"','identityName':'"+identityName+"','identityNo':'"+identityNo+"','crawlerType':'"+crawlerType+"','appId':'"+appId+"','secret_key':'"+secret_key+"','sign':'"+sign+"'}";
//		String str = pGet.sendPost("http://bbk.chao234.top/api/Gateway/index?username="+username+"&password="+password+"&identityName="+identityName+"&identityNo="+identityNo+"&crawlerType="+crawlerType+"&appId="+appId+"&secret_key="+secret_key+"&sign="+sign,"");
//		System.out.println(str);
		
		
		
//		String crawlerId = "2019092015244197455559";
//		String crawlerToken = "qrQKci";
//		String sms_verify_code ="301901";
//		String appId = "8625";
//		String secret_key = "2260bc42b69e0bd65a73b2086fc4d412";
//		String crawlerType = "OperatorReport";
//		PostAndGet pGet = new PostAndGet();
//	  //  String str = pGet.sendPost("http://bbk.chao234.top/api/Gateway/operate?crawlerId="+crawlerId+"&crawlerToken="+crawlerToken+"&sms_verify_code="+sms_verify_code+"&appId="+appId+"&crawlerType="+crawlerType+"&appId="+appId+"&secret_key="+secret_key+"&crawlerType="+crawlerType,"");
//	    String str = pGet.sendPost("http://bbk.chao234.top/api/Gateway/operate?crawlerId="+crawlerId+"&crawlerToken="+crawlerToken+"&sms_verify_code="+sms_verify_code+"&appId="+appId+"&crawlerType="+crawlerType+"&secret_key="+secret_key,"");
//		System.out.println(str);
	}

}