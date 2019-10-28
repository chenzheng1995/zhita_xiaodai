package com.zhita.controller.postloanorders;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
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

import com.zhita.model.manage.Collection;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Overdue;
import com.zhita.service.manage.postloanorders.Postloanorderservice;

/**
 * 贷后订单管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("postloanor")
public class PostloanordersController {
	
	@Autowired
	private Postloanorderservice postloanor;
	
	
	/**
	 * 期限内订单
	 * @param detils
	 * @return
	 */
	@ResponseBody
	@RequestMapping("postOrders")
	public Map<String, Object> SelectOrderpostloanor(Orderdetails detils){
		return postloanor.allpostOrders(detils);
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 已还订单
	 * @param detils
	 * @return
	 */
	@ResponseBody
	@RequestMapping("YihuanOrders")
	public Map<String, Object> SelectYihuanOrders(Orderdetails detils){
		return postloanor.YiHuanOrders(detils);
	}
	
	
	
	
	
	/**
	 * 已还订单
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("YihuanOrdersexport")
	public void YihuanOrdersexport(Orderdetails coll, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Orderdetails> userlList = new ArrayList<Orderdetails>(postloanor.YiHuanOrdersAc(coll));

		// 创建excel表的表头
		String[] headers = { "订单编号", "姓名", "手机号", "客户端", "渠道", "贷款方式", "期限", "实借时间", "实借金额" , "放款金额" , "延期后应还时间" , "逾期天数" , "逾期罚金" , "应还金额" , "实还时间" ,"实还金额"};
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
		for (int i = 1; i <= userlList.size(); i++) {
			System.out.println(i);
			HSSFRow nextrow = sheet.createRow(i);
			HSSFCell cell2 = nextrow.createCell(0);
			cell2.setCellValue(userlList.get(i - 1).getOrderNumber());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(userlList.get(i - 1).getName());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getPhone());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getRegisteClient());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getSourceName());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getBorrowMoneyWay());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getBorrowTimeLimit());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getOrderCreateTime());
			cell2 = nextrow.createCell(8);
			cell2.setCellValue(userlList.get(i - 1).getRealityAccount().toString());
			cell2 = nextrow.createCell(9);
			cell2.setCellValue(userlList.get(i - 1).getMakeLoans().toString());
			cell2 = nextrow.createCell(10);
			cell2.setCellValue(userlList.get(i - 1).getDeferAfterReturntime());
			cell2 = nextrow.createCell(11);
			cell2.setCellValue(userlList.get(i - 1).getOverdueNumberOfDays());
			cell2 = nextrow.createCell(12);
			cell2.setCellValue(userlList.get(i - 1).getInterestPenaltySum().toString());
			cell2 = nextrow.createCell(13);
			cell2.setCellValue(userlList.get(i - 1).getOrder_money().toString());
			cell2 = nextrow.createCell(14);
			cell2.setCellValue(userlList.get(i - 1).getRealtime());
			cell2 = nextrow.createCell(15);
			cell2.setCellValue(userlList.get(i - 1).getRepaymentMoney().toString());
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
	 * 坏账订单
	 * @param detils
	 * @return
	 */
	@ResponseBody
	@RequestMapping("HuaiZhangOrders")
	public Map<String, Object> SelectHuaiZhangOrders(Orderdetails detils){
		return postloanor.HuaiZhangOrders(detils);
	}
	
	
	
	
	
	
	/**
	 * 坏账订单
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("HuaiZhangOrdersexport")
	public void HuaiZhangOrdersexport(Orderdetails coll, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Orderdetails> userlList = new ArrayList<Orderdetails>(postloanor.HuaiZhangOrdersAc(coll));

		// 创建excel表的表头
		String[] headers = { "订单编号", "姓名", "手机号", "客户端", "渠道", "贷款方式", "还款期限", "实借时间", "实借金额/放款金额" , "延期后应还时间" , "逾期等级" , "逾期天数" , "逾期罚金" , "应还金额" };
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
		for (int i = 1; i <= userlList.size(); i++) {
			System.out.println(i);
			HSSFRow nextrow = sheet.createRow(i);
			HSSFCell cell2 = nextrow.createCell(0);
			cell2.setCellValue(userlList.get(i - 1).getOrderNumber());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(userlList.get(i - 1).getName());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getPhone());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getRegisteClient());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getSourceName());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getBorrowMoneyWay());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getBorrowTimeLimit());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getOrderCreateTime());
			cell2 = nextrow.createCell(8);
			cell2.setCellValue(userlList.get(i - 1).getRealityAccount().toString()+"/"+userlList.get(i - 1).getMakeLoans().toString());
			cell2 = nextrow.createCell(9);
			cell2.setCellValue(userlList.get(i - 1).getDeferAfterReturntime());
			cell2 = nextrow.createCell(10);
			cell2.setCellValue(userlList.get(i - 1).getOverdueGrade());
			cell2 = nextrow.createCell(11);
			cell2.setCellValue(userlList.get(i - 1).getOverdueNumberOfDays());
			cell2 = nextrow.createCell(12);
			cell2.setCellValue(userlList.get(i - 1).getInterestPenaltySum().toString()+"/"+userlList.get(i - 1).getOrder_money().toString());
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
	 * 已逾期订单
	 * @param detils
	 * @return
	 */
	@ResponseBody
	@RequestMapping("CollectionOrderSum")
	public Map<String, Object> CollectionOrders(Orderdetails detils){
		return postloanor.CollecOrders(detils);
	}
	
	
	
	
	
	
	
	/**
	 * 逾期订单
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("CollecOrdersexport")
	public void CollecOrdersexport(Orderdetails coll, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Orderdetails> userlList = new ArrayList<Orderdetails>(postloanor.CollecOrdersAc(coll));

		// 创建excel表的表头
				String[] headers = { "订单编号", "姓名", "手机号", "客户端", "渠道", "贷款方式", "期限", "实借时间", "实借金额" , "放款金额" , "应还时间" , "逾期等级" ,"逾期天数" , "逾期罚金" , "应还金额" };
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
				for (int i = 1; i <= userlList.size(); i++) {
					System.out.println(i);
					HSSFRow nextrow = sheet.createRow(i);
					HSSFCell cell2 = nextrow.createCell(0);
					cell2.setCellValue(userlList.get(i - 1).getOrderNumber());
					cell2 = nextrow.createCell(1);
					cell2.setCellValue(userlList.get(i - 1).getName());
					cell2 = nextrow.createCell(2);
					cell2.setCellValue(userlList.get(i - 1).getPhone());
					cell2 = nextrow.createCell(3);
					cell2.setCellValue(userlList.get(i - 1).getRegisteClient());
					cell2 = nextrow.createCell(4);
					cell2.setCellValue(userlList.get(i - 1).getSourceName());
					cell2 = nextrow.createCell(5);
					cell2.setCellValue(userlList.get(i - 1).getBorrowMoneyWay());
					cell2 = nextrow.createCell(6);
					cell2.setCellValue(userlList.get(i - 1).getBorrowTimeLimit());
					cell2 = nextrow.createCell(7);
					cell2.setCellValue(userlList.get(i - 1).getOrderCreateTime());
					cell2 = nextrow.createCell(8);
					cell2.setCellValue(userlList.get(i - 1).getRealityAccount().toString());
					cell2 = nextrow.createCell(9);
					cell2.setCellValue(userlList.get(i - 1).getMakeLoans().toString());
					cell2 = nextrow.createCell(10);
					cell2.setCellValue(userlList.get(i - 1).getDeferAfterReturntime());
					cell2 = nextrow.createCell(11);
					cell2.setCellValue(userlList.get(i - 1).getOverdueGrade());
					cell2 = nextrow.createCell(12);
					cell2.setCellValue(userlList.get(i - 1).getOverdueNumberOfDays());
					cell2 = nextrow.createCell(13);
					cell2.setCellValue(userlList.get(i - 1).getInterestPenaltySum().toString());
					cell2 = nextrow.createCell(14);
					cell2.setCellValue(userlList.get(i - 1).getOrder_money().toString());
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
	 * 逾期订单
	 * @param detils
	 * @return
	 */
	@ResponseBody
	@RequestMapping("BeoverduepostOrders")
	public Map<String, Object> SelectBeoverdueOrderpostloanor(Orderdetails detils){
		return postloanor.allpostOrdersBeoverdue(detils);
	}
	
	
	
	
	
	/**
	 * 未逾期未分配
	 * @return
	 */
	@ResponseBody
	@RequestMapping("CollectionNoOrder")
	public Map<String, Object> SelectCollection(Orderdetails order){
		return postloanor.SelectCollection(order);
	}
	
	
	
	
	
	
	
	/**
	 * 未逾期未分配订单
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("CollectionNoOrderexport")
	public void CollectionNoOrderexport(Orderdetails coll, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Orderdetails> userlList = new ArrayList<Orderdetails>(postloanor.SelectCollectionAc(coll));

		// 创建excel表的表头
				String[] headers = { "订单编号", "姓名", "手机号" , "贷款方式", "还款期限", "实借时间", "实借金额/放款金额" , "应还利息/应还金额" };
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
				for (int i = 1; i <= userlList.size(); i++) {
					System.out.println(i);
					HSSFRow nextrow = sheet.createRow(i);
					HSSFCell cell2 = nextrow.createCell(0);
					cell2.setCellValue(userlList.get(i - 1).getOrderNumber());
					cell2 = nextrow.createCell(1);
					cell2.setCellValue(userlList.get(i - 1).getName());
					cell2 = nextrow.createCell(2);
					cell2.setCellValue(userlList.get(i - 1).getPhone());
					cell2 = nextrow.createCell(3);
					cell2.setCellValue(userlList.get(i - 1).getBorrowMoneyWay());
					cell2 = nextrow.createCell(4);
					cell2.setCellValue(userlList.get(i - 1).getBorrowTimeLimit());
					cell2 = nextrow.createCell(5);
					cell2.setCellValue(userlList.get(i - 1).getOrderCreateTime());
					cell2 = nextrow.createCell(6);
					cell2.setCellValue(userlList.get(i - 1).getRealityAccount().toString()+"/"+userlList.get(i - 1).getMakeLoans().toString());
					cell2 = nextrow.createCell(7);
					cell2.setCellValue(userlList.get(i - 1).getInterestSum()+"/"+userlList.get(i - 1).getOrder_money().toString());
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
	 * 订单分配催收员
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("UpdateOver")
	public Map<String, Object> UpdateOver(Orderdetails order){
		return postloanor.UpdateOrder(order);
	}
	
	
	
	
	
	/**
	 * 未逾期已分配
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("NoCollection")
	public Map<String, Object> ConnectionOrder(Orderdetails order){
		return postloanor.CollectionOrderdet(order);
	}
	
	
	
	
	
	
	
	/**
	 * 未逾期未分配订单
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("NoCollectionxport")
	public void NoCollectionxport(Orderdetails coll, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Orderdetails> userlList = new ArrayList<Orderdetails>(postloanor.CollectionOrderdetAc(coll));

		// 创建excel表的表头
				String[] headers = { "订单编号", "姓名", "手机号" , "贷款方式", "还款期限", "实借时间", "实借金额/放款金额" , "延期前应还时间" , "应还利息/应还金额" , "每次延期次数" , "延期后应还时间" , "延期次数/延期金额" , "催收人" , "分配时间"};
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
				for (int i = 1; i <= userlList.size(); i++) {
					System.out.println(i);
					HSSFRow nextrow = sheet.createRow(i);
					HSSFCell cell2 = nextrow.createCell(0);
					cell2.setCellValue(userlList.get(i - 1).getOrderNumber());
					cell2 = nextrow.createCell(1);
					cell2.setCellValue(userlList.get(i - 1).getName());
					cell2 = nextrow.createCell(2);
					cell2.setCellValue(userlList.get(i - 1).getPhone());
					cell2 = nextrow.createCell(3);
					cell2.setCellValue(userlList.get(i - 1).getBorrowMoneyWay());
					cell2 = nextrow.createCell(4);
					cell2.setCellValue(userlList.get(i - 1).getBorrowTimeLimit());
					cell2 = nextrow.createCell(5);
					cell2.setCellValue(userlList.get(i - 1).getOrderCreateTime());
					cell2 = nextrow.createCell(6);
					cell2.setCellValue(userlList.get(i - 1).getRealityAccount().toString()+"/"+userlList.get(i - 1).getMakeLoans().toString());
					cell2 = nextrow.createCell(7);
					cell2.setCellValue(userlList.get(i - 1).getDeferAfterReturntime());
					cell2 = nextrow.createCell(8);
					cell2.setCellValue(userlList.get(i - 1).getInterestSum()+"/"+userlList.get(i - 1).getOrder_money().toString());
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
	 * 逾前催收率报表
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("CollectionRecoveryrate")
	public Map<String, Object> Recoveryrate(Orderdetails order){
		return postloanor.CollectionRecovery(order);
	}
	
	

	/**
	 * 逾前催收率报表
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("CollectionRecoveryratexport")
	public void CollectionRecoveryratexport(Orderdetails coll, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Collection> userlList = new ArrayList<Collection>(postloanor.CollectionRecoveryAc(coll));

		// 创建excel表的表头
				String[] headers = { "日期", "应还金额", "未分配总数" , "电话未接通数", "电话已接通数", "当天未还款数", "当天已还款数" , "当天还款率(%)" };
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
				for (int i = 1; i <= userlList.size(); i++) {
					System.out.println(i);
					HSSFRow nextrow = sheet.createRow(i);
					HSSFCell cell2 = nextrow.createCell(0);
					cell2.setCellValue(userlList.get(i - 1).getCollectiondate());
					cell2 = nextrow.createCell(1);
					cell2.setCellValue(userlList.get(i - 1).getShouldReapyMoney().toString());
					cell2 = nextrow.createCell(2);
					cell2.setCellValue(userlList.get(i - 1).getCollection_count());
					cell2 = nextrow.createCell(3);
					cell2.setCellValue(userlList.get(i - 1).getNotconnected());
					cell2 = nextrow.createCell(4);
					cell2.setCellValue(userlList.get(i - 1).getConnected());
					cell2 = nextrow.createCell(5);
					cell2.setCellValue(userlList.get(i - 1).getSameday());
					cell2 = nextrow.createCell(6);
					cell2.setCellValue(userlList.get(i - 1).getPaymentmade());
					cell2 = nextrow.createCell(7);
					cell2.setCellValue(userlList.get(i - 1).getPaymentmadeData());
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
	 * 逾前催收员
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("OverdueUser")
	public Map<String, Object> OverdueUser(Orderdetails order){
		return postloanor.OverdueUser(order);
	}
	
	
	
	/**
	 * 逾前催收率报表
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("OverdueUserexport")
	public void OverdueUserexport(Orderdetails coll, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Collection> userlList = new ArrayList<Collection>(postloanor.OverdueUserAc(coll));

		// 创建excel表的表头
				String[] headers = { "日期", "应还金额", "未分配总数" , "电话未接通数", "电话已接通数", "当天未还款数", "当天已还款数" , "当天还款率(%)" };
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
				for (int i = 1; i <= userlList.size(); i++) {
					System.out.println(i);
					HSSFRow nextrow = sheet.createRow(i);
					HSSFCell cell2 = nextrow.createCell(0);
					cell2.setCellValue(userlList.get(i - 1).getCollectiondate());
					cell2 = nextrow.createCell(1);
					cell2.setCellValue(userlList.get(i - 1).getShouldReapyMoney().toString());
					cell2 = nextrow.createCell(2);
					cell2.setCellValue(userlList.get(i - 1).getCollection_count());
					cell2 = nextrow.createCell(3);
					cell2.setCellValue(userlList.get(i - 1).getNotconnected());
					cell2 = nextrow.createCell(4);
					cell2.setCellValue(userlList.get(i - 1).getConnected());
					cell2 = nextrow.createCell(5);
					cell2.setCellValue(userlList.get(i - 1).getSameday());
					cell2 = nextrow.createCell(6);
					cell2.setCellValue(userlList.get(i - 1).getPaymentmade());
					cell2 = nextrow.createCell(7);
					cell2.setCellValue(userlList.get(i - 1).getPaymentmadeData());
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
	 * 完成逾前催收
	 * @param ov
	 * @return
	 */
	@ResponseBody
	@RequestMapping("Wancheng")
	public Map<String, Object> OverDueUserA(Overdue ov){
		return postloanor.WanchengUser(ov);
	}
	
	
	
	/**
	 * 查看我的催收订单
	 * @return
	 */
	@ResponseBody
	@RequestMapping("MyOverdue")
	public Map<String, Object> MyOverdue(Orderdetails order){
		return postloanor.MyOverdues(order);
	}
	
	
	
	
	/**
	 * 修改催收订单ID
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("UpdateOverdue")
	public Map<String, Object> UpdateOverdue(Orderdetails order){
		return postloanor.UpdateOverdue(order);
	}
	
	
	
	@ResponseBody
	@RequestMapping("deleteoverdue")
	public Map<String, Object> DeleteOverdue(Integer overdue_id){
		return postloanor.DeOverdue(overdue_id);
	}
}
