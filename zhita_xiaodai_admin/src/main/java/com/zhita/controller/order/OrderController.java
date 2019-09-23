package com.zhita.controller.order;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.zhita.model.manage.DeferredAndOrder;
import com.zhita.model.manage.Offlinedelay;
import com.zhita.model.manage.OrderQueryParameter;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.User;
import com.zhita.model.manage.UserLikeParameter;
import com.zhita.service.manage.order.IntOrderService;
import com.zhita.service.manage.user.IntUserService;
import com.zhita.util.PhoneDeal;
import com.zhita.util.Timestamps;
import com.zhita.util.TuoMinUtil;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private IntOrderService intOrderService;
	@Autowired
	private IntUserService intUserService;

	/*
	 * //后台管理----机审订单 (公司id，page，订单号，姓名，手机号，订单开始时间，订单结束时间，风控反馈)
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping("/queryatrOrders") public Map<String, Object>
	 * queryatrOrders(OrderQueryParameter orderQueryParameter){ Map<String,
	 * Object> map=intOrderService.queryatrOrders(orderQueryParameter); return
	 * map; }
	 * 
	 * //后台管理----机审拒绝未人审订单 (公司id，page，订单号，姓名，手机号，订单开始时间，订单结束时间)
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping("/queryroaOrders") public Map<String, Object>
	 * queryroaOrders(OrderQueryParameter orderQueryParameter){ Map<String,
	 * Object> map=intOrderService.queryroaOrders(orderQueryParameter); return
	 * map; }
	 * 
	 * //后台管理----机审拒绝未人审订单——审核通过操作(assessor是当前登录用户的id)
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping("/upaOrderIfpeopleWhose") public int
	 * upaOrderIfpeopleWhose(Integer orderid,Integer assessor){ int
	 * num=intOrderService.upaOrderIfpeopleWhose(orderid, assessor); return num;
	 * }
	 * 
	 * //后台管理----已机审已人审（公司id，page,订单号，姓名，手机号，订单开始时间，订单结束时间 审核员id）
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping("/queryroasOrders") public Map<String, Object>
	 * queryroasOrders(OrderQueryParameter orderQueryParameter){ Map<String,
	 * Object> map=intOrderService.queryroasOrders(orderQueryParameter); return
	 * map; }
	 * 
	 * //后台管理----订单 查询（公司id，page,订单号，姓名，手机号，注册开始时间，注册结束时间 渠道id）
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping("/queryAllOrders") public Map<String, Object>
	 * queryAllOrders(OrderQueryParameter orderQueryParameter){ Map<String,
	 * Object> map=intOrderService.queryAllOrders(orderQueryParameter); return
	 * map; }
	 */

	/**
	 * 订单查询【机审通过和人审通过的用户
	 * 在放款后在订单表产生的订单数据】（公司id，page,订单号，姓名，手机号，注册开始时间，注册结束时间，渠道id）
	 */
	@ResponseBody
	@RequestMapping("/queryAllordersByLike")
	public Map<String, Object> queryAllordersByLike(OrderQueryParameter orderQueryParameter) {
		Map<String, Object> map = intOrderService.queryAllordersByLike(orderQueryParameter);
		return map;
	}

	/**
	 * 机审状态用户【包含机审拒绝和机审通过用户】（公司id，page,申请编号，姓名，手机号，申请时间开始，申请时间结束）
	 */
	@ResponseBody
	@RequestMapping("/queryAllUser")
	public Map<String, Object> queryAllUser(UserLikeParameter userLikeParameter) {
		Map<String, Object> map = intOrderService.queryAllUser(userLikeParameter);
		return map;
	}

	/**
	 * 人审状态用户（公司id，page,申请编号，姓名，手机号，申请时间开始，申请时间结束）
	 */
	@ResponseBody
	@RequestMapping("/queryAllUserPeople")
	public Map<String, Object> queryAllUserPeople(UserLikeParameter userLikeParameter) {
		Map<String, Object> map = intOrderService.queryAllUserPeople(userLikeParameter);
		return map;
	}

	/**
	 * 人审通过按钮
	 */
	@ResponseBody
	@RequestMapping("/updateShareOfState")
	public int updateShareOfState(Integer sysuserid, Integer userid) {
		int num = intOrderService.updateShareOfState(sysuserid, userid);
		return num;
	}

	/**
	 * 人审不通过按钮
	 */
	@ResponseBody
	@RequestMapping("/updateShareOfStateNo")
	public int updateShareOfStateNo(Integer companyId, Integer sysuserid, Integer userid) {
		int num = intOrderService.updateShareOfStateNo(sysuserid, userid);
		intUserService.insertBlacklistno(companyId, userid, sysuserid);// 添加黑名单
		return num;
	}

	/**
	 * 人审过后状态用户【包括人审不通过和人审通过】（公司id，page,申请编号，姓名，手机号，申请时间开始，申请时间结束，审核员id）
	 */
	@ResponseBody
	@RequestMapping("queryAllUserPeopleYet")
	public Map<String, Object> queryAllUserPeopleYet(UserLikeParameter userLikeParameter) {
		Map<String, Object> map = intOrderService.queryAllUserPeopleYet(userLikeParameter);
		return map;
	}

	/**
	 * 用户认证信息
	 * 
	 * @param userid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryUserAttesta")
	public Map<String, Object> queryUserAttesta(Integer userid) {
		Map<String, Object> map = intUserService.queryUserAttesta(userid);
		return map;
	}

	/**
	 * 机审状态用户 
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/export.do")
	public void export(UserLikeParameter userLikeParameter, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PhoneDeal pd = new PhoneDeal();// 手机号加密解密工具类
		TuoMinUtil tm = new TuoMinUtil();// 脱敏工具类
		if ((userLikeParameter.getApplytimestart() != null && !"".equals(userLikeParameter.getApplytimestart()))
				&& (userLikeParameter.getApplytimeend() != null && !"".equals(userLikeParameter.getApplytimeend()))) {
			try {
				userLikeParameter.setApplytimestart(Timestamps.dateToStamp(userLikeParameter.getApplytimestart()));
				userLikeParameter.setApplytimeend(
						(Long.parseLong(Timestamps.dateToStamp(userLikeParameter.getApplytimeend())) + 86400000) + "");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (userLikeParameter.getPhone() != null && !"".equals(userLikeParameter.getPhone())) {
			userLikeParameter.setPhone(pd.encryption(userLikeParameter.getPhone()));
		}

		// 查询用户表的全部数据
		List<User> userlList = new ArrayList<User>(intOrderService.queryAllUserExcel(userLikeParameter));

		for (int i = 0; i < userlList.size(); i++) {
			userlList.get(i).setPhone(tm.mobileEncrypt(pd.decryption(userlList.get(i).getPhone())));// 将手机号进行脱敏
			userlList.get(i).setApplytime(Timestamps.stampToDate(userlList.get(i).getApplytime()));
			if (userlList.get(i).getState().equals("0")) {
				userlList.get(i).setState("有效");
			} else {
				userlList.get(i).setState("无效");
			}
			if (userlList.get(i).getShareOfState().equals("0")) {
				userlList.get(i).setShareOfState("机审未通过");
			} else {
				userlList.get(i).setShareOfState("机审通过");
			}
		}
		// 查询用户表有多少行记录
		Integer count = intOrderService.queryAllUsercount(userLikeParameter);
		// 创建excel表的表头
		String[] headers = { "申请编号", "申请时间", "姓名", "手机号", "状态", "渠道", "客户端", "风控", "风控分", "机审结果" };
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
			cell2.setCellValue(userlList.get(i - 1).getApplynumber());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(userlList.get(i - 1).getApplytime());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getName());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getPhone());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getState());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getSourcename());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getRegisteclient());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getRmModleName());
			cell2 = nextrow.createCell(8);
			cell2.setCellValue(userlList.get(i - 1).getRiskControlPoints());
			cell2 = nextrow.createCell(9);
			cell2.setCellValue(userlList.get(i - 1).getShareOfState());
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
	 * 待人审状态用户 
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/exportpeople.do")
	public void exportpeople(UserLikeParameter userLikeParameter, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PhoneDeal pd = new PhoneDeal();// 手机号加密解密工具类
		TuoMinUtil tm = new TuoMinUtil();// 脱敏工具类
		if ((userLikeParameter.getApplytimestart() != null && !"".equals(userLikeParameter.getApplytimestart()))
				&& (userLikeParameter.getApplytimeend() != null && !"".equals(userLikeParameter.getApplytimeend()))) {
			try {
				userLikeParameter.setApplytimestart(Timestamps.dateToStamp(userLikeParameter.getApplytimestart()));
				userLikeParameter.setApplytimeend(
						(Long.parseLong(Timestamps.dateToStamp(userLikeParameter.getApplytimeend())) + 86400000) + "");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (userLikeParameter.getPhone() != null && !"".equals(userLikeParameter.getPhone())) {
			userLikeParameter.setPhone(pd.encryption(userLikeParameter.getPhone()));
		}

		// 查询用户表的全部数据
		List<User> userlList = new ArrayList<User>(intOrderService.queryAllUserPeopleExcel(userLikeParameter));

		for (int i = 0; i < userlList.size(); i++) {
			userlList.get(i).setPhone(tm.mobileEncrypt(pd.decryption(userlList.get(i).getPhone())));// 将手机号进行脱敏
			userlList.get(i).setApplytime(Timestamps.stampToDate(userlList.get(i).getApplytime()));
			if (userlList.get(i).getState().equals("0")) {
				userlList.get(i).setState("有效");
			} else {
				userlList.get(i).setState("无效");
			}
			if (userlList.get(i).getShareOfState().equals("1")) {
				userlList.get(i).setShareOfState("需要人工审核");
			}
		}
		// 查询用户表有多少行记录
		Integer count = intOrderService.queryAllUserPeoplecount(userLikeParameter);
		// 创建excel表的表头
		String[] headers = { "申请编号", "申请时间", "姓名", "手机号", "状态", "渠道", "客户端", "风控", "风控分", "机审结果" };
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
			cell2.setCellValue(userlList.get(i - 1).getApplynumber());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(userlList.get(i - 1).getApplytime());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getName());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getPhone());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getState());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getSourcename());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getRegisteclient());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getRmModleName());
			cell2 = nextrow.createCell(8);
			cell2.setCellValue(userlList.get(i - 1).getRiskControlPoints());
			cell2 = nextrow.createCell(9);
			cell2.setCellValue(userlList.get(i - 1).getShareOfState());
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
	 * 已人审状态用户 
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/exportpeopleyet.do")
	public void exportpeopleyet(UserLikeParameter userLikeParameter, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PhoneDeal pd = new PhoneDeal();// 手机号加密解密工具类
		TuoMinUtil tm = new TuoMinUtil();// 脱敏工具类
		if ((userLikeParameter.getApplytimestart() != null && !"".equals(userLikeParameter.getApplytimestart()))
				&& (userLikeParameter.getApplytimeend() != null && !"".equals(userLikeParameter.getApplytimeend()))) {
			try {
				userLikeParameter.setApplytimestart(Timestamps.dateToStamp(userLikeParameter.getApplytimestart()));
				userLikeParameter.setApplytimeend(
						(Long.parseLong(Timestamps.dateToStamp(userLikeParameter.getApplytimeend())) + 86400000) + "");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (userLikeParameter.getPhone() != null && !"".equals(userLikeParameter.getPhone())) {
			userLikeParameter.setPhone(pd.encryption(userLikeParameter.getPhone()));
		}

		// 查询用户表的全部数据
		List<User> userlList = new ArrayList<User>(intOrderService.queryAllUserPeopleYetExcel(userLikeParameter));

		for (int i = 0; i < userlList.size(); i++) {
			userlList.get(i).setPhone(tm.mobileEncrypt(pd.decryption(userlList.get(i).getPhone())));// 将手机号进行脱敏
			userlList.get(i).setApplytime(Timestamps.stampToDate(userlList.get(i).getApplytime()));
			if (userlList.get(i).getState().equals("0")) {
				userlList.get(i).setState("有效");
			} else {
				userlList.get(i).setState("无效");
			}
			if (userlList.get(i).getShareOfState().equals("3")) {
				userlList.get(i).setShareOfState("人审未通过");
			} else {
				userlList.get(i).setShareOfState("人审通过");
			}
		}
		// 查询用户表有多少行记录
		Integer count = intOrderService.queryAllUserPeopleYetcount(userLikeParameter);
		// 创建excel表的表头
		String[] headers = { "申请编号", "申请时间", "姓名", "手机号", "状态", "渠道", "客户端", "风控", "风控分", "机审结果", "审核员" };
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
			cell2.setCellValue(userlList.get(i - 1).getApplynumber());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(userlList.get(i - 1).getApplytime());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getName());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getPhone());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getState());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getSourcename());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getRegisteclient());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getRmModleName());
			cell2 = nextrow.createCell(8);
			cell2.setCellValue(userlList.get(i - 1).getRiskControlPoints());
			cell2 = nextrow.createCell(9);
			cell2.setCellValue(userlList.get(i - 1).getShareOfState());
			cell2 = nextrow.createCell(10);
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

	/**
	 * 订单查询
	 * 用于导出excel的查询结果
	 * @param queryJson
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/exportorder.do")
	public void exportorder(OrderQueryParameter orderQueryParameter,HttpServletRequest request, HttpServletResponse response) throws IOException {
		PhoneDeal pd = new PhoneDeal();// 手机号加密解密工具类
		TuoMinUtil tm = new TuoMinUtil();// 脱敏工具类
		if ((orderQueryParameter.getOrderstarttime() != null && !"".equals(orderQueryParameter.getOrderstarttime()))
				&& (orderQueryParameter.getOrderendtime() != null
						&& !"".equals(orderQueryParameter.getOrderendtime()))) {
			try {
				orderQueryParameter.setOrderstarttime(Timestamps.dateToStamp(orderQueryParameter.getOrderstarttime()));
				orderQueryParameter.setOrderendtime(
						(Long.parseLong(Timestamps.dateToStamp(orderQueryParameter.getOrderendtime())) + 86400000)
								+ "");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (orderQueryParameter.getShouldstarttime() != null && !"".equals(orderQueryParameter.getShouldstarttime())
				&& orderQueryParameter.getShouldendtime() != null
				&& !"".equals(orderQueryParameter.getShouldendtime())) {
			try {
				orderQueryParameter
						.setShouldstarttime(Timestamps.dateToStamp(orderQueryParameter.getShouldstarttime()));
				orderQueryParameter.setShouldendtime(
						(Long.parseLong(Timestamps.dateToStamp(orderQueryParameter.getShouldendtime())) + 86400000)
								+ "");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (orderQueryParameter.getPhone() != null && !"".equals(orderQueryParameter.getPhone())) {
			orderQueryParameter.setPhone(pd.encryption(orderQueryParameter.getPhone()));
		}
		List<Orders> orderList = new ArrayList<>();//查询订单表的全部数据
		Integer count=0;//查询订单表有多少行记录
		if((!StringUtils.isEmpty(orderQueryParameter.getOrderstatus()))&&orderQueryParameter.getOrderstatus().equals("2")){
			orderList = new ArrayList<Orders>(intOrderService.queryAllordersByLikeacrapExcel(orderQueryParameter));
			count=intOrderService.queryAllordersByLikeCountacrap(orderQueryParameter);
		}else{
			orderList = new ArrayList<Orders>(intOrderService.queryAllordersByLikeExcel(orderQueryParameter));
			count=intOrderService.queryAllordersByLikeCount(orderQueryParameter);
		}
		for (int i = 0; i < orderList.size(); i++) {
			BigDecimal realmoney = intOrderService.queryrepaymoney(orderList.get(i).getId());// 该订单还款成功的还款金额--还款表
			if (realmoney == null) {
				realmoney = new BigDecimal("0.00");
			}
			BigDecimal offmoney = intOrderService.queryrepaymoneyoff(orderList.get(i).getId());// 该订单还款成功的还款金额--线下还款表
			if (offmoney == null) {
				offmoney = new BigDecimal("0.00");
			}
			BigDecimal bankmoney = intOrderService.queryrepaymoneybank(orderList.get(i).getId());// 该订单扣款成功的还款金额--银行卡自动扣款表
			if (bankmoney == null) {
				bankmoney = new BigDecimal("0.00");
			}
			BigDecimal money = realmoney.add(offmoney).add(bankmoney);
			orderList.get(i).setRepaymentMoney(String.valueOf(money));

			BigDecimal shourldmoney = orderList.get(i).getOrderdetails().getRealityBorrowMoney()
					.add(orderList.get(i).getOrderdetails().getInterestSum())
					.add(orderList.get(i).getOrderdetails().getInterestPenaltySum());
			orderList.get(i).setShourldmoney(shourldmoney);// 应还金额（借款金额+期限内总利息+逾期的逾期费）
			orderList.get(i).getUser().setPhone(tm.mobileEncrypt(pd.decryption(orderList.get(i).getUser().getPhone())));// 将手机号进行脱敏
			orderList.get(i).setShouldReturnTime(Timestamps.stampToDate(orderList.get(i).getShouldReturnTime()));
			orderList.get(i).setOrderCreateTime(Timestamps.stampToDate(orderList.get(i).getOrderCreateTime()));
			orderList.get(i).getUser().setRegistetime(Timestamps.stampToDate(orderList.get(i).getUser().getRegistetime()));

			List<DeferredAndOrder> listdefer = intOrderService.queryDefer(orderList.get(i).getId());
			List<Offlinedelay> listdeferlay = intOrderService.queryDeferlay(orderList.get(i).getId());

			orderList.get(i).setDeferrTime(listdefer.size() + listdeferlay.size());// 延期次数
			BigDecimal deferrMoney = new BigDecimal("0.00");// 延期金额
			BigDecimal defMoney = new BigDecimal("0.00");// 线上延期
			BigDecimal defMoneylay = new BigDecimal("0.00");// 人工延期
			if (!listdefer.isEmpty() && listdefer.size() != 0) {
				for (int j = 0; j < listdefer.size(); j++) {
					if(listdefer.get(j).getInterestOnArrears()==null){
						listdefer.get(j).setInterestOnArrears(new BigDecimal("0.00"));
					}
					defMoney = defMoney.add(listdefer.get(j).getInterestOnArrears());
				}
			}
			if (!listdeferlay.isEmpty() && listdeferlay.size() != 0) {
				for (int j = 0; j < listdeferlay.size(); j++) {
					if (listdeferlay.get(j).getExtensionfee() == null) {
						listdeferlay.get(j).setExtensionfee(new BigDecimal("0.00"));
					}
					defMoneylay = defMoneylay.add(listdeferlay.get(j).getExtensionfee());
				}
			}
			deferrMoney = defMoney.add(defMoneylay);
			orderList.get(i).setDeferrMoney(deferrMoney);
			
			if(orderList.get(i).getOrderStatus().equals("0")){
				orderList.get(i).setOrderStatus("期限中");
			}else if(orderList.get(i).getOrderStatus().equals("1")){
				orderList.get(i).setOrderStatus("已逾期");
			}else if(orderList.get(i).getOrderStatus().equals("2")){
				orderList.get(i).setOrderStatus("已延期");
			}else if(orderList.get(i).getOrderStatus().equals("3")){
				orderList.get(i).setOrderStatus("已还款");
			}else if(orderList.get(i).getOrderStatus().equals("4")){
				orderList.get(i).setOrderStatus("已坏账");
			}
			
			if(orderList.get(i).getUser().getAccount().isEmpty()){
				orderList.get(i).getUser().setAccount("机审");
			}
			
		}
	
		//创建excel表的表头
		String[] headers = {"订单编号", "姓名" ,"手机号","客户端", "注册时间","订单时间","订单状态","渠道","风控","风控分","审核员","贷款方式","还款期限","借款次数","放款金额","延期次数","延期金额","总利息","应还金额","实际还款"};
		//创建Excel工作簿
		HSSFWorkbook workbook=new HSSFWorkbook();
		//创建一个工作表sheet
		HSSFSheet sheet=workbook.createSheet();
		//创建第一行
		HSSFRow row=sheet.createRow(0);
		//定义一个单元格,相当于在第一行插入了三个单元格值分别是 "姓名", "性别", "年龄"
		HSSFCell cell=null;
		//插入第一行数据
				for(int i=0;i<headers.length;i++) {
					cell=row.createCell(i);
					cell.setCellValue(headers[i]);
				}
				//追加数据
						for(int i=1;i<=count;i++) {
							HSSFRow nextrow=sheet.createRow(i);
							HSSFCell cell2=nextrow.createCell(0);
							cell2.setCellValue(orderList.get(i-1).getOrderNumber());
							cell2=nextrow.createCell(1);
							cell2.setCellValue(orderList.get(i-1).getUser().getName());
							cell2=nextrow.createCell(2);
							cell2.setCellValue(orderList.get(i-1).getUser().getPhone());
							cell2=nextrow.createCell(3);
							cell2.setCellValue(orderList.get(i-1).getUser().getRegisteclient());
							cell2=nextrow.createCell(4);
							cell2.setCellValue(orderList.get(i-1).getUser().getRegistetime());
							cell2=nextrow.createCell(5);
							cell2.setCellValue(orderList.get(i-1).getOrderCreateTime());
							cell2=nextrow.createCell(6);
							cell2.setCellValue(orderList.get(i-1).getOrderStatus());
							cell2=nextrow.createCell(7);
							cell2.setCellValue(orderList.get(i-1).getUser().getSourcename());
							cell2=nextrow.createCell(8);
							cell2.setCellValue(orderList.get(i-1).getRiskcontrolname());
							cell2=nextrow.createCell(9);
							cell2.setCellValue(orderList.get(i-1).getRiskmanagementFraction());
							cell2=nextrow.createCell(10);
							cell2.setCellValue(orderList.get(i-1).getUser().getAccount());
							cell2=nextrow.createCell(11);
							cell2.setCellValue(orderList.get(i-1).getBorrowMoneyWay());
							cell2=nextrow.createCell(12);
							cell2.setCellValue(orderList.get(i-1).getBorrowTimeLimit());
							cell2=nextrow.createCell(13);
							cell2.setCellValue(orderList.get(i-1).getHowManyTimesBorMoney());
							cell2=nextrow.createCell(14);
							cell2.setCellValue(orderList.get(i-1).getOrderdetails().getMakeLoans().toString());
							
							
							cell2=nextrow.createCell(15);
							cell2.setCellValue(orderList.get(i-1).getDeferrTime());
							
							
							cell2=nextrow.createCell(16);
							cell2.setCellValue(orderList.get(i-1).getDeferrMoney().toString());
							
							cell2=nextrow.createCell(17);
							cell2.setCellValue(orderList.get(i-1).getOrderdetails().getInterestInAll().toString());
							
							cell2=nextrow.createCell(18);
							cell2.setCellValue(orderList.get(i-1).getShourldmoney().toString());
							cell2=nextrow.createCell(19);
							cell2.setCellValue(orderList.get(i-1).getRepaymentMoney());
							
						}
						//将excel的数据写入文件
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
							response.setHeader("Content-Disposition", "attachment; filename=agent_book.xls");//要保存的文件名
							response.setContentType("application/octet-stream; charset=utf-8");
							os.write(retArr);
							os.flush();
						} finally {
							if (os != null) {
								os.close();
							}
						}
	
	}
	
	
	@ResponseBody
	@RequestMapping("Hkuanhuidiao")
	public void OrderStuts(String status){
		intOrderService.UpdateOrdersUpdate();
	}
}
