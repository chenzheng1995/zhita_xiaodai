package com.zhita.controller.xinyan.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhita.controller.xinyan.Config;
import com.zhita.controller.xinyan.rsa.RsaCodingUtil;
import com.zhita.controller.xinyan.util.HttpUtils;
import com.zhita.controller.xinyan.util.MapToXMLString;
import com.zhita.controller.xinyan.util.SecurityUtil;
import com.zhita.controller.xinyan.util.UUIDGenerator;

import net.sf.json.JSONObject;

/**
 * @author 淘气
 * @createtime 2016年9月26日下午8:58:24
 */
public class OperatorQueryInfoAction extends HttpServlet {

	private static final long serialVersionUID = 4865269178052033560L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String mobile = req.getParameter("mobile");
		String type = req.getParameter("type");
		log("=====手机号(mobile):" + mobile+",手机基本信息类型(type)："+type);
		/** 1、 商户号 **/
		String member_id = Config.getConstants().get("member.id");
		/** 2、终端号 **/
		String terminal_id = Config.getConstants().get("terminal.id");
		/** 3、 加密数据类型 **/
		String data_type = Config.getConstants().get("data.type");
		/** 4、加密数据 **/

		/** 组装参数 (7) **/
		String trans_id=UUIDGenerator.getUUID();// 必须入库 并且唯一 商户订单号
		String trade_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 订单日期
		Map<Object, Object> ArrayData = new HashMap<Object, Object>();
		ArrayData.put("trans_id", trans_id);
		ArrayData.put("member_id", member_id);
		ArrayData.put("terminal_id", terminal_id);
		ArrayData.put("mobile", mobile);
		ArrayData.put("trade_date", trade_date);
		ArrayData.put("type", type);
		ArrayData.put("industry_type", "A1");//请根据自己的行业类型传相应的参数
		Map<Object, Object> ArrayData1 = new HashMap<Object, Object>();
		String XmlOrJson = "";
		if (data_type.equals("xml")) {
			ArrayData1.putAll(ArrayData);
			XmlOrJson = MapToXMLString.converter(ArrayData1, "data_content");
		} else {
			JSONObject jsonObjectFromMap = JSONObject.fromObject(ArrayData);
			XmlOrJson = jsonObjectFromMap.toString();
		}
		log("====请求明文:" + XmlOrJson);

		/** base64 编码 **/
		String base64str = SecurityUtil.Base64Encode(XmlOrJson);
		base64str=base64str.replaceAll("\r\n", "");//重要 避免出现换行空格符
		/** rsa加密 **/
		String pfxpath = Config.getWebRoot() + "key\\" + Config.getConstants().get("pfx.name");// 商户私钥
		File pfxfile = new File(pfxpath);
		if (!pfxfile.exists()) {
			log("私钥文件不存在！");
			throw new RuntimeException("私钥文件不存在！");
		}
		String pfxpwd =Config.getConstants().get("pfx.pwd");// 私钥密码

		String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);// 加密数据
		log("====加密串:" + data_content);

		/** ============== http 请求==================== **/
		 Map<String, String> headers =new HashMap<>();
		String request_url = Config.getConstants().get("operatorQueryInfoUrl");
		Map<String, Object> params =new HashMap<String,Object>();
		 params.put("member_id", member_id);
		 params.put("terminal_id", terminal_id);
		 params.put("data_type", "json");
		 params.put("data_content", data_content);
		 String PostString = HttpUtils.doPostByForm(request_url, headers,params);
		log("请求返回：" + PostString);

		/** ================返回处理============= **/
		if (PostString.isEmpty()) {// 判断参数是否为空
			log("=====返回数据为空");
			throw new RuntimeException("返回数据为空");
		}
		req.setAttribute("result", PostString);
		req.getRequestDispatcher("/show.jsp").forward(req,resp);


	}

	public void log(String msg) {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\t: " + msg);
	}
}
