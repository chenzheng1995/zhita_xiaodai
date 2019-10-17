package com.zhita.controller.user;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.OrderQueryParameter;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.User;
import com.zhita.model.manage.UserLikeParameter;
import com.zhita.service.manage.user.IntUserService;
import com.zhita.util.PhoneDeal;
import com.zhita.util.Timestamps;
import com.zhita.util.TuoMinUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IntUserService intUserService;

	//后台管理----用户列表(公司id，page,姓名，手机号，注册开始时间，注册结束时间，用户认证状态，银行卡认证状态，运营商认证状态)
	@ResponseBody
	@RequestMapping("/queryUserList")
	public Map<String, Object> queryUserList(UserLikeParameter userLikeParameter){
		Map<String, Object> map=intUserService.queryUserList(userLikeParameter);
		return map;
	}
	
	//后台管理---添加黑名单
	@ResponseBody
	@RequestMapping("/insertBlacklist")
	public int insertBlacklist(Integer companyId,Integer userId,Integer operator){
		int num=intUserService.insertBlacklist(companyId, userId, operator);
		return num;
	}
	
	//后台管理---解除黑名单
	@ResponseBody
	@RequestMapping("/removeBlacklist")
	public int removeBlacklist(Integer companyId,Integer userId){
		int num=intUserService.removeBlacklist(companyId, userId);
		return num;
	}

	//后台管理----用户认证信息——借款信息（公司id，page,订单号，姓名，手机号，注册开始时间，注册结束时间     渠道id  用户id）
	@ResponseBody
	@RequestMapping("/queryAllOrdersByUserid")
  	public Map<String,Object> queryAllOrdersByUserid(OrderQueryParameter orderQueryParameter){
  		Map<String,Object> map=intUserService.queryAllOrdersByUserid(orderQueryParameter);
  		return map;
  	}
  	
	//后台管理----黑名单用户订单 查询（公司id  page，姓名，手机号，身份证号，注册开始时间，注册结束时间     渠道id）——黑名单用户  机审判定黑名单
	@ResponseBody
	@RequestMapping("/queryAllOrdersByUserid1")
  	public Map<String,Object> queryAllOrdersByUserid1(OrderQueryParameter orderQueryParameter){
  		Map<String,Object> map=intUserService.queryAllOrdersByUserid1(orderQueryParameter);
  		return map;
  	}
	
	//后台管理---用户认证信息
	@ResponseBody
	@RequestMapping("/queryUserAttesta")
	public Map<String,Object> queryUserAttesta(Integer userid){
		Map<String,Object> map=intUserService.queryUserAttesta(userid);
		return map;
	}
	
	//后台管理---用户认证信息
	@ResponseBody
	@RequestMapping("/queryauthenconcity")
	public Map<String,Object> queryauthenconcity(Integer userid,Integer page){
		Map<String,Object> map=intUserService.queryauthenconcity(userid,page);
		return map;
	}
	
	//后台管理---用户认证信息
	@ResponseBody
	@RequestMapping("/queryauthenave")
	public Map<String,Object> queryauthenave(Integer userid,Integer page){
		Map<String,Object> map=intUserService.queryauthenave(userid,page);
		return map;
	}
	
	//后台管理---用户认证信息
	@ResponseBody
	@RequestMapping("/queryauthenlabel")
	public Map<String,Object> queryauthenlabel(Integer userid,Integer page){
		Map<String,Object> map=intUserService.queryauthenlabel(userid,page);
		return map;
	}
	
	//后台管理---用户认证信息
	@ResponseBody
	@RequestMapping("/queryAllsen")
    public Map<String,Object> queryAllsen(Integer userid){
    	return intUserService.queryAllsen(userid);
    }
	
	//用户反欺诈报告命中不命中的显示
	@RequestMapping("/getModel")
	@ResponseBody
	@Transactional
	public String getModel(int userId){
       String model = intUserService.getModel(userId);
       return model;
	}
	
	/**
	 * 注册用户信息
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/exportuser.do")
	public void exportuser(UserLikeParameter userLikeParameter, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PhoneDeal pd = new PhoneDeal();//手机号加密解密工具类
		TuoMinUtil tm = new TuoMinUtil();//脱敏工具类
		if((userLikeParameter.getRegisteTimeStart()!=null&&!"".equals(userLikeParameter.getRegisteTimeStart()))&&(userLikeParameter.getRegisteTimeEnd()!=null&&!"".equals(userLikeParameter.getRegisteTimeEnd()))){
				try {
					userLikeParameter.setRegisteTimeStart(Timestamps.dateToStamp(userLikeParameter.getRegisteTimeStart()));
					userLikeParameter.setRegisteTimeEnd((Long.parseLong(Timestamps.dateToStamp(userLikeParameter.getRegisteTimeEnd()))+86400000)+"");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		if(userLikeParameter.getPhone()!=null&&!"".equals(userLikeParameter.getPhone())){
			userLikeParameter.setPhone(pd.encryption(userLikeParameter.getPhone()));
		}

		// 查询用户表的全部数据
		List<User> userlList = new ArrayList<User>(intUserService.queryUserListExcel(userLikeParameter));

		for (int i = 0; i <userlList.size(); i++) {
			userlList.get(i).setPhone(tm.mobileEncrypt(pd.decryption(userlList.get(i).getPhone())));//将手机号进行脱敏
			userlList.get(i).setRegistetime(Timestamps.stampToDate(userlList.get(i).getRegistetime()));
    		if(userlList.get(i).getUserattestationstatus()==null||"".equals(userlList.get(i).getUserattestationstatus())){
    			userlList.get(i).setUserattestationstatus("0");
    		}
    		if(userlList.get(i).getBankattestationstatus()==null||"".equals(userlList.get(i).getBankattestationstatus())){
    			userlList.get(i).setBankattestationstatus("0");
    		}
    		if(userlList.get(i).getOperaattestationstatus()==null||"".equals(userlList.get(i).getOperaattestationstatus())){
    			userlList.get(i).setOperaattestationstatus("0");
    		}
    		
    		if(userlList.get(i).getUserattestationstatus().equals("0")){
    			userlList.get(i).setUserattestationstatus("未认证");
    		}else{
    			userlList.get(i).setUserattestationstatus("已认证");
    		}
    		
    		if(userlList.get(i).getBankattestationstatus().equals("0")){
    			userlList.get(i).setBankattestationstatus("未认证");
    		}else{
    			userlList.get(i).setBankattestationstatus("已认证");
    		}
    		if(userlList.get(i).getOperaattestationstatus().equals("0")){
    			userlList.get(i).setOperaattestationstatus("未认证");
    		}else if(userlList.get(i).getOperaattestationstatus().equals("1")){
    			userlList.get(i).setOperaattestationstatus("已认证");
    		}else{
    			userlList.get(i).setOperaattestationstatus("认证中");
    		}
    		
    		if(userlList.get(i).getIfblacklist().equals("0")){
    			userlList.get(i).setIfblacklist("正常");
    		}else{
    			userlList.get(i).setIfblacklist("黑名单");
    		}
		}
		// 查询用户表有多少行记录
		Integer count = intUserService.queryUserListcount(userLikeParameter);
		// 创建excel表的表头
		String[] headers = { "注册时间", "渠道","客户端", "姓名", "手机号", "个人信息", "运营商","银行卡","状态"};
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
			cell2.setCellValue(userlList.get(i - 1).getRegistetime());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(userlList.get(i - 1).getSourcename());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getRegisteclient());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getName());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getPhone());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getUserattestationstatus());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getOperaattestationstatus());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getBankattestationstatus());
			cell2 = nextrow.createCell(8);
			cell2.setCellValue(userlList.get(i - 1).getIfblacklist());
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
	
	/**
	 * 逾期黑名单
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/exportoverdue.do")
	public void exportoverdue(OrderQueryParameter orderQueryParameter, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PhoneDeal pd = new PhoneDeal();//手机号加密解密工具类
		TuoMinUtil tm = new TuoMinUtil();//脱敏工具类
		if((orderQueryParameter.getRegistestarttime()!=null&&!"".equals(orderQueryParameter.getRegistestarttime()))&&(orderQueryParameter.getRegisteendtime()!=null&&!"".equals(orderQueryParameter.getRegisteendtime()))){
			try {
				orderQueryParameter.setRegistestarttime(Timestamps.dateToStamp(orderQueryParameter.getRegistestarttime()));
				orderQueryParameter.setRegisteendtime((Long.parseLong(Timestamps.dateToStamp(orderQueryParameter.getRegisteendtime()))+86400000)+"");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(orderQueryParameter.getPhone()!=null&&!"".equals(orderQueryParameter.getPhone())){
			orderQueryParameter.setPhone(pd.encryption(orderQueryParameter.getPhone()));
		}

		// 查询用户表的全部数据
		List<Orders> orderlList = new ArrayList<Orders>(intUserService.queryAllOrdersByUserid1Excel(orderQueryParameter));
		for (int i = 0; i < orderlList.size(); i++) {
			orderlList.get(i).getUser().setPhone(tm.mobileEncrypt(pd.decryption(orderlList.get(i).getUser().getPhone())));//将手机号进行脱敏
			orderlList.get(i).getUser().setRegistetime(Timestamps.stampToDate(orderlList.get(i).getUser().getRegistetime()));;
			orderlList.get(i).setOrderCreateTime(Timestamps.stampToDate(orderlList.get(i).getOrderCreateTime()));
		}
		
		// 查询用户表有多少行记录
		Integer count = intUserService.queryAllOrdersByUserid1count(orderQueryParameter);
		// 创建excel表的表头
		String[] headers = { "注册时间", "姓名", "手机号", "身份证号", "引流平台","客户端类型","借款次数"};
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
			cell2.setCellValue(orderlList.get(i - 1).getUser().getRegistetime());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(orderlList.get(i - 1).getUser().getName());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(orderlList.get(i - 1).getUser().getPhone());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(orderlList.get(i - 1).getUser().getIdcard());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(orderlList.get(i - 1).getUser().getSourcename());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(orderlList.get(i - 1).getUser().getRegisteclient());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(orderlList.get(i - 1).getHowManyTimesBorMoney());
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
}
