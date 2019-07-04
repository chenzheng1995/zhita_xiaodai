package com.zhita.service.manage.autheninfor;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zhita.dao.manage.AuthenticationInformationMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.AuthenticationInformation;
import com.zhita.model.manage.Company;
import com.zhita.util.FolderUtil;

@Service
public class AutheninforServiceImp implements IntAutheninforService{
	@Autowired
	private AuthenticationInformationMapper authenticationInformationMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//后台管理---查询认证信息表所有信息
    public List<AuthenticationInformation> queryAll(Integer companyId){
    	List<AuthenticationInformation> list=authenticationInformationMapper.queryAll(companyId);
    	return list;
    }
    
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany(){
    	List<Company> list=sysUserMapper.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加功能
    public Map<String, Object> insert(AuthenticationInformation record,MultipartFile file) throws Exception{
    	Map<String, Object> map = new HashMap<>();
		if (file != null) {// 判断上传的文件是否为空
			String path = null;// 文件路径
			String type = null;// 文件类型
			InputStream iStream = file.getInputStream();
			String fileName = file.getOriginalFilename();// 文件原名称
			// 判断文件类型
			type = fileName.indexOf(".") != -1? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()): null;
			if (type != null) {// 判断文件类型是否为空
				if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {
					// 自定义的文件名称
					String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;
					// 设置存放图片文件的路径
					path = "D://image" +trueFileName;
					//path = "D://nginx-1.14.2/html/dist/image/shuffling_figure/" + /* System.getProperty("file.separator")+ */trueFileName;
//					OssUtil ossUtil = new OssUtil();
//					String ossPath = ossUtil.uploadFile(iStream, path);
//					if(ossPath.substring(0, 5).equals("https")) {
//						System.out.println("路径为："+ossPath);
//						shufflingFigure.setCover(ossPath);
//						map.put("msg", "图片上传成功");
//					}
					InputStream inStream = file.getInputStream();
					FolderUtil folderUtil = new FolderUtil();
					String code = folderUtil.uploadImage(inStream, path);
					if(code.equals("200")) {
						record.setIcon("http://tg.mis8888.com/image/autheninfor/"+trueFileName);
						map.put("msg", "图片上传成功");
					}else {
						map.put("msg", "图片上传失败");
					}
				} else {
					map.put("msg", "不是我们想要的文件类型,请按要求重新上传");
					return map;
				}
			} else {
				map.put("msg", "文件类型为空");
				return map;
			}
		}else {
			map.put("msg", "请上传图片");
			return map;
		} 
		
    	int sum=authenticationInformationMapper.insert(record);
    	return map;
    }
    
    //后台管理---根据主键id查询出当前对象信息
    public AuthenticationInformation selectByPrimaryKey(Integer id){
    	AuthenticationInformation authenticationInformation=authenticationInformationMapper.selectByPrimaryKey(id);
    	return authenticationInformation;
    }
    
    //后台管理---更新保存功能
    public Map<String, Object> updateByPrimaryKey(AuthenticationInformation record,MultipartFile file) throws Exception{
		Map<String, Object> map = new HashMap<>();
		if (file.getSize()!=0) {// 判断上传的文件是否为空
			String path = null;// 文件路径
			String type = null;// 文件类型
			InputStream iStream = file.getInputStream();
			String fileName = file.getOriginalFilename();// 文件原名称
			// 判断文件类型
			type = fileName.indexOf(".") != -1? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()): null;
			if (type != null) {// 判断文件类型是否为空
				if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {
					// 自定义的文件名称
					String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;
					// 设置存放图片文件的路径
					path = "D://image" +trueFileName;
					//path = "D://nginx-1.14.2/html/dist/image/shuffling_figure/" + /* System.getProperty("file.separator")+ */trueFileName;
//					OssUtil ossUtil = new OssUtil();
//					String ossPath = ossUtil.uploadFile(iStream, path);
//					if(ossPath.substring(0, 5).equals("https")) {
//						System.out.println("路径为："+ossPath);
//						shufflingFigure.setCover(ossPath);
//						map.put("msg", "图片上传成功");
//					}
					InputStream inStream = file.getInputStream();
					FolderUtil folderUtil = new FolderUtil();
					String code = folderUtil.uploadImage(inStream, path);
					if(code.equals("200")) {
						record.setIcon("http://tg.mis8888.com/image/autheninfor/"+trueFileName);
						map.put("msg", "图片上传成功");
					}else {
						map.put("msg", "图片上传失败");
					}

				} else {
					map.put("msg", "不是我们想要的文件类型,请按要求重新上传");
					return map;
				}
			} else {
				map.put("msg", "文件类型为空");
				return map;
			}
		}else {
			int id = record.getId();
			String icon=authenticationInformationMapper.queryIcon(id);//通过传过来的认证信息表id，查询图标字段
			record.setIcon(icon);
		} 
    	
    	int sum=authenticationInformationMapper.updateByPrimaryKey(record);
    	return map;
    }
}
