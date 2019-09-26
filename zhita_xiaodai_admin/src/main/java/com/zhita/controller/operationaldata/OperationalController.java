package com.zhita.controller.operationaldata;

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
import com.zhita.model.manage.Orders;
import com.zhita.service.manage.operational.OperationalService;


@Controller
@RequestMapping("operation")
public class OperationalController {
	
	
	
	@Autowired
	private OperationalService oser;
	
	

	
	/**
	 * 平台总数据
	 * @param or
	 * @return
	 */
	@ResponseBody
	@RequestMapping("platformsNum")
	public Map<String, Object> Platformsoppicues(Orderdetails or){
		return oser.PlatformsNu(or);
	}
	
	
	
	
	
	
	/**
	 * 平台总数据
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("PlatformsNumexport")
	public void PlatformsNumexport(Orderdetails or, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Orders> userlList = new ArrayList<Orders>(oser.PlatformsNuexport(or));

		// 创建excel表的表头
		String[] headers = { "日期", "总放款金额", "总放款笔数", "总还款金额", "总还款笔数", "总逾期金额", "总逾期笔数", "总坏账金额", "总坏账笔数" , "线下减免条数" , "线下减免金额"};
		// 创建Excel工作簿
		@SuppressWarnings("resource")
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
			cell2.setCellValue(userlList.get(i - 1).getGesamtbetragderDarlehen().toString());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getGesamtbetragderNum());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getGesamtbetragderRvckzahlung().toString());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getZahlderGesamtdarlehen());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getGesamtbetraguberfalligerBetrag().toString());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getGesamtbetraguberfallNum());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getAmountofbaddebts().toString());
			cell2 = nextrow.createCell(8);
			cell2.setCellValue(userlList.get(i - 1).getBaddebt());
			cell2 = nextrow.createCell(9);
			cell2.setCellValue(userlList.get(i - 1).getXianscount());
			cell2 = nextrow.createCell(10);
			cell2.setCellValue(userlList.get(i - 1).getXiansmoney().toString());
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
	 * 还款数据
	 * @param or
	 * @return
	 */
	@ResponseBody
	@RequestMapping("HuanKuandata")
	public Map<String, Object> HuanKuandata(Orderdetails or){
		return oser.HuanKuan(or);
	}
	
	
	
	/**
	 * 平台总数据
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("HuanKuandataexport")
	public void HuanKuandataexport(Orderdetails or, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 查询用户表的全部数据
		List<Orders> userlList = new ArrayList<Orders>(oser.HuanKuanexport(or));

		// 创建excel表的表头
		String[] headers = { "日期", "总放款金额", "总放款笔数", "总还款金额", "总还款笔数", "总逾期金额", "总逾期笔数", "总坏账金额", "总坏账笔数" , "线下减免条数" , "线下减免金额"};
		// 创建Excel工作簿
		@SuppressWarnings("resource")
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
			cell2.setCellValue(userlList.get(i - 1).getGesamtbetragderDarlehen().toString());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(userlList.get(i - 1).getGesamtbetragderNum());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(userlList.get(i - 1).getGesamtbetragderRvckzahlung().toString());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(userlList.get(i - 1).getZahlderGesamtdarlehen());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(userlList.get(i - 1).getGesamtbetraguberfalligerBetrag().toString());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(userlList.get(i - 1).getGesamtbetraguberfallNum());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(userlList.get(i - 1).getAmountofbaddebts().toString());
			cell2 = nextrow.createCell(8);
			cell2.setCellValue(userlList.get(i - 1).getBaddebt());
			cell2 = nextrow.createCell(9);
			cell2.setCellValue(userlList.get(i - 1).getXianscount());
			cell2 = nextrow.createCell(10);
			cell2.setCellValue(userlList.get(i - 1).getXiansmoney().toString());
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
	 * 逾期数据
	 * @param or
	 * @return
	 */
	@ResponseBody
	@RequestMapping("CollectionData")
	public Map<String, Object> CollectionData(Orderdetails or){
		return oser.CollectionData(or);
	}
	
	
	
	
	
	
	/**
	 * 订单收支数据
	 * @param or
	 * @return
	 */
	@ResponseBody
	@RequestMapping("OrderBudget")
	public Map<String, Object> OrderBudget(Orderdetails or){
		return oser.OrderBudget(or);
	}
	
	
	
	
	/**
	 * 查询引流平台
	 * @param companyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AllDrainage")
	public Map<String, Object> AllDrainge(Integer companyId){
		return oser.AllDrainageOf(companyId);
	}
	
	
	
	
	
	/**
	 * 逾期等级
	 * @param companyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("YuqiM")
	public Map<String, Object> YuqiM(Integer companyId){
		return oser.AllOverdueclass(companyId);
	}
}
