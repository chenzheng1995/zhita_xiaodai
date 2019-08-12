package com.zhita.controller.promote;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.dao.manage.CompanyMapper;
import com.zhita.model.manage.Source;
import com.zhita.service.manage.source.IntSourceService;
import com.zhita.util.FolderUtil;
import com.zhita.util.RedisClientUtil;

@Controller
@RequestMapping("/promote")
public class PromoteController {
	
	@Autowired
	IntSourceService intSourceService;
	
	@Autowired
	CompanyMapper companyMapper;
	
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
		 map.put("msg","渠道被删除");
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
        String path = "D://nginx-1.14.2/html/dist/H5Code/" + fileName;
        FolderUtil folderUtil = new FolderUtil();
        try {
            folderUtil.uploadImage(inputStream, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://tg.rong51dai.com/H5Code/" + fileName;
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
