package com.zhita.service.manage.aboutus;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zhita.dao.manage.AboutusMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.Aboutus;
import com.zhita.model.manage.Company;
import com.zhita.util.OssUtil;

@Service
public class AboutusServiceImp implements IntAboutusService{
	@Autowired
	private AboutusMapper aboutusMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//后台管理---查询所有
    public List<Aboutus> queryAll(Integer companyId){
    	List<Aboutus> list=aboutusMapper.queryAll(companyId);
    	return list;
    }
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany(){
    	List<Company> list=sysUserMapper.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加功能
    public Map<String, Object> insert(Aboutus record,MultipartFile file) throws Exception{
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
					//path = "D://image" +trueFileName;
					path = "D://nginx-1.14.2/html/dist/image/aboutus/" + /* System.getProperty("file.separator")+ */trueFileName;
					OssUtil ossUtil = new OssUtil();
					String ossPath = ossUtil.uploadFile(iStream, path);
					if(ossPath.substring(0, 5).equals("https")) {
						System.out.println("路径为："+ossPath);
						record.setLogo(ossPath);
						map.put("msg", "图片上传成功");
					}
					/*InputStream inStream = file.getInputStream();
					FolderUtil folderUtil = new FolderUtil();
					String code = folderUtil.uploadImage(inStream, path);
					if(code.equals("200")) {
						record.setLogo("http://tg.mis8888.com/image/aboutus/"+trueFileName);
						map.put("msg", "图片上传成功");
					}*/else {
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
		aboutusMapper.insert(record);
		return map;
    }
    
    //后台管理---根据id查询当前对象信息
    public Aboutus selectByPrimaryKey(Integer id){
    	Aboutus aboutus=aboutusMapper.selectByPrimaryKey(id);
    	return aboutus;
    }
    
    //后台管理---更新功能
    public int updateByPrimaryKey(Aboutus record){
		int num=aboutusMapper.updateByPrimaryKey(record);
		return num;
    }

	@Override
	public Map<String, Object> getaboutus(int companyId) {
		Map<String, Object> map =aboutusMapper.getaboutus(companyId);
		return map;
	}
    
}
