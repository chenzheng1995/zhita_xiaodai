package com.zhita.service.manage.blacklistuser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.zhita.dao.manage.BlacklistUserMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.dao.manage.UserMapper;
import com.zhita.model.manage.BlacklistUser;
import com.zhita.model.manage.Company;
import com.zhita.util.ExcelUtils;
import com.zhita.util.ListPageUtil;
import com.zhita.util.PageUtil2;
import com.zhita.util.Timestamps;

@Service
public class BlacklistuserServiceImp implements IntBlacklistuserService{
	@Autowired
	private BlacklistUserMapper blacklistUserMapper;
	@Autowired
	private SysUserMapper syUserMapper;
	@Autowired
	private UserMapper userMapper;
	
	//后台管理---查询列表
    public Map<String, Object> queryAll(Integer page,Integer companyId,String name,String phone,String idcard,String blackType){
     	List<BlacklistUser> list=new ArrayList<>();
    	List<BlacklistUser> listto=new ArrayList<>();
    	PageUtil2 pageUtil=null;
    	list=blacklistUserMapper.queryAll(companyId, name, phone, idcard,blackType);
    	
    	
    	if(list!=null && !list.isEmpty()){
    		ListPageUtil listPageUtil=new ListPageUtil(list,page,10);
    		listto.addAll(listPageUtil.getData());
    		
    		for (int i = 0; i < listto.size(); i++) {
    			listto.get(i).setOperationtime(Timestamps.stampToDate(listto.get(i).getOperationtime()));
    		}
    		
    		pageUtil=new PageUtil2(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
    	}else{
    		pageUtil=new PageUtil2(1, 10, 0);
    	}
    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("blackuserlist", listto);
		map.put("pageutil", pageUtil);
    	return map;
    }
    
    //后台管理——添加功能（先查询出所有公司）
    public List<Company> queryAllCompany(){
    	List<Company> list=syUserMapper.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加操作
    @Transactional
    public int insert(BlacklistUser record){
    	record.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
    	record.setBlackType("3");//黑名单类型（3：手工录入）
    	int num=blacklistUserMapper.insert(record);
    	return num;
    }
    
    //后台管理---根据id查询当前对象信息
    public BlacklistUser selectByPrimaryKey(Integer id){
    	BlacklistUser blacklistUser=blacklistUserMapper.selectByPrimaryKey(id);
    	return blacklistUser;
    }
    
    //后台管理---更新保存功能
    public int updateByPrimaryKey(BlacklistUser record){
    	record.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
    	int num=blacklistUserMapper.updateByPrimaryKey(record);
    	return num;
    }
    
    //后台管理---更新假删除状态
    @Transactional
    public int upaFalseDel(Integer id,Integer userid){
    	int num=blacklistUserMapper.upaFalseDel(id);
    	userMapper.upaBlacklistStatus1(userid);
    	return num;
    }
    
    /**
     * 批量导入Excel
     */
    @Transactional
    public String ajaxUploadExcel(MultipartFile file,Integer companyId,Integer operator){
	       /* MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	        MultipartFile file = multipartRequest.getFile("excelFile");*/
	        if(file.isEmpty()){
	            try {
	                throw new Exception("文件不存在！");
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	 
	        InputStream in =null;
	        try {
	            in = file.getInputStream();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 
	        List<List<Object>> listob = null;
	        try {
	            listob = new ExcelUtils().getBankListByExcel(in,file.getOriginalFilename());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        for (int i = 0; i < listob.size(); i++) {
	            /*   List<Object> lo = listob.get(i);
	               if (lo.get(i)=="") {
	                    continue;
	                }*/
	            System.out.println(listob.get(i)+"-------");
	        }
	        for (int i = 0; i < listob.size(); i++) {
	            List<Object> lo = listob.get(i);
	            BlacklistUser vo = new BlacklistUser();
	            BlacklistUser j = null;
	 
	            try {
	            	if(lo.size()==1||lo.size()==2){
	            		j = blacklistUserMapper.queryByPhone(String.valueOf(lo.get(0)));//通过手机号查询表中是否有该黑名单用户
	            	}else{
	            		j = blacklistUserMapper.queryByPhone(String.valueOf(lo.get(1)));//通过手机号查询表中是否有该黑名单用户
	            	}
	            } catch (NumberFormatException e) {
	                // TODO Auto-generated catch block
	                System.out.println("数据库中无该条数据，新增");
	 
	            }
	            if(lo.size()==1){
	            	 vo.setCompanyid(companyId);
	            	 vo.setPhone(String.valueOf(lo.get(0)));
	            	 vo.setOperator(operator);
	 	             vo.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
	 	             vo.setBlackType("7");//黑名单类型（7：批量导入）
	            }else if(lo.size()==2){
	            	vo.setCompanyid(companyId);
	            	vo.setPhone(String.valueOf(lo.get(1)));
		            vo.setIdcard(String.valueOf(lo.get(2)));
		            vo.setOperator(operator);
		            vo.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
		            vo.setBlackType("7");//黑名单类型（7：批量导入）
	            }else{
	            	vo.setCompanyid(companyId);
		            vo.setName(String.valueOf(lo.get(0)));
		            vo.setPhone(String.valueOf(lo.get(1)));
		            vo.setIdcard(String.valueOf(lo.get(2)));
		            vo.setOperator(operator);
		            vo.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
		            vo.setBlackType("7");//黑名单类型（7：批量导入）
	            }
	            
	 
	            if(j == null)
	            {
	            	blacklistUserMapper.insert(vo);
	                System.out.println("susscess");
	            }
	            else
	            {
	            	blacklistUserMapper.updateByPhone(vo);
	            }
	        }
	 
	        return "1";
	    }

	@Override
	public int getid(String phone, int companyId) {
		int num1 = blacklistUserMapper.getid(phone,companyId);
		return num1;
	}

	@Override
	public int getid1(String idCard, int companyId) {
		int num1 = blacklistUserMapper.getid1(idCard,companyId);
		return num1;
	}

	@Override
	public void setBlacklistuser(String idCard, int userId, int companyId, String phone, String name, String date,
			String blackType) {
		blacklistUserMapper.setBlacklistuser(idCard,userId,companyId,phone,name,date,blackType);
		
	}
	
	/**
	 * 人工添加黑名单
	 * 用于导出excel的查询结果
	 */
	public void exportblack(Integer companyId,String name,String phone,String idcard,String blackType, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 查询黑名单用户表的全部数据
		List<BlacklistUser> userlList = new ArrayList<BlacklistUser>(blacklistUserMapper.queryAll(companyId, name, phone, idcard, blackType));
		for (int i = 0; i < userlList.size(); i++) {
			userlList.get(i).setOperationtime(Timestamps.stampToDate(userlList.get(i).getOperationtime()));
			if(userlList.get(i).getBlackType().equals("1")){
				userlList.get(i).setBlackType("逾期自动判定");
			}else if(userlList.get(i).getBlackType().equals("2")){
				userlList.get(i).setBlackType("重复用户");
			}else if(userlList.get(i).getBlackType().equals("3")){
				userlList.get(i).setBlackType("手工录入");
			}else if(userlList.get(i).getBlackType().equals("4")){
				userlList.get(i).setBlackType("第三方黑名单");
			}else if(userlList.get(i).getBlackType().equals("5")){
				userlList.get(i).setBlackType("三要素认证超过次数");
			}else if(userlList.get(i).getBlackType().equals("6")){
				userlList.get(i).setBlackType("人审拒绝");
			}else if(userlList.get(i).getBlackType().equals("7")){
				userlList.get(i).setBlackType("批量导入");
			}else if(userlList.get(i).getBlackType().equals("8")){
				userlList.get(i).setBlackType("人工添加");
			}else{
				userlList.get(i).setBlackType("非法渠道");
			}
		}

		// 查询用户表有多少行记录
		Integer count = userlList.size();
		// 创建excel表的表头
		String[] headers = {"姓名", "手机号", "身份证号", "类型","最后编辑时间","操作成员"};
		// 创建Excel工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表sheet
		HSSFSheet sheet = workbook.createSheet();
		// 创建第一行
		HSSFRow row = sheet.createRow(0);
		// 定义一个单元格,相当于在第一行插入了三个单元格值分别是 "姓名", "性别", "年龄"
		HSSFCell cell = null;
		// 插入第一行数据
		for (int i = 0; i < headers.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(headers[i]);
		}
		// 追加数据
		for (int i = 1; i <= count; i++) {
			HSSFRow nextrow = sheet.createRow(i);
			HSSFCell cell2 = nextrow.createCell(0);
			cell2.setCellValue(userlList.get(i - 1).getName());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(userlList.get(i - 1).getPhone());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getIdcard());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getBlackType());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getOperationtime());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getAccount());
		}
		// 将excel的数据写入文件
		ByteArrayOutputStream fos = null;
		byte[] retArr = null;
		try {
			fos = new ByteArrayOutputStream();
			workbook.write(fos);
			retArr = fos.toByteArray();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		OutputStream os = response.getOutputStream();
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=agent_book.xls");// 要保存的文件名
			response.setContentType("application/octet-stream; charset=utf-8");
			os.write(retArr);
			os.flush();
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

	@Override
	public void setBlacklistuser1(int companyId, String phone, String blackType,String registrationTime1,int userId) {
		blacklistUserMapper.setBlacklistuser1(companyId,phone,blackType,registrationTime1,userId);
		
	}


}
