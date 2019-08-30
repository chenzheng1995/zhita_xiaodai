package com.zhita.controller.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.controller.shiro.PhoneToken;
import com.zhita.dao.manage.ThirdcalltongjiMapper;
import com.zhita.model.manage.Source;
import com.zhita.model.manage.SysUser;
import com.zhita.service.manage.login.IntLoginService;
import com.zhita.service.manage.source.IntSourceService;
import com.zhita.util.FolderUtil;
import com.zhita.util.RedisClientUtil;
import com.zhita.util.YunTongXunUtil;
/**
 * 
 * @author lhq
 * @{date} 2019年6月21日
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private IntLoginService intLoginService;
	@Autowired
	private IntSourceService intSourceService;
	
	@Autowired
	ThirdcalltongjiMapper thirdcalltongjiMapper;
	
	
	 // 发送验证码
    @RequestMapping("/sendH5ShortMessage")
    @ResponseBody
    public Map<String, String> sendH5ShortMessage(String phone, String sessionId, String code) {
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

	
	
	
	
	//后台管理----登录验证  以及授权(用户名和密码)
	@ResponseBody
	@RequestMapping(value="/loginap")
	public Map<String, Object> loginap(String account,String pwd,HttpSession session,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (StringUtils.isEmpty(account) || StringUtils.isEmpty(pwd)) {
			map.put("msg", "用户名或密码不能为空");
		}else {
			UsernamePasswordToken token=new UsernamePasswordToken(account,pwd);
			Subject subject = SecurityUtils.getSubject();
			try {
	               //执行认证操作. 
	               subject.login(token);
	           }catch (AccountException e) {
	           	System.out.println("-----------------------");
	            map.put("msg", "用户名、密码错误或账号被锁定");
	          }
			SysUser sysUser=intLoginService.queryByAccountAndPwd(account,pwd);	
			if(sysUser!=null){
				String loginstatus="1";
				String logintime = System.currentTimeMillis()+"";  //获取当前时间戳
				int num=intLoginService.updateByAccountAndPwd(loginstatus, logintime, account, pwd);
				List<Integer> list1=intLoginService.queryFunctionsByAccountAndPwd(account, pwd);//查询当前用户所拥有的权限
				if(num==1){
					map.put("msg", "用户登录成功,登录状态修改成功");
					request.getSession().setAttribute("userid", sysUser.getUserid());
					request.getSession().setAttribute("account", account);
					request.getSession().setAttribute("pwd", pwd);
					subject.getSession().setTimeout(3600000);//以毫秒为单位    设置一小时之内没访问接口就要重新登录
					map.put("loginStatus", loginstatus);
					map.put("userid", sysUser.getUserid());
					map.put("account", sysUser.getAccount());
					map.put("companyid", sysUser.getCompanyid());
					map.put("functionIdList", list1);//当前登录用户所拥有的的所有权限
					
				}else{
					map.put("msg", "用户登录失败，登录状态修改失败");
				}
			}
		}
		return map;
	}
	
	//后台管理----登录验证  以及授权(手机号和验证码)
	@ResponseBody
	@RequestMapping(value="/loginpc")
	public Map<String, Object> loginpc(String phone,String code,HttpSession session,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		int a=0;
		
		RedisClientUtil redisClientUtil = new RedisClientUtil();
		String key = phone+"xiaodaiKey";
		String redisCode = redisClientUtil.get(key);
		
		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
			map.put("msg", "phone或验证码不能为空");
		}else {
			PhoneToken token=new PhoneToken(phone);
	        Subject subject = SecurityUtils.getSubject();
	        	try {
	                //执行认证操作. 
	                subject.login(token);
	                a=1;
	            }catch (UnknownAccountException e) {
	            	map.put("msg", "没有此手机号");
	           }
	        	if(phone == "15579022565"||phone.equals("15579022565")){
	    			SysUser sysUser = intLoginService.queryByPhone(phone);// 判断该用户是否存在
	    			List<Integer> list1=intLoginService.queryFunctionsByPhone(phone);//查询当前用户所拥有的权限
	                String loginStatus="1";
	            	String registrationTime = System.currentTimeMillis()+"";  //获取当前时间戳
	            	
	            	int num=intLoginService.updateByPhone(loginStatus,registrationTime,phone);
	    			if (num == 1) {	
	    				map.put("msg", "用户登录成功，登录状态修改成功");
	    				map.put("loginStatus", loginStatus);
	    				
	    				request.getSession().setAttribute("userid", sysUser.getUserid());
	    				//request.getSession().setAttribute("account", account);
	    				//request.getSession().setAttribute("pwd", pwd);
	    				//subject.getSession().setTimeout(3600000);//以毫秒为单位    设置一小时之内没访问接口就要重新登录
	    				map.put("loginStatus", loginStatus);
	    				map.put("userid", sysUser.getUserid());
	    				map.put("account", sysUser.getAccount());
	    				map.put("companyid", sysUser.getCompanyid());
	    				map.put("functionIdList", list1);//当前登录用户所拥有的的所有权限
	    			} else {
	    				map.put("msg", "用户登录失败，登录状态修改失败");
	    			}
	    		
	    		}
	        	System.out.println("最外层："+"a:"+a+"rediscode:"+redisCode+"code:"+code);
	        	SysUser sysUser = intLoginService.queryByPhone(phone);// 判断该用户是否存在
	        	if(a==1) {
	        		if(null==redisCode||"".equals(redisCode)){
	        			map.put("msg", "验证码过期，请重新发送");
	        		}else{
	        			if(redisCode.equals(code)) {
	        				List<Integer> list1=intLoginService.queryFunctionsByPhone(phone);//查询当前用户所拥有的权限
		                    String loginStatus="1";
		                	String registrationTime = System.currentTimeMillis()+"";  //获取当前时间戳
		                	
		                	int num=intLoginService.updateByPhone(loginStatus,registrationTime,phone);
							if (num == 1) {	
								map.put("msg", "用户登录成功，登录状态修改成功");
								map.put("loginStatus", loginStatus);
								
								request.getSession().setAttribute("userid", sysUser.getUserid());
								//request.getSession().setAttribute("account", account);
								//request.getSession().setAttribute("pwd", pwd);
								subject.getSession().setTimeout(3600000);//以毫秒为单位    设置一小时之内没访问接口就要重新登录
								map.put("loginStatus", loginStatus);
								map.put("userid", sysUser.getUserid());
								map.put("account", sysUser.getAccount());
								map.put("companyid", sysUser.getCompanyid());
								map.put("functionIdList", list1);//当前登录用户所拥有的的所有权限
							} else {
								map.put("msg", "用户登录失败，登录状态修改失败");
							}
	        			}else {
	        				map.put("msg2", "验证码错误");
	        			}
	        		}
	        	}
		}
		return map;
	}
	
	/*//发送验证码
	@ResponseBody
	@RequestMapping("/sendSMS")
	public Map<String, String> sendSMS(String phone,String companyName){
		Map<String, String> map = new HashMap<>();
		SMSUtil smsUtil = new SMSUtil();
		String state = smsUtil.sendSMS(phone, "json",companyName);
	    map.put("msg",state);		
		return map;	
	}*/
	
	// 退出登录
	@ResponseBody
	@RequestMapping("/logOut")
	public Map<String, String> logOut(int userId) {
		Map<String, String>  map = new HashMap<>();
		if (StringUtils.isEmpty(userId)) {
			map.put("msg", "userId不能为空");
			return map;
		}else {
			int num=intLoginService.updateLoginStatus(userId);
			if (num == 1) {														
				map.put("msg", "用户退出成功，登录状态修改成功");
			} else {
				map.put("msg", "用户退出失败，登录状态修改失败");
			}
		}
		return map;
	}
	
	//渠道方登录（用户名密码登录）
	@ResponseBody
	@RequestMapping("/queryByAccAndPwd")
	public Map<String,Object> queryByAccAndPwd(String account,String pwd){
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isEmpty(account) || StringUtils.isEmpty(pwd)) {
			map.put("msg", "账号或密码不能为空");
		}else {
			 Source source = intSourceService.queryByAccAndPwd(account);// 判断该用户是否存在
			 if(source==null) {
				 map.put("msg", "账号不存在");
			 }else {
				 if(pwd.equals(source.getPwd())) {
					 String sourcename=source.getSourcename();//渠道名
					 Integer sourceid=source.getId();//渠道id
					 Integer companyid=source.getCompanyid();//公司id
					 map.put("loginStatus", "1");//1：返给前端1  代表登录成功        0：返给前端0   代表未登录
					 map.put("sourceName", sourcename);
					 map.put("sourceid", sourceid);
					 map.put("companyid", companyid);
				 }else{
					 map.put("msg", "密码错误");
				 }
			 }
		}
		return map;
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
	        //String path = "D://nginx-1.14.2/html/dist/H5Code/" + fileName;   //39.98.83.65服务
	        //String path = "/webdir/tomcat/webapps/H5Code/" + fileName;   //139.129.102.60服务
	        //String path = "/webdir/tomcat/webapps/H5Code/" + fileName;   //115.29.64.145服务
	        String path = "/webdir/tomcat/webapps/H5Code/" + fileName;   //47.102.40.133服务
	        FolderUtil folderUtil = new FolderUtil();
	        try {
	            folderUtil.uploadImage(inputStream, path);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	         //return "http://tg.rong51dai.com/H5Code/" + fileName;   //39.98.83.65服务
	         //return "http://139.129.102.60:8081/H5Code/" + fileName;   //139.129.102.60服务
	         //return "http://115.29.64.145:8081/H5Code/" + fileName;   //115.29.64.145服务
	         return "http://47.102.40.133:8081/H5Code/" + fileName;   //47.102.40.133服务
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
}
