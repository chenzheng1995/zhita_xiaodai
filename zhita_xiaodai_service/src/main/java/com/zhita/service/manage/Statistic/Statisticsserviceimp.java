package com.zhita.service.manage.Statistic;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhita.chanpayutil.BaseConstant;
import com.zhita.chanpayutil.BaseParameter;
import com.zhita.chanpayutil.ChanPayUtil;
import com.zhita.dao.manage.BankcardMapper;
import com.zhita.dao.manage.PaymentRecordMapper;
import com.zhita.dao.manage.StatisticsDao;
import com.zhita.dao.manage.ThirdcalltongjiMapper;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Bankdeductions;
import com.zhita.model.manage.BlacklistUser;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.MouthBankName;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Repayment;
import com.zhita.model.manage.Result;
import com.zhita.model.manage.RreturnAuth;
import com.zhita.model.manage.User;
import com.zhita.util.MD5Utils;
import com.zhita.util.PageUtil;
import com.zhita.util.PhoneDeal;
import com.zhita.util.RedisClientUtil;
import com.zhita.util.Timestamps;
import com.zhita.util.TuoMinUtil;
import com.zhita.util.YunTongXunUtil;


@Service
public class Statisticsserviceimp extends BaseParameter implements Statisticsservice {
	
	@Autowired
	private BankcardMapper bankcardMapper;
	
	
	@Autowired
	private StatisticsDao sdao;
	
	
	
	@Autowired
	private PaymentRecordMapper padao;
	
	
	
	@Autowired
	ThirdcalltongjiMapper thirdcalltongjiMapper;
	

	
	/**
	 * BankCommonName   银行卡
	 * AcctNo   卡号。
	 * AcctName  姓名
	 * TransAmt  金额
	 * LiceneceNo  身份证号
	 * Phone  手机号
	 * sys_userId   操作人
	 * deductionproportion  扣款比例
	 * orderNumber   订单ID
	 */
	@Override
	public MouthBankName SendBankcomm(String BankCommonName,String AcctNo,String AcctName,String TransAmt,String LiceneceNo,
			String Phone,Integer sys_userId,Integer deductionproportion,String orderNumber,Integer orderId,Integer userId) {
			Map<String, String> map = this.requestBaseParameter();
			Bankdeductions ban = new Bankdeductions();
			ban.setSys_userId(sys_userId);
			ban.setDeductionproportion(deductionproportion);
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			ban.setDeduction_time(sim.format(new Date()));
			String time = sim.format(new Date());
			ban.setOrderId(orderId);
			ban.setUserId(userId);
			BigDecimal deduction_money = new BigDecimal(TransAmt);
			ban.setDeduction_money(deduction_money);
			sdao.Addbankdeduction(ban);
			
			map.put("TransCode", "T20000"); // 交易码
			map.put("OutTradeNo", orderNumber); // 商户网站唯一订单号
			map.put("CorpAcctNo", ""); // 企业账号（可空）
			map.put("BusinessType", "0"); // 业务类型
			map.put("ProtocolNo", ""); // 协议号（可空）        
			map.put("BankCommonName", BankCommonName); // 通用银行名称
			map.put("AccountType", "00"); // 账户类型
			
			//**************  银行四要素信息需要用真实数据  *******************
			map.put("AcctNo", ChanPayUtil.encrypt(AcctNo,
					BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET)); // 对手人账号
			map.put("AcctName", ChanPayUtil.encrypt(AcctName,
					BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET)); // 对手人账户名称
			map.put("TransAmt", TransAmt);
			map.put("LiceneceType", "01");
			map.put("LiceneceNo", ChanPayUtil.encrypt(LiceneceNo,
					BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
			map.put("Phone", ChanPayUtil.encrypt(Phone,
					BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
			map.put("CorpPushUrl", "http://172.20.11.16");
			map.put("PostScript", "用途");

			String ReturnMoth = ChanPayUtil.sendPost(map, BaseConstant.CHARSET,
					BaseConstant.MERCHANT_PRIVATE_KEY);
			MouthBankName mo = JSON.parseObject(ReturnMoth, MouthBankName.class);
			mo.setTime(time);
			return mo;
		}

	@Override
	public Map<String, Object> AllCollection(Orderdetails order) {
		TuoMinUtil tm = new TuoMinUtil();
		PhoneDeal p = new PhoneDeal();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer totalCount = sdao.OrderCollectionNum(order.getCompanyId());
		PageUtil pages = new PageUtil(order.getPage(),totalCount);
		order.setPage(pages.getPage());
			
			System.out.println(order.getDeferAfterReturntimeStatu_time()+order.getDeferAfterReturntimeEnd_time());
			if(order.getStatu_time() != null && order.getStatu_time() != "" && order.getEnd_time() != "" && order.getEnd_time()!= null){
				try {
					order.setStatu_time(Timestamps.dateToStamp1(order.getStatu_time()));
					order.setEnd_time(Timestamps.dateToStamp1(order.getEnd_time()));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		PhoneDeal pAC = new PhoneDeal();
		if(order.getPhone()!=null){
			if(order.getPhone().length()!=0){
				order.setPhone(pAC.decryption(order.getPhone()));
			}
		}
			
		List<Orderdetails> ordeBank = sdao.AllBanl(order);
		for(int i=0;i<ordeBank.size();i++){
			if(ordeBank.get(i).getDeduction_money()==null){
				ordeBank.get(i).setDeduction_money(new BigDecimal(0));
			}
			ordeBank.get(i).setRealityAccount(ordeBank.get(i).getInterestSum().add(ordeBank.get(i).getRealityAccount()));
			ordeBank.get(i).setOrder_money(ordeBank.get(i).getRealityBorrowMoney().add(ordeBank.get(i).getInterestPenaltySum()));
			ordeBank.get(i).setOrderCreateTime(Timestamps.stampToDate(ordeBank.get(i).getOrderCreateTime()));
			ordeBank.get(i).setDeferAfterReturntime(Timestamps.stampToDate(ordeBank.get(i).getDeferAfterReturntime()));
			ordeBank.get(i).setSurplus_money(ordeBank.get(i).getShouldReapyMoney());
			String phon = p.decryption(ordeBank.get(i).getPhone());
			ordeBank.get(i).setPhone(tm.mobileEncrypt(phon));
		}
		map.put("Orderdetails", ordeBank);
		map.put("PageUtil", pages);
		return map;
	}

	@Override
	public String IDnumber(Integer userId) {
		return sdao.UserAll(userId);
	}

	@Override
	public Integer UpdateBank(Bankdeductions ban) {
		return sdao.UpdateBank(ban);
	}

	@Override
	public Map<String, Object> AllBankdeduData(Bankcard ban) {
		System.out.println();
		if(ban.getStatu_time()!=null && ban.getStatu_time()!="" && ban.getEnd_time()!=null && ban.getEnd_time()!=""){
			try {
				ban.setStatu_time(Timestamps.dateToStamp1(ban.getStatu_time()));
				ban.setEnd_time(Timestamps.dateToStamp1(ban.getEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Integer totalCount = sdao.SelectTotalCount(ban);
		PageUtil pages = new PageUtil(ban.getPage(), totalCount);
		ban.setPage(pages.getPage());
		List<Bankdeductions> bans = sdao.AllBank(ban);
		for(int i=0;i<bans.size();i++){
			bans.get(i).setCompanyId(ban.getCompanyId());
			bans.get(i).setChengNum(sdao.ChenggNum(bans.get(i)));//已选扣款用户总数
			bans.get(i).setShiNum(bans.get(i).getUserNum()-bans.get(i).getChengNum());//扣款失败用户数
			bans.get(i).setDeduction_money(sdao.ChenggMoney(bans.get(i)));//扣款金额
			bans.get(i).setChengMoney(sdao.SelectChengMoney(bans.get(i)));
			bans.get(i).setDeduction_time(Timestamps.stampToDate(bans.get(i).getDeduction_time()));
		}
		map.put("Bankdeduction", bans);
		map.put("pageutil", pages);
		return map;
	}

	@Override
	public Map<String, Object> AllBankdetail(Bankdeductions bank) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Bankdeductions> b = sdao.AllBan(bank);
		map.put("Bankdeduction", b);
		return map;
	}

	@Override
	public Map<String, Object> AllDetails(Bankdeductions bank) {
		List<Bankdeductions> bans = sdao.SelectBankKoukuan(bank);
		for(int i=0;i<bans.size();i++){
			bans.get(i).setCollection_money(bans.get(i).getRealityBorrowMoney().add(bans.get(i).getInterestPenaltySum()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Bankdeduction", bans);
		return map;
	}

	@Override
	public Integer UpdateOrderSurp(Orders o) {
		return sdao.UpdateOrderSuperl(o);
	}

	@Override
	public Integer SelectUserId(Integer userId) {
		return sdao.SelectUserId(userId);
	}

	
	/**
	 * 查询银行卡ID
	 */
	@Override
	public Integer SelectTrxId(Bankcard bank) {
		return sdao.SelectTrxId(bank);
	}
	
	
	
	/**
	 * 添加银行卡
	 */
	@Override
	public Integer AddBankcard(Bankcard bank) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			bank.setAuthentime(Timestamps.dateToStamp1(sim.format(new Date())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return sdao.AddBankcard(bank);
	}
	
	

	/**
	 * 修改认证状态
	 */
	@Override
	public Integer UpdateChanpay(Integer id) {
		return sdao.UpdateStatu(id);
	}

	@Override
	public Integer deleteBank(Integer userId) {
		return sdao.DeleteChan(userId);
	}

	/**
	 * 添加还款记录
	 */
	@Override
	public Integer AddRepayment(Repayment repay) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		repay.setCardnumber(sdao.Cardnumber(repay.getUserId()));
		try {
			repay.setRepaymentDate(Timestamps.dateToStamp1(sim.format(new Date())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		repay.setPaymentbtiao("畅捷支付");
		Orders o = sdao.SelectOrderId(repay.getOrderNumber());
		repay.setOrderid(o.getId());
		System.out.println(repay.getOrderid());
		
		return sdao.AddRepay(repay);
	}

	/**
	 * 修改订单状态
	 */
	@Override
	public Integer UpdateOrders(Orders ord) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			ord.setRealtime(Timestamps.dateToStamp1(sim.format(new Date())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Integer id = sdao.UpdateOrders(ord);//
		if(id != null){
			Integer delaytimes = 0;
			ord.setChenggNum(delaytimes);
			sdao.UpdateUser(ord);
		}else{
			return 0;
		}
		return 200;
	}

	@Override
	public Integer AddDeferred(Deferred defe) {
		Orders o = sdao.SelectOrderId(defe.getOrderNumber());
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			defe.setDeferredTime(Timestamps.dateToStamp(sim.format(new Date())));
			defe.setDeferBeforeReturntime((Long.parseLong(Timestamps.dateToStamp2(defe.getDeferBeforeReturntime())))+86399000+"");
			defe.setDeferAfterReturntime((Long.parseLong(Timestamps.dateToStamp2(defe.getDeferAfterReturntime())))+86399000+"");
		} catch (Exception e) {
			// TODO: handle exception
		}
		defe.setOrderid(o.getId());
		return sdao.AddDeferred(defe);
	}


	/**
	 * 修改延期状态
	 */
	@Override
	public Integer UpdateDefeOrders(Orders ord) {
		Integer num = sdao.SelectUserdelayTimes(ord.getUserId());//获取用户延期次数
		ord = sdao.SelectOrderId(ord.getOrderNumber());//获取订单ID   用户ID  公司ID
		ord.setShouldReturnTime(sdao.DefeDefeAfertime(ord.getId()));//获取延期后应还时间
		sdao.UpdateDefe(sdao.Defeid(ord.getId()));//延期状态
		Integer delaytimes = num+1;
		System.out.println("数据:"+delaytimes);
		ord.setChenggNum(delaytimes);
		Integer updateId = sdao.DefeOrder(ord);
		Integer a = null;
		if(updateId!=null){
			a=sdao.UpdateUser(ord);
		}else{
			a=0;
		}
		return a;
	}

	@Override
	public void SelectId(String orderNumber) {
	Orders	ord = sdao.SelectOrderId(orderNumber);//根据编号查询订单ID
		System.out.println(ord.getCompanyId()+ord.getId()+ord.getUserId());
	}

	@Override
	public Integer SelectReaymentOrderId(String orderNumber) {
		return sdao.SelectRepaymentOrderId(orderNumber);
	}

	@Override
	public Integer UpdateBan(Bankcard bank) {
		return sdao.UpdateBankcard(bank);
	}

	@Override
	public Map<String, Object> RenzhenId(String accountNo,String bankPreMobile,String idCardCode,String name,String bankcardTypeName,Integer userId,
			Integer conpanyId,String appNumber,String code) {
		
		 Map<String, Object> map = new HashMap<String, Object>();
		  Integer banktypeid = bankcardMapper.SelectBankName(bankcardTypeName);
		if(banktypeid!=null){
			
			
			 String host = "https://bankver.market.alicloudapi.com";
			    String path = "/creditop/BankCardQuery/BankCardVerification";
			    String method = "GET";
			    String appcode = "ea779e8386254a6db6b3d4b599dfea44";
			    Map<String, String> headers = new HashMap<String, String>();
			    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
			    headers.put("Authorization", "APPCODE " + appcode);
			    Map<String, String> querys = new HashMap<String, String>();
			    querys.put("accountNo", accountNo);
			    querys.put("bankPreMobile", bankPreMobile);
			    querys.put("idCardCode", idCardCode);
			    querys.put("name", name);
			    
		    
		    RedisClientUtil redis = new RedisClientUtil();

			    
		    
		    if(redis.get("userId"+userId)!=null){
		    	map.put("code", 0);
		    	map.put("Ncode", 0);
		    	map.put("msg", "请勿重复点击");
		    	return map;
		    }else{
			    
	    		
			    try {
			    	/**
			    	* 重要提示如下:
			    	* HttpUtils请从
			    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			    	* 下载
			    	*
			    	* 相应的依赖请参照
			    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			    	*/
			    	//redisClientUtil.set("biaoshi"+userId, biaoshi);
			    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			    	//获取response的body
			    	String a = EntityUtils.toString(response.getEntity());
			    	JSONObject jsonObject=JSONObject.parseObject(a);
			    	System.out.println("数据:"+a);
			    	redis.set("userId"+userId, String.valueOf(userId));
			    	if(a!=null){
			    		if(a.length()!=0){
					    	String msg = jsonObject.getString("message");
					    	String reason = jsonObject.getString("reason");
			    	if(reason.equals("成功")){//等于0是认证成功
			    		Integer num = sdao.SelectUserRenNum(userId);
			    		sdao.UpdateUserBankType(userId);
			    		if(num==null){
			    			num=0;
			    		}
			    		User user = new User();
		    			user.setId(userId);
		    			user.setAuthentication(num+1);
		    			Integer i = sdao.UserAuthenNum(user);//修改用户认证次数
		    			
		    			if(i!=null){
		    				DateFormat format = new SimpleDateFormat("yyyy/M/d");
		    				String result = MD5Utils.getMD5(bankPreMobile + appNumber + format.format(new Date()) + "@xiaodai");
		    				if (result.length() == 31) {
		    					result = 0 + MD5Utils.getMD5(bankPreMobile + appNumber + format.format(new Date()) + "@xiaodai");
		    				}
		    				System.out.println("验证码:"+code+":C:"+result);
		    				if (result.equals(code)) {
		    					YunTongXunUtil yunTongXunUtil = new YunTongXunUtil();
		    					String state = yunTongXunUtil.sendSMS(bankPreMobile);
		    					if("提交成功".equals(state)) {
		    					String thirdtypeid = "1";
		    					String date = System.currentTimeMillis()+"";
		    					thirdcalltongjiMapper.setthirdcalltongji(conpanyId,thirdtypeid,date);
		    					}
		    					map.put("Ncode","2000");
		    					map.put("OriAuthTrxId", 1);
		    					map.put("code","200");
		    					map.put("msg", state);
					    		map.put("desc", "认证成功");
					    		return map;
		    				} else {
		    					redis.delkey("userId"+userId);
		    					map.put("Ncode","405");
		    					map.put("msg", "发送失败");
		    					map.put("Code","405");
		    					return map;
		    				}
		    			}else{
		    				redis.delkey("userId"+userId);
		    				map.put("Ncode","405");
	    					map.put("msg", "更新失败");
	    					map.put("Code","405");
	    					return map;
		    			}
		    			
			    	}else{
			    		
			    		Integer num = sdao.SelectUserRenNum(userId);
			    		if(num==null){
			    			num=0;
			    		}
			    		if(num < 3){
			    			User user = new User();
			    			user.setId(userId);
			    			user.setAuthentication(num+1);
			    			Integer i = sdao.UserAuthenNum(user);//修改用户认证次数
			    			
			    			if(i!=null){
			    				DateFormat format = new SimpleDateFormat("yyyy/M/d");
			    				String result = MD5Utils.getMD5(bankPreMobile + appNumber + format.format(new Date()) + "@xiaodai");
			    				if (result.length() == 31) {
			    					result = 0 + MD5Utils.getMD5(bankPreMobile + appNumber + format.format(new Date()) + "@xiaodai");
			    				}
			    				System.out.println("验证码:"+code+":C:"+result);
			    				if (result.equals(code)) {
			    					YunTongXunUtil yunTongXunUtil = new YunTongXunUtil();
			    					String state = yunTongXunUtil.sendSMS(bankPreMobile);
			    					if("提交成功".equals(state)) {
			    					String thirdtypeid = "1";
			    					String date = System.currentTimeMillis()+"";
			    					thirdcalltongjiMapper.setthirdcalltongji(conpanyId,thirdtypeid,date);
			    					}
			    					map.put("Ncode","2000");
			    					map.put("code","200");
			    					map.put("msg", state);
						    		map.put("desc", "认证成功");
						    		return map;
			    				} else {
			    					map.put("Ncode","405");
			    					redis.delkey("userId"+userId);
			    					map.put("msg", "发送失败");
			    					map.put("Code","405");
			    					return map;
			    				}
			    			}else{
			    				redis.delkey("userId"+userId);
			    				map.put("Ncode","405");
		    					map.put("msg", "更新失败");
		    					map.put("Code","405");
		    					return map;
			    			}
			    		}else{
			    			
			    			int i = sdao.UserBlacklist(userId);
			    			if(i==1){
			    				BlacklistUser banuser = new BlacklistUser();
			    				banuser.setCompanyid(conpanyId);
			    				banuser.setName(name);
			    				banuser.setPhone(bankPreMobile);
			    				banuser.setIdcard(idCardCode);
			    				banuser.setUserid(userId);
			    				SimpleDateFormat sima = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    				try {
			    					banuser.setOperationtime(Timestamps.dateToStamp1(sima.format(new Date())));
								} catch (Exception e) {
									// TODO: handle exception
								}
			    				banuser.setBlackType("5");
			    				banuser.setDeleted("0");
			    				
			    				sdao.Addblacklist_user(banuser);
			    				if(i==1){
				    				map.put("code", 200);
				    				redis.delkey("userId"+userId);
						    		map.put("Ncode", 2000);
						    		map.put("msg", "您重复认证超过三次,您已被拉入黑名单");
						    		return map;
				    			}else{
				    				redis.delkey("userId"+userId);
				    				map.put("code", 0);
						    		map.put("Ncode", 0);
						    		map.put("msg", "拉入失败");
						    		return map;
				    			}
			    			
			    		}
			    			redis.delkey("userId"+userId);
			    		map.put("code", 0);
			    		map.put("Ncode", 0);
			    		map.put("msg", msg);
			    	}
			    	}
			    	}else{
			    		redis.delkey("userId"+userId);
			    			map.put("code", 0);
				    		map.put("Ncode", 0);
				    		map.put("msg", "该卡无法认证,请换张卡");
			    		}
			    		
			    		
			    	}else{
			    		redis.delkey("userId"+userId);
			    		map.put("code", 0);
			    		map.put("Ncode", 0);
			    		map.put("msg", "该卡无法认证,请换张卡");
			    	}
			    } catch (Exception e) {
			    	e.printStackTrace();
			    	redis.delkey("userId"+userId);
			    }
			    
		    } 
			
		}else{
			map.put("code", "0");
			map.put("Ncode", "0");
			map.put("msg", "该卡不在放款范围");
		}
	   
		
	    return map;
	}
		
		

	
	
	
	
	
}
