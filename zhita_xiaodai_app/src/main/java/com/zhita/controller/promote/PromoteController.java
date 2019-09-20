package com.zhita.controller.promote;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.dao.manage.BorrowMoneyMessageMapper;
import com.zhita.dao.manage.CompanyMapper;
import com.zhita.dao.manage.ThirdcalltongjiMapper;
import com.zhita.model.manage.Source;
import com.zhita.service.manage.blacklistuser.IntBlacklistuserService;
import com.zhita.service.manage.loanthresholdvalue.IntLoanthresholdvalueService;
import com.zhita.service.manage.login.IntLoginService;
import com.zhita.service.manage.order.IntOrderService;
import com.zhita.service.manage.source.IntSourceService;
import com.zhita.service.manage.thirdpartyint.IntThirdpartyintService;
import com.zhita.service.manage.usthresholdvalue.IntUsthresholdvalueService;
import com.zhita.util.FolderUtil;
import com.zhita.util.PhoneDeal;
import com.zhita.util.RedisClientUtil;
import com.zhita.util.SMSUtil;
import com.zhita.util.Timestamps;
import com.zhita.util.YunTongXunUtil;

@Controller
@RequestMapping("/promote")
public class PromoteController {
	
	@Autowired
	IntSourceService intSourceService;
	
	@Autowired
	CompanyMapper companyMapper;
	
	@Autowired
	IntLoginService loginService;
	
	@Autowired
	IntThirdpartyintService intThirdpartyintService;

	@Autowired
	IntBlacklistuserService intBlacklistuserService;
	
	@Autowired
	IntOrderService intOrderService;
	
	@Autowired
	IntUsthresholdvalueService intUsthresholdvalueService;
	
	@Autowired
	ThirdcalltongjiMapper thirdcalltongjiMapper;
	
	@Autowired
	BorrowMoneyMessageMapper borrowMoneyMessageMapper;
	
	//判断这个渠道有没有删除或禁用，如果删除或禁用了就不让用户显示推广页
	@RequestMapping("/isPromotion")
	@ResponseBody
	@Transactional
	public Map<String, Object> ispromotion (int companyId,String sourceName) {
		Map<String, Object> map = new HashMap<>();
		 map.put("msg","成功");
		 map.put("code","200");
		List<String> list = intSourceService.getstateAndDeleted(companyId,sourceName);
	    for (String string : list) {
	     String	state = string;
		 if("2".equals(state)) {
			 map.put("msg","渠道被禁用");
			 map.put("code","300");
			 return map;
		 }
		}
		 map.put("msg","对不起，您的连接有误");
		 map.put("code","300");
	    List<String> list1 = intSourceService.getDeleted(companyId,sourceName);
	    for (String string : list1) {
		     String	deleted = string;
			 if("0".equals(deleted)) {  
				 map.put("msg","成功");
				 map.put("code","200");
				 return map;
			 }
			}

		return map;
		
	}

	@RequestMapping("/getSourceClick")
	@ResponseBody
	@Transactional
	public Map<String, Object> getqrcode(String company,String source,String date) {

		Map<String, Object> map = new HashMap<>();
    	RedisClientUtil redisClientUtil = new RedisClientUtil();
    	String SourceClick = redisClientUtil.get(company+source+date+"xiaodaiKey");
    	if(SourceClick==null) {
    		redisClientUtil.set(company+source+date+"xiaodaiKey","1");
    		System.out.println(redisClientUtil.getSourceClick(company+source+date+"xiaodaiKey"));
    		map.put("code","200");
    	}else {
    		redisClientUtil.set(company+source+date+"xiaodaiKey",Integer.parseInt(redisClientUtil.getSourceClick(company+source+date+"xiaodaiKey"))+1+""); //由于value是string类型的，所以先转换成int类型，+1之后在转换成string类型
    		System.out.println(redisClientUtil.getSourceClick(company+source+date+"xiaodaiKey"));
    		map.put("code","200");
		}
    	int companyId = companyMapper.getCompanyId(company);
    	int sourceId = intSourceService.queryByLike1(source,companyId);

    	map.put("sourceId", sourceId);
    	return map;
	
	}
	
	
    // 发送验证码
    @RequestMapping("/sendH5ShortMessage")
    @ResponseBody
    public Map<String, String> sendH5ShortMessage(String phone, String company, String sessionId, String code) {
        Map<String, String> map = new HashMap<>();
        RedisClientUtil redis = new RedisClientUtil();
        String serviceCode = redis.get(sessionId);
        if (StringUtils.isEmpty(serviceCode)) {
            map.put("msg", "会话过期请刷新页面");
        } else if (!serviceCode.equals(code)) {
            map.put("msg", "验证码错误");
        } else {
            YunTongXunUtil yunTongXunUtil = new YunTongXunUtil();
            String state = yunTongXunUtil.sendSMS(phone);
            if("提交成功".equals(state)) {
            	int companyId =3;
    			String thirdtypeid = "1";
    			String date = System.currentTimeMillis()+"";
    			thirdcalltongjiMapper.setthirdcalltongji(companyId,thirdtypeid,date);
    			}
            map.put("msg", state);
            return map;
        }
        return map;
    }
	
	
    @RequestMapping("/initializationH5")
    @ResponseBody
    public Map initializationH5() {
        Map<String, String> codeMap = applyCodeImage();
        Map<String, String> resultMap = new HashMap<>();
        String sessionId = java.util.UUID.randomUUID().toString();
        RedisClientUtil.set(sessionId, codeMap.get("photoCode"), 3600);
        resultMap.put("sessionId", sessionId);
        resultMap.put("photoUrl", codeMap.get("photoUrl"));
        return resultMap;
    }
	
    @RequestMapping("/getH5Code")
    @ResponseBody
    public String getH5Code(String sessionId) {
        Map<String, String> codeMap = applyCodeImage();
        RedisClientUtil.set(sessionId, codeMap.get("photoCode"), 3600);
        return codeMap.get("photoUrl");
    }

    private Map<String, String> applyCodeImage() {
        int width = 90;// 定义图片的width
        int height = 20;// 定义图片的height
        int codeCount = 4;// 定义图片上显示验证码的个数
        int xx = 15;
        int fontHeight = 18;
        int codeY = 16;
        Map<String, String> result = new HashMap<>();
        char[] codeSequence = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics gd = buffImg.getGraphics();
        //随机操作对象
        Random random = new Random();
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);
        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
        // 设置字体。
        gd.setFont(font);
        // 画边框。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);
        // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
       /* gd.setColor(Color.BLACK);
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }*/
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;
        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String code = String.valueOf(codeSequence[random.nextInt(10)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red, green, blue));
            gd.drawString(code, (i + 1) * xx, codeY);
            // 将产生的四个随机数组合在一起。
            randomCode.append(code);
        }
        /*Map<String, Object> map = new HashMap<String, Object>();
        //存放验证码
        map.put("code", randomCode);
        //存放生成的验证码BufferedImage对象
        map.put("codePic", buffImg);
        return map;*/
        result.put("photoUrl",uploadImage(bufferedImageToInputStream(buffImg)));
        result.put("photoCode", randomCode.toString());
        return result;
    }


    private String uploadImage(InputStream inputStream) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = format.format(new Date()) + UUID.randomUUID().toString() + ".JPG";// 文件原名称
        // 判断文件类型
//        String path = "D://nginx-1.14.2/html/dist/H5Code/" + fileName;
        String path = "/webdir/tomcat/webapps/H5Code/" + fileName;
        FolderUtil folderUtil = new FolderUtil();
        try {
            folderUtil.uploadImage(inputStream, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return "http://tg.rong51dai.com/H5Code/" + fileName;
        return "http://139.129.102.60:8081/H5Code/" + fileName;
    }
    
    private int getRandomCode() {
        return (int) ((Math.random() * 9 + 1) * 1000);
    }

    public InputStream bufferedImageToInputStream(BufferedImage image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            return input;
        } catch (IOException e) {
            System.out.println("图片流转换异常");
            e.getStackTrace();
        }
        return null;
    }
    
    
    
 // 验证码登陆

// 	/**
// 	 * @param phone         手机号
// 	 * @param code          验证码
// 	 * @param company       公司名
// 	 * @param registeClient 软件类型
// 	 * @return
// 	 */
 	@RequestMapping("/codelogin")
 	@ResponseBody
 	@Transactional
 	public Map<String, Object> codeLogin(String phone, String code, int companyId, String registeClient,
 			String sourceName, String useMarket,String userAgentInfo) {
 		Map<String, Object> map = new HashMap<String, Object>();
 		String loginStatus = "1";
 		PhoneDeal phoneDeal = new PhoneDeal();
 		String newPhone = phoneDeal.encryption(phone);
 		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code) || StringUtils.isEmpty(companyId)
 				|| StringUtils.isEmpty(registeClient) || StringUtils.isEmpty(sourceName)
 				|| StringUtils.isEmpty(useMarket)) {
 			map.put("msg", "phone,code,companyId,registrationType,sourceName和useMarket不能为空");
 			return map;
 		} else {
			
//         	String ifBlacklist =loginService.getifBlacklist(newPhone,companyId);
 			Integer id = loginService.findphone(newPhone, companyId); // 判断该用户是否存在
 			int num1 = intBlacklistuserService.getid(phone, companyId);
 			if (num1 == 1 || num1 > 1) {
 				String ifBlacklist = loginService.getifBlacklist(newPhone, companyId);
 				if(ifBlacklist==null) {
						map.put("msg", "手机号黑名单 ");
						map.put("SCode", "407");
						map.put("prompt", "您暂时不符合借款要求，请三个月之后再来尝试");
						return map;
 				}else {
 	 				if ("0".equals(ifBlacklist)) {
 	 					loginService.updateifBlacklist1(newPhone, companyId);
 	 				} else {
 	 					int userId = loginService.getId(newPhone, companyId);
 	 					int orderStatus = intOrderService.getorderStatus(userId, companyId);
 	 					if (orderStatus != 1) {
 	 						map.put("msg", "手机号黑名单 ");
 	 						map.put("SCode", "407");
 	 						map.put("prompt", "您暂时不符合借款要求，请三个月之后再来尝试");
 	 						return map;
 	 					}
 	 				}
				}

 			} else {

// 			int num1 = sourceDadSonService.getSourceDadSon(sourceId,sonSourceName,company);
// 			if (num1 == 0) {
// 			sourceDadSonService.setSourceDadSon(sourceId,sonSourceName,company);
// 			}

 				RedisClientUtil redisClientUtil = new RedisClientUtil();
 				String key = phone + "xiaodaiKey";
 				String redisCode = redisClientUtil.get(key);
 				if (redisCode == null) {
 					map.put("msg", "验证码已过期，请重新发送");
 					map.put("SCode", "402");
 					return map;
 				}
 				if (redisCode.equals(code)) {
 					redisClientUtil.delkey(key);// 验证码正确就从redis里删除这个key
 					String registrationTime = System.currentTimeMillis() + ""; // 获取当前时间戳
 					if (id == null) {
 			 			Timestamps timestamps = new Timestamps();
 			 			long todayZeroTimestamps = timestamps.getTodayZeroTimestamps(); //今天0点的时间戳
 			 			long tomorrowZeroTimestamps = todayZeroTimestamps+86400000; //明天0点的时间戳
 			 			long number1 = loginService.getnumber(todayZeroTimestamps,tomorrowZeroTimestamps,companyId);//当天的注册数
 			 			int maxthresholdvalue = intUsthresholdvalueService.getmaxthresholdvalue(companyId);//最大可以注册的数量
 			 			if(number1>maxthresholdvalue) {
 								map.put("msg", "注册数达到上限 ");
 								map.put("SCode", "409");
 								return map;
 			 			}
 						String operatorsAuthentication = intThirdpartyintService.getOperatorsAuthentication(companyId);
 						int merchantId = intSourceService.getsourceId(sourceName);
 						BigDecimal canBorrowlines = borrowMoneyMessageMapper.getCanBorrowlines(companyId);
 						int number = loginService.insertUser2(newPhone, loginStatus, companyId, registeClient,
 								registrationTime, merchantId, useMarket, operatorsAuthentication,userAgentInfo,canBorrowlines);
 						if (number == 1) {
 							id = loginService.getId(newPhone, companyId); // 获取该用户的id
 							map.put("msg", "用户登录成功，数据插入成功，让用户添加密码");
 							map.put("SCode", "201");
 							map.put("loginStatus", loginStatus);
 							map.put("userId", id);
 							map.put("phone", phone);
 						} else {
 							map.put("msg", "用户登录失败，用户数据插入失败");
 							map.put("SCode", "405");
 						}
 					}
// 					} else {
// 						String loginTime = System.currentTimeMillis() + "";
// 						int num = loginService.updateStatus(loginStatus, newPhone, companyId, loginTime);
// 						if (num == 1) {
// 							id = loginService.getId(newPhone, companyId); // 获取该用户的id
// 							String pwd = loginService.getPwd(id);
// 							if (pwd == null) {
// 								map.put("msg", "用户登录成功，登录状态修改成功，让用户添加密码");
// 								map.put("SCode", "201");
// 								map.put("loginStatus", loginStatus);
// 								map.put("userId", id);
// 								map.put("phone", phone);
// 							} else {
// 								map.put("msg", "用户登录成功，登录状态修改成功");
// 								map.put("SCode", "200");
// 								map.put("loginStatus", loginStatus);
// 								map.put("userId", id);
// 								map.put("phone", phone);
// 							}
//						} else {
// 							map.put("msg", "用户登录失败，登录状态修改失败");
// 							map.put("SCode", "406");
// 						}
// 					}
 				} else {
 					map.put("msg", "验证码错误");
 					map.put("SCode", "403");
 					return map;
				}

 			}
 			return map;
 		}

 	}
    
    
// 	/**
// 	 * @param phone         手机号
// 	 * @param code          验证码
// 	 * @param company       公司名
// 	 * @param registeClient 软件类型
// 	 * @return
// 	 */
// 	@RequestMapping("/codelogin")
// 	@ResponseBody
// 	@Transactional
// 	public Map<String, Object> codeLogin(String phone, int companyId, String registeClient,
// 			String sourceName, String useMarket,String userAgentInfo) {
// 		Map<String, Object> map = new HashMap<String, Object>();
// 		String loginStatus = "1";
// 		PhoneDeal phoneDeal = new PhoneDeal();
// 		String newPhone = phoneDeal.encryption(phone);
// 		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(companyId)
// 				|| StringUtils.isEmpty(registeClient) || StringUtils.isEmpty(sourceName)
// 				|| StringUtils.isEmpty(useMarket)) {
// 			map.put("msg", "phone,code,companyId,registrationType,sourceName和useMarket不能为空");
// 			return map;
// 		} else {
//			
////         	String ifBlacklist =loginService.getifBlacklist(newPhone,companyId);
// 			Integer id = loginService.findphone(newPhone, companyId); // 判断该用户是否存在
// 			int num1 = intBlacklistuserService.getid(phone, companyId);
// 			if (num1 == 1 || num1 > 1) {
// 				String ifBlacklist = loginService.getifBlacklist(newPhone, companyId);
// 				if(ifBlacklist==null) {
//						map.put("msg", "手机号黑名单 ");
//						map.put("SCode", "407");
//						map.put("prompt", "您暂时不符合借款要求，请三个月之后再来尝试");
//						return map;
// 				}else {
// 	 				if ("0".equals(ifBlacklist)) {
// 	 					loginService.updateifBlacklist1(newPhone, companyId);
// 	 				} else {
// 	 					int userId = loginService.getId(newPhone, companyId);
// 	 					int orderStatus = intOrderService.getorderStatus(userId, companyId);
// 	 					if (orderStatus != 1) {
// 	 						map.put("msg", "手机号黑名单 ");
// 	 						map.put("SCode", "407");
// 	 						map.put("prompt", "您暂时不符合借款要求，请三个月之后再来尝试");
// 	 						return map;
// 	 					}
// 	 				}
//				}
//
// 			} else {
//
//// 			int num1 = sourceDadSonService.getSourceDadSon(sourceId,sonSourceName,company);
//// 			if (num1 == 0) {
//// 			sourceDadSonService.setSourceDadSon(sourceId,sonSourceName,company);
//// 			}
//
// 					String registrationTime = System.currentTimeMillis() + ""; // 获取当前时间戳
// 					if (id == null) {
// 			 			Timestamps timestamps = new Timestamps();
// 			 			long todayZeroTimestamps = timestamps.getTodayZeroTimestamps(); //今天0点的时间戳
// 			 			long tomorrowZeroTimestamps = todayZeroTimestamps+86400000; //明天0点的时间戳
// 			 			long number1 = loginService.getnumber(todayZeroTimestamps,tomorrowZeroTimestamps,companyId);//当天的注册数
// 			 			int maxthresholdvalue = intUsthresholdvalueService.getmaxthresholdvalue(companyId);//最大可以注册的数量
// 			 			if(number1>maxthresholdvalue) {
// 								map.put("msg", "注册数达到上限 ");
// 								map.put("SCode", "409");
// 								return map;
// 			 			}
// 						String operatorsAuthentication = intThirdpartyintService.getOperatorsAuthentication(companyId);
// 						int merchantId = intSourceService.getsourceId(sourceName);
// 						int number = loginService.insertUser2(newPhone, loginStatus, companyId, registeClient,
// 								registrationTime, merchantId, useMarket, operatorsAuthentication,userAgentInfo);
// 						if (number == 1) {
// 							id = loginService.getId(newPhone, companyId); // 获取该用户的id
// 							map.put("msg", "用户登录成功，数据插入成功，让用户添加密码");
// 							map.put("SCode", "201");
// 							map.put("loginStatus", loginStatus);
// 							map.put("userId", id);
// 							map.put("phone", phone);
// 						} else {
// 							map.put("msg", "用户登录失败，用户数据插入失败");
// 							map.put("SCode", "405");
// 						}
// 					}
//// 					} else {
//// 						String loginTime = System.currentTimeMillis() + "";
//// 						int num = loginService.updateStatus(loginStatus, newPhone, companyId, loginTime);
//// 						if (num == 1) {
//// 							id = loginService.getId(newPhone, companyId); // 获取该用户的id
//// 							String pwd = loginService.getPwd(id);
//// 							if (pwd == null) {
//// 								map.put("msg", "用户登录成功，登录状态修改成功，让用户添加密码");
//// 								map.put("SCode", "201");
//// 								map.put("loginStatus", loginStatus);
//// 								map.put("userId", id);
//// 								map.put("phone", phone);
//// 							} else {
//// 								map.put("msg", "用户登录成功，登录状态修改成功");
//// 								map.put("SCode", "200");
//// 								map.put("loginStatus", loginStatus);
//// 								map.put("userId", id);
//// 								map.put("phone", phone);
//// 							}
//// 						} else {
//// 							map.put("msg", "用户登录失败，登录状态修改失败");
//// 							map.put("SCode", "406");
//// 						}
//// 					}
// 				
// 			}
// 			return map;
// 		}
//
// 	}
	
}
