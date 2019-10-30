package com.zhita.controller.finance;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Accountadjustment;
import com.zhita.model.manage.Bankdeductions;
import com.zhita.model.manage.Collection;
import com.zhita.model.manage.Offlinedelay;
import com.zhita.model.manage.Offlinetransfer;
import com.zhita.model.manage.Offlinjianmian;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.Thirdparty_interface;
import com.zhita.model.manage.Thirdpricefind;
import com.zhita.service.manage.finance.FinanceService;
import com.zhita.service.manage.newpayment.NewPaymentservice;



@Controller
@RequestMapping("fina")
public class FinanceController {

	
	@Autowired
	private FinanceService fianser;
	
	
	@Autowired
	private NewPaymentservice newsim;
	
	
	
	
	
	/**
	 * 放款实时流水
	 * @param payrecord
	 * @return
	 */
	@ResponseBody
	@RequestMapping("Allpayment_record")
	public Map<String, Object> Allpayment(Payment_record payrecord){
		return fianser.AllPaymentrecord(payrecord);
	}
	
	
	
	
	/**
	 * 放款实时流水
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("Allpayment_recordexport")
	public void Allpayment_recordexport(Payment_record payrecord, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Payment_record> userlList = new ArrayList<Payment_record>(fianser.AllPaymentrecordexport(payrecord));

		// 创建excel表的表头
		String[] headers = { "流水号时间", "放款渠道", "放款流水号", "开户行", "银行卡号", "放款金额", "订单编号", "姓名", "手机号" };
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
			cell2.setCellValue(userlList.get(i - 1).getRemittanceTime());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(userlList.get(i - 1).getSourceName());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getPipelinenumber());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getBank());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getBorrowRepayBankcard());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getPaymentmoney().toString());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getName());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getPhone());
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
	 * 放款实时流水订单详情
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("PaymentOrder")
	public Map<String, Object> OrderPayment(Orderdetails orderNumber){
		return fianser.OrderPayment(orderNumber);
	}
	
	
	
	
	/**
	 * 还款实时流水
	 * @param payrecord
	 * @return
	 */
	@ResponseBody
	@RequestMapping("HuanKuan")
	public Map<String, Object> HuanKuan(Payment_record payrecord){
		return fianser.Huankuan(payrecord);
	}
	
	
	
	
	

	/**
	 * 还款实时流水
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("HuanKuanexport")
	public void HuanKuanexportexport(Payment_record payrecord, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Payment_record> userlList = new ArrayList<Payment_record>(fianser.AllHuankuanexport(payrecord));

		// 创建excel表的表头
		String[] headers = { "流水号时间", "放款渠道", "放款流水号", "开户行", "银行卡号", "放款金额", "订单编号", "姓名", "手机号" };
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
			cell2.setCellValue(userlList.get(i - 1).getRemittanceTime());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(userlList.get(i - 1).getSourceName());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getPipelinenumber());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getBank());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getBorrowRepayBankcard());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getPaymentmoney().toString());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getName());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getPhone());
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
	 * 放款渠道查询
	 * @param compayId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("ThirdpatyAll")
	public Map<String, Object> Thirdpaty(Integer compayId){
		return fianser.ThirdpatyAll(compayId);
	}
	
	
	
	
	/**
	 * 还款渠道查询
	 * @param compayId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("RepaymentAll")
	public Map<String, Object> Repayment(Integer compayId){
		return fianser.RepaymentAll(compayId);
	}
	
	
	
	
	
	
	
	/**
	 * 添加线上调账
	 * @param acc
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AddAcount")
	public Map<String, Object> AddAccount(Accountadjustment acc){
		return fianser.Accountadjus(acc);
	}
	
	
	
	
	
	/**
	 * 根据订单编号查询订单详情
	 * @param orderNumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping("OrderAcount")
	public Map<String, Object> OrderAccount(Orderdetails orderNumber){
		return fianser.OrderAccount(orderNumber);
	}
	
	
	
	
	
	
	
	/**
	 * 查询线上调账订单
	 * @param ordetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping("SelectOrderAccount")
	public Map<String, Object> SelectOrderAccount(Orderdetails ordetail){
		return fianser.SelectOrderAccount(ordetail);
	}
	
	
	
	/**
	 * 查询线上调账订单
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("SelectOrderAccountExport")
	public void SelectOrderAccountExport(Orderdetails ordetail, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Accountadjustment> userlList = new ArrayList<Accountadjustment>(fianser.SelectOrderAccountSc(ordetail));

		// 创建excel表的表头
		String[] headers = { "订单编号", "姓名", "手机号", "调账时间", "还款渠道", "调账减免金额", "减免后应还总金额", "还款备注", "减免后最迟应还时间" };
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
			cell2.setCellValue(userlList.get(i - 1).getAmou_time());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getThname());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getAmountmoney().toString());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getTotalamount().toString());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getAccounttime());
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
	 * 还款实时流水
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("SelectOrderAccountexport")
	public void SelectOrderAccountexport(Payment_record payrecord, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Payment_record> userlList = new ArrayList<Payment_record>(fianser.AllHuankuanexport(payrecord));

		// 创建excel表的表头
		String[] headers = { "流水号时间", "放款渠道", "放款流水号", "开户行", "银行卡号", "放款金额", "订单编号", "姓名", "手机号" };
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
			cell2.setCellValue(userlList.get(i - 1).getRemittanceTime());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(userlList.get(i - 1).getSourceName());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getPipelinenumber());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getBank());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getBorrowRepayBankcard());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getPaymentmoney().toString());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getName());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getPhone());
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
	 * 线上已还清订单
	 * @param ordetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping("SelectNoMoney")
	public Map<String, Object> SelectNoMoneyAccount(Orderdetails ordetail){
		return fianser.SelectNoMoney(ordetail);
	}
	
	
	
	
	
	
	/**
	 * 线上已还清订单
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("SelectNoMoneyexport")
	public void SelectNoMoneyexport(Orderdetails ordetail, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Accountadjustment> userlList = new ArrayList<Accountadjustment>(fianser.SelectNoMoneyAc(ordetail));

		// 创建excel表的表头
		String[] headers = { "订单编号", "姓名", "手机号", "调账时间", "还款渠道", "还款流水号", "调账减免金额", "减免后应还总金额", "还款备注" , "减免后最迟实还时间" };
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
			cell2.setCellValue(userlList.get(i - 1).getAmou_time());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getThname());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getPipelinenumber());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getAmountmoney().toString());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getTotalamount().toString());
			cell2 = nextrow.createCell(8);
			cell2.setCellValue(userlList.get(i - 1).getRemarks());
			cell2 = nextrow.createCell(9);
			cell2.setCellValue(userlList.get(i - 1).getAccounttime());
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
	 * 线上未还清订单
	 * @param ordetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping("SelectOkMoney")
	public Map<String, Object> OkMoneyAccount(Orderdetails ordetail){
		return fianser.SelectOkMoney(ordetail);
	}
	
	
	
	
	
	/**
	 * 线上未还清订单
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("SelectOkMoneyexport")
	public void SelectOkMoneyexport(Orderdetails ordetail, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Accountadjustment> userlList = new ArrayList<Accountadjustment>(fianser.SelectOkMoneyAc(ordetail));

		// 创建excel表的表头
		String[] headers = { "订单编号", "姓名", "手机号", "调账时间", "还款渠道", "调账减免金额", "减免后应还总金额", "还款备注" , "减免后最迟实还时间", "逾期天数" };
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
			cell2.setCellValue(userlList.get(i - 1).getAmou_time());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getThname());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getAmountmoney().toString());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getTotalamount().toString());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getRemarks());
			cell2 = nextrow.createCell(8);
			cell2.setCellValue(userlList.get(i - 1).getAccounttime());
			cell2 = nextrow.createCell(9);
			cell2.setCellValue(userlList.get(i - 1).getOverdueNumberOfDays());
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
	 * 新增线下调账
	 * @param unde
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AddUndert")
	public Map<String, Object> AddUndert(Offlinetransfer unde){
		return fianser.AddUnderthe(unde);
	}
	
	
	
	
	
	/**
	 * 线下调账记录
	 * @param ordetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping("Orderoffline")
	public Map<String, Object> OrderOffine(Orderdetails ordetail){
		return fianser.Selectoffine(ordetail);
	}
	
	
	
	
	
	
	
	/**
	 * 线下查询
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AllXiaOrder")
	public Map<String, Object> XiaOrder(Orderdetails order){
		return fianser.SelectXiaOrder(order);
	}
	
	
	
	
	/**
	 * 线下减免调账
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("AllXiaOrderexport")
	public void AllXiaOrderexport(Orderdetails ordetail, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Offlinjianmian> userlList = new ArrayList<Offlinjianmian>(fianser.SelectXiaOrderAc(ordetail));

		// 创建excel表的表头
		String[] headers = { "操作时间	", "操作人", "用户订单", "用户姓名", "手机号", "减免前应还金额", "线下用户已还金额", "还款备注" };
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
			cell2.setCellValue(userlList.get(i - 1).getSedn_time());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(userlList.get(i - 1).getAccount());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getOrderNumber());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getName());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getPhone());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getMakeLoans().toString());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getOffusermoney().toString());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getRemarks());
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
	 * 查询一键扣款银行
	 * @param bank
	 * @return
	 */
	@ResponseBody
	@RequestMapping("BankDeduction")
	public Map<String, Object> BankDeduction(Bankdeductions bank){
		return fianser.SelectBankDeductOrders(bank);
	}
	
	
	
	
	/**
	 * 查询扣款详情
	 * @param bank
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AllBank")
	public Map<String, Object> AllBank(Integer orderId){
		return fianser.AllBank(orderId);
	}
	
	
	
	
	
	/**
	 * 添加
	 * @param bank
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AddBank")
	public Map<String, Object> AddBank(Bankdeductions orderId){
		return fianser.AddBank(orderId);
	}
	
	
	
	
	
	/**
	 * 延期扣款统计
	 * @param bank
	 * @return
	 */
	@ResponseBody
	@RequestMapping("DelayStatistics")
	public Map<String, Object> DelayStatistics(Bankdeductions bank){
		return fianser.AllDelayStatis(bank);
	}
	
	
	
	
	
	
	/**
	 * 延期   扣款统计
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("DelayStatisticsexport")
	public void DelayStatisticsexport(Bankdeductions bank, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Bankdeductions> userlList = new ArrayList<Bankdeductions>(fianser.AllDelayStatisAc(bank));

		// 创建excel表的表头
		String[] headers = { "时间", "延期笔数", "延期费", "银行扣款笔数", "银行扣款金额" };
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
			cell2.setCellValue(userlList.get(i - 1).getDeferredTime());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(userlList.get(i - 1).getOrderNum());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getDeferredamount().toString());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getDefeNum().toString());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getDeferredamount().toString());

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
	 * 总收入支出
	 * @param banl
	 * @return
	 */
	@ResponseBody
	@RequestMapping("financialoverview")
	public Map<String, Object> Financialove(Bankdeductions banl){
		return fianser.Financialover(banl);
	}
	
	
	
	
	
	/**
	 * 添加线下调减免
	 * @return
	 */
	@ResponseBody
	@RequestMapping("Addoff")
	public Map<String, Object> AddOff(Offlinjianmian off){
		return fianser.AddOffJianmian(off);
	}
	
	
	
	
	/**
	 * 查询公司延期数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("CompanyDelay")
	public Map<String, Object> CompanyDelay(Integer companyId){
		return fianser.CompanyDelay(companyId);
	}
	
	
	
	/**
	 * 添加人工延期
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AddDelaylabor")
	public Map<String, Object> AddDelay(Offlinedelay ofdelay){
		return fianser.AddDelay(ofdelay);
	}
	
	
	
	/**
	 * 查询延期记录
	 * @param of
	 * @return
	 */
	@ResponseBody
	@RequestMapping("Delaylabor")
	public Map<String, Object> Delaylabor(Offlinedelay of){
		return fianser.Delaylabor(of);
	}
	
	
	
	
	/**
	 * 延期记录
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("Delaylaborexport")
	public void Delaylaborexport(Offlinedelay of, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Offlinedelay> userlList = new ArrayList<Offlinedelay>(fianser.DelaylaborAc(of));

		// 创建excel表的表头
		String[] headers = { "操作时间", "操作人", "用户订单", "用户姓名", "手机号" , "身份证号" , "设置前延期后应还时间" , "逾期天数" , "设置前已延期次数/金额" , "线下已缴纳延期费" , "备注" , "设置后延期应还时间"};
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
			cell2.setCellValue(userlList.get(i - 1).getOperating_time());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(userlList.get(i - 1).getAccount());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getOrderId());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getName());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getPhone());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getIdcard_number());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getDeferAfterReturntime());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getOverdueNumberOfDays());
			cell2 = nextrow.createCell(8);
			cell2.setCellValue(userlList.get(i - 1).getDefeNum()+"/"+userlList.get(i - 1).getDefeMoney());
			cell2 = nextrow.createCell(9);
			cell2.setCellValue(userlList.get(i - 1).getExtensionfee().toString());
			cell2 = nextrow.createCell(10);
			cell2.setCellValue(userlList.get(i - 1).getRemarks());
			cell2 = nextrow.createCell(11);
			cell2.setCellValue(userlList.get(i - 1).getDelay_time());

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
	
	
	
	
	//后台管理---查询所有
	@ResponseBody
	@RequestMapping("/queryall")
    public List<Thirdpricefind> queryall(Integer companyid){
    	return fianser.queryall(companyid);
    }
    
    //后台管理----修改价格
	@ResponseBody
	@RequestMapping("/updateprice")
    public int updateprice(BigDecimal price,Integer id){
    	return fianser.updateprice(price, id);
    }
	
	//后台管理---费用统计
	@ResponseBody
	@RequestMapping("/pricetongji")
	public Map<String, Object> pricetongji(Integer companyId,Integer page,String starttime,String endtime) throws ParseException{
		Map<String, Object> map=fianser.pricetongji(companyId, page, starttime, endtime);
		return map;
	}
	
	
	
	//查询线上调账记录
	@ResponseBody
	@RequestMapping("AllAccorders")
	public Map<String, Object> AllAccorders(String orderNumber){
		return fianser.SelectAccOrders(orderNumber);
	}
	
	
	
	/**
	 * 删除线上减免记录
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("DeleteAccorders")
	public Map<String, Object> DeleteAccorders(Integer id){
		return fianser.DeleteAccorders(id);
	}
	
	
	
	@ResponseBody
	@RequestMapping("ThirAll")
	public Map<String, Object> ThirAllTian(Integer companyId){
		System.out.println(companyId);
		Map<String, Object> map = new HashMap<String, Object>();
		Thirdparty_interface paymentname = newsim.SelectPaymentName(companyId);//获取系统设置的 放款名称   和  还款名称
		System.out.println(paymentname.getLoanSource()+"111"+paymentname.getRepaymentSource());
		map.put("fangkuan", paymentname.getLoanSource());
		map.put("huankuan", paymentname.getRepaymentSource());
		return map;
	}

}
