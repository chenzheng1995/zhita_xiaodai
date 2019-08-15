package com.zhita.util;

import java.io.IOException;

public class Test {
	

	public static void main(String[] args) throws IOException {
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
	/*	
		String aString = "香港/北京/上海/重庆";
		String[] bStrings = aString.split("/");
        for (int i = 0; i < bStrings.length; i++) {
            System.out.println(bStrings[i]);
        }*/
    	
		System.out.println(MD5Utils.getMD555555("123abc"));
	}
	}

