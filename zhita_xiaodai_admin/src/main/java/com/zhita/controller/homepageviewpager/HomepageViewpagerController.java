package com.zhita.controller.homepageviewpager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zhita.model.manage.Company;
import com.zhita.model.manage.HomepageViewpager;
import com.zhita.service.manage.homepageviewpager.IntHomepageViewpageService;
import com.zhita.util.OssUtil;

@Controller
@RequestMapping("/homepage")
public class HomepageViewpagerController {
	@Autowired
	private IntHomepageViewpageService intHomepageViewpageService;
	
	//后台管理----查询首页轮播图所有信息
	@ResponseBody
	@RequestMapping("/queryAll")
    public List<HomepageViewpager> queryAll(Integer companyId){
    	List<HomepageViewpager> list=intHomepageViewpageService.queryAll(companyId);
    	return list;
    }
	
	//后台管理---添加功能（查询出所有公司）
	@ResponseBody
	@RequestMapping("/queryAllCompany")
    public List<Company> queryAllCompany(){
		List<Company> list=intHomepageViewpageService.queryAllCompany();
		return list;
	}
	
	//后台管理---添加功能
	@ResponseBody
	@RequestMapping("/insert")
    public int insert(HomepageViewpager record){
    	int num=intHomepageViewpageService.insert(record);
    	return num;
    }
	
	@ResponseBody
	@RequestMapping("/PictureUpload")
	public Map<String, String> PictureUpload(MultipartFile file){
    	Map<String, String> result = new HashMap<String, String>();
    	if (file != null) {// 判断上传的文件是否为空
			String path = null;// 文件路径
			String type = null;// 文件类型
			InputStream iStream = null;
			try {
				iStream = file.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String fileName = file.getOriginalFilename();// 文件原名称
			// 判断文件类型
			type = fileName.indexOf(".") != -1? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()): null;
			if (type != null) {// 判断文件类型是否为空
				if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {
					// 自定义的文件名称
					String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;
					// 设置存放图片文件的路径
					path = "homepageviewpage/" + trueFileName;
					OssUtil ossUtil = new OssUtil();
					String ossPath = "";
					try {
						ossPath = ossUtil.uploadFile(iStream, path);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(ossPath.substring(0, 5).equals("https")) {
						System.out.println("路径为："+ossPath);
						result.put("msg", "图片上传成功");
						result.put("success", ossPath);
					}else {
						result.put("msg", "图片上传失败");
					}
				} else {
					result.put("msg", "不是我们想要的文件类型,请按要求重新上传");
					return result;
				}
			} else {
				result.put("msg", "文件类型为空");
				return result;
			}
		}else {
			result.put("msg", "请上传图片");
			return result;
		}
		return result; 
    }
    
    //后台管理---编辑功能（根据id查询当前对象信息）
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public HomepageViewpager selectByPrimaryKey(Integer id){
    	HomepageViewpager homepageViewpager=intHomepageViewpageService.selectByPrimaryKey(id);
    	return homepageViewpager;
    }
    
    //后台管理---编辑功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(HomepageViewpager record){
		int num=intHomepageViewpageService.updateByPrimaryKey(record);
    	return num;
    }
	
	//后台管理---修改当前对象假删除状态
	@ResponseBody
	@RequestMapping("/updateFalDel")
    public int updateFalDel(Integer id){
    	int num=intHomepageViewpageService.updateFalDel(id);
    	return num;
    }
}
