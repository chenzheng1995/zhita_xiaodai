package com.zhita.service.manage.contactcustomer;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zhita.dao.manage.ContactCustomerServiceMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.Company;
import com.zhita.model.manage.ContactCustomerService;
import com.zhita.util.FolderUtil;

@Service
public class ContactcustomerServiceImp implements IntContactcustomerService{
	@Autowired
	private ContactCustomerServiceMapper contactCustomerServiceMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//后台管理---查询联系客服表所有信息
    public List<ContactCustomerService> queryAll(Integer companyId){
    	List<ContactCustomerService> list=contactCustomerServiceMapper.queryAll(companyId);
    	return list;
    }
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany(){
    	List<Company> list=sysUserMapper.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加功能
    public Map<String, Object> insert(ContactCustomerService record,MultipartFile file,MultipartFile file1) throws Exception{
		Map<String, Object>  map = new HashMap<>();
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
						record.setAdvertisingmap("http://tg.mis8888.com/image/advertisingmap/"+trueFileName);
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
		if (file1 != null) {// 判断上传的文件是否为空
			String path = null;// 文件路径
			String type = null;// 文件类型
			InputStream iStream = file1.getInputStream();
			String fileName = file1.getOriginalFilename();// 文件原名称
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
					InputStream inStream = file1.getInputStream();
					FolderUtil folderUtil = new FolderUtil();
					String code = folderUtil.uploadImage(inStream, path);
					if(code.equals("200")) {
						record.setQrcode("http://tg.mis8888.com/image/qrcode/"+trueFileName);
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
		
    	contactCustomerServiceMapper.insert(record);
    	return map;
    
    }
    
    //后台管理---根据id查询出当前对象信息
    public ContactCustomerService selectByPrimaryKey(Integer id){
    	ContactCustomerService contactCustomerService=contactCustomerServiceMapper.selectByPrimaryKey(id);
    	return contactCustomerService;
    }
    
    //后台管理---修改功能
    public Map<String, Object> updateByPrimaryKey(ContactCustomerService record,MultipartFile file,MultipartFile file1) throws Exception{
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
						record.setAdvertisingmap("http://tg.mis8888.com/image/advertisingmap/"+trueFileName);
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
			String advertisingmap = contactCustomerServiceMapper.queryAdvertisingmap(id); //通过传过来的轮播图id，查询图片的URL
			record.setAdvertisingmap(advertisingmap);
		} 
		
		if (file1.getSize()!=0) {// 判断上传的文件是否为空
			String path = null;// 文件路径
			String type = null;// 文件类型
			InputStream iStream = file1.getInputStream();
			String fileName = file1.getOriginalFilename();// 文件原名称
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
					InputStream inStream = file1.getInputStream();
					FolderUtil folderUtil = new FolderUtil();
					String code = folderUtil.uploadImage(inStream, path);
					if(code.equals("200")) {
						record.setAdvertisingmap("http://tg.mis8888.com/image/advertisingmap/"+trueFileName);
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
			String qrcode = contactCustomerServiceMapper.queryQrcode(id); //通过传过来的轮播图id，查询图片的URL
			record.setQrcode(qrcode);
		}
    	
		contactCustomerServiceMapper.updateByPrimaryKey(record);
    	return map;
    
    }
    
}
