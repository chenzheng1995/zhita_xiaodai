package com.zhita.controller.collection;

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
import com.zhita.model.manage.Collectiondetails;
import com.zhita.model.manage.Orderdetails;
import com.zhita.service.manage.collection.Collectionservice;

/**
 * 支付
 * @author Administrator
 *
 */
@Controller
@RequestMapping("collection")
public class CollectionController {
	
	
	@Autowired
	private Collectionservice collservice;

	
	
	
	
	/**
	 * 查询逾期未分配订单
	 * @param coll
	 * @return
	 */
	@ResponseBody
	@RequestMapping("BeoverdueCollection")
	public Map<String, Object> BeoverdueCollection(Collection coll){
		System.out.println(coll.getOrderNumber());
		return collservice.allBeoverdueConnection(coll);
	}
	
	
	
	
	/**
	 * 逾期未分配订单
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("BeoverdueCollectionexport")
	public void export(Collection coll, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Orderdetails> userlList = new ArrayList<Orderdetails>(collservice.allBeoverdueConnectionCollection(coll));

		// 查询用户表有多少行记录
		Integer count = collservice.SelectTotalCount(coll);
		// 创建excel表的表头
		String[] headers = { "订单编号", "姓名", "手机号", "贷款方式", "还款期数", "实借时间", "实借总金额", "应还时间", "逾期天数" , "逾期罚金/含逾应还总金额"};
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
		System.out.println(count);
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
			cell2.setCellValue(userlList.get(i - 1).getRealityBorrowMoney().toString());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getShouldReturnTime());
			cell2 = nextrow.createCell(8);
			cell2.setCellValue(userlList.get(i - 1).getOverdueNumberOfDays());
			cell2 = nextrow.createCell(9);
			cell2.setCellValue(userlList.get(i - 1).getInterestPenaltySum().toString());
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
	 * 查询催收员
	 * @param companyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("collectionmember")
	public Map<String, Object> Collectionmemeber(Integer companyId){
		return collservice.Collectionmember(companyId);
	}
	
	
	
	
	
	
	/**
	 * 分配催单员
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AddCollection")
	public Map<String, Object> UpdateCollection(Collection col){
		return collservice.UpdateColl(col);
	}
	
	
	
	
	
	
	
	/**
	 * 查询已逾期已分配
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("BeoverdueYifenp")
	public Map<String, Object> BeoverdueYifen(Orderdetails order){
		return collservice.BeoverdueYi(order);
	}
	
	
	
	
	
	/**
	 * 查询已逾期已分配
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("BeoverdueYifenpCollectionexport")
	public void BeoverdueYifenpCollectionexport(Orderdetails order, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Orderdetails> userlList = new ArrayList<Orderdetails>(collservice.BeoverdueYiCollection(order));

		// 创建excel表的表头
		String[] headers = { "订单编号" , "姓名" , "手机号" ,	"实借时间" , "实借总金额"	, "延期后应还时间" , "逾期天数"	, "逾期等级" , "逾期罚金/含逾应还总金额" , "催收人"	, "催收时间" , "催收状态" , "实还金额"};
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
			HSSFRow nextrow = sheet.createRow(i);
			HSSFCell cell2 = nextrow.createCell(0);
			cell2.setCellValue(userlList.get(i - 1).getOrderNumber());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(userlList.get(i - 1).getName());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getPhone());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getOrderCreateTime());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getRealityBorrowMoney().toString());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getRealtime());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getOverdueNumberOfDays());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getOverdueGrade());
			cell2 = nextrow.createCell(8);
			cell2.setCellValue(userlList.get(i - 1).getInterestPenaltySum().toString()+"/"+userlList.get(i - 1).getOrder_money().toString());
			cell2 = nextrow.createCell(9);
			cell2.setCellValue(userlList.get(i - 1).getReallyName());
			cell2 = nextrow.createCell(10);
			cell2.setCellValue(userlList.get(i - 1).getCollectionTime());
			cell2 = nextrow.createCell(11);
			cell2.setCellValue(userlList.get(i - 1).getCollectionStatus());
			cell2 = nextrow.createCell(12);
			cell2.setCellValue(userlList.get(i - 1).getRealityBorrowMoney().toString());
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
	 * 催收率详情
	 * @param orders
	 * @return
	 */
	@ResponseBody
	@RequestMapping("Collectiondetails")
	public Map<String, Object> AllocatedOrders(Orderdetails orders){
		return collservice.Colldetails(orders);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 查询催收率报表
	 * @param coll
	 * @return
	 */
	@ResponseBody
	@RequestMapping("CollectionLv")
	public Map<String, Object> CollectionLv(Collection coll){
		return collservice.Collectionmemberdetails(coll);
	}
	
	
	
	
	
	/**
	 * 查询已逾期已分配
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("Collectiondetailsexport")
	public void Collectiondetailsexport(Collection coll, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Collection> userlList = new ArrayList<Collection>(collservice.ColldetailsYiCollection(coll));

		for (int i = 0; i < userlList.size(); i++) {
			
		}
		// 查询用户表有多少行记录
		Integer count = userlList.size();
		// 创建excel表的表头
		String[] headers = { "日期", "订单总数", "分配订单数", "承诺还款订单数", "未还清订单数", "坏账订单数", "催回率(%)"};
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
			cell2.setCellValue(userlList.get(i - 1).getRealtime());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(userlList.get(i - 1).getOrderNum());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getCollection_count());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getSameday());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getPaymentmade());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getConnected());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getDataCol().toString());
			
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
	 * 查询催收员催收率报表
	 * @param coll
	 * @return
	 */
	@ResponseBody
	@RequestMapping("CollectionUserLv")
	public Map<String, Object> CollectionUserLv(Collection coll){
		return collservice.CollectionmemberUser(coll);
	}
	
	
	
	
	
	/**
	 * 查询催收员催收率报表
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("CollectionUserLvexport")
	public void CollectionUserLvexport(Collection coll, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Collection> userlList = new ArrayList<Collection>(collservice.CollectionmemberUserlv(coll));

		for (int i = 0; i < userlList.size(); i++) {
			
		}
		// 查询用户表有多少行记录
		Integer count = userlList.size();
		// 创建excel表的表头
		String[] headers = { "催收员姓名", "分配订单数", "承诺还款订单数", "未还清订单数", "坏账订单数", "催回率(%)"};
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
			cell2.setCellValue(userlList.get(i - 1).getRealtime());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(userlList.get(i - 1).getOrderNum());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getCollection_count());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getSameday());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getPaymentmade());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getConnected());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getDataCol().toString());
			
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
	 * 查询催收员工报表
	 * @param coll
	 * @return
	 */
	@ResponseBody
	@RequestMapping("Collectionmemberdetails")
	public Map<String, Object> Collectionmemberdetails(Collection coll){
		return collservice.Collectionmemberdetails(coll);
	}
	
	
	
	
	
	
	
	/**
	 * 已分配未催收
	 * @param col
	 * @return
	 */
	@ResponseBody
	@RequestMapping("FenpeiWeiCollection")
	public Map<String, Object> FenpeiWeiCollection(Collection col){
		return collservice.FenpeiWeiCollection(col);
	}
	
	
	
	
	
	/**
	 * 已分配未催收
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("FenpeiWeiCollectionexport")
	public void FenpeiWeiCollectionexport(Collection coll, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Orderdetails> userlList = new ArrayList<Orderdetails>(collservice.FenpeiWeiCollectionAc(coll));

		for (int i = 0; i < userlList.size(); i++) {
			
		}
		// 查询用户表有多少行记录
		Integer count = userlList.size();
		// 创建excel表的表头
		String[] headers = { "订单编号", "真实姓名", "手机号", "贷款方式", "还款期数", "实借时间" , "实借总金额" , "延期后应还时间" , "逾期天数" , "逾期等级" , "逾期罚金/含逾应还总金额" , "催收时间" , "用户状态" , "承诺还清部分金额"};
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
			cell2.setCellValue(userlList.get(i - 1).getRealityBorrowMoney().toString());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getDeferAfterReturntime());
			cell2 = nextrow.createCell(8);
			cell2.setCellValue(userlList.get(i - 1).getOverdueNumberOfDays());
			cell2 = nextrow.createCell(9);
			cell2.setCellValue(userlList.get(i - 1).getOverdueGrade());
			cell2 = nextrow.createCell(10);
			cell2.setCellValue(userlList.get(i - 1).getInterestPenaltySum()+"/"+userlList.get(i - 1).getRealityBorrowMoney());
			cell2 = nextrow.createCell(11);
			cell2.setCellValue(userlList.get(i - 1).getCollectionTime());
			cell2 = nextrow.createCell(12);
			cell2.setCellValue(userlList.get(i - 1).getUsertype());
			cell2 = nextrow.createCell(13);
			cell2.setCellValue("0");
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
	 * 已分配已催收
	 * @param 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("YiCollection")
	public Map<String, Object> YiCollection(Collection col){
		return collservice.YiCollection(col);
	}
	
	
	
	
	/**
	 * 已分配已催收
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("YiCollectionexport")
	public void YiCollectionexport(Collection coll, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Orderdetails> userlList = new ArrayList<Orderdetails>(collservice.YiCollectionAc(coll));

		for (int i = 0; i < userlList.size(); i++) {
			
		}
		// 查询用户表有多少行记录
		Integer count = userlList.size();
		// 创建excel表的表头
		String[] headers = { "订单编号", "真实姓名", "手机号", "贷款方式", "还款期数", "实借时间" , "实借总金额" , "延期后应还时间" , "逾期天数" , "逾期等级" , "逾期罚金/含逾应还总金额" , "催收时间" , "用户状态" , "承诺还清部分金额"};
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
			cell2.setCellValue(userlList.get(i - 1).getRealityBorrowMoney().toString());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getDeferAfterReturntime());
			cell2 = nextrow.createCell(8);
			cell2.setCellValue(userlList.get(i - 1).getOverdueNumberOfDays());
			cell2 = nextrow.createCell(9);
			cell2.setCellValue(userlList.get(i - 1).getOverdueGrade());
			cell2 = nextrow.createCell(10);
			cell2.setCellValue(userlList.get(i - 1).getInterestPenaltySum()+"/"+userlList.get(i - 1).getRealityBorrowMoney());
			cell2 = nextrow.createCell(11);
			cell2.setCellValue(userlList.get(i - 1).getCollectionTime());
			cell2 = nextrow.createCell(12);
			cell2.setCellValue(userlList.get(i - 1).getUsertype());
			cell2 = nextrow.createCell(13);
			cell2.setCellValue("0");
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
	 * 已分配未催收  完成联系  功能
	 * @param coldets
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AddCollectiondertilas")
	public Map<String, Object> AddCollectiondertails(Collection coldets){
		return collservice.AddColloetails(coldets);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 添加详情
	 * @param coldets
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AddColl")
	public Map<String, Object> AddCollOrders(Collectiondetails coldets){
		return collservice.AddCollOrders(coldets);
	}
	
	
	
	/**
	 * 结束催收
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("JieShu")
	public Map<String, Object> JieShu(Integer orderId){
		return collservice.JieShuCollection(orderId);
	}
	
	
	
	/**
	 * 取消分配
	 */
	@ResponseBody
	@RequestMapping("deleCollection")
	public Map<String, Object> DeleteCollection(Integer colid){
		return collservice.DeleteCollection(colid);
	}
}
