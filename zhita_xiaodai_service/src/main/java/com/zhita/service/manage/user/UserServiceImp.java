package com.zhita.service.manage.user;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.OrdersMapper;
import com.zhita.dao.manage.UserMapper;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.DeferredAndOrder;
import com.zhita.model.manage.Operator;
import com.zhita.model.manage.OrderQueryParameter;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Source;
import com.zhita.model.manage.User;
import com.zhita.model.manage.UserAttestation;
import com.zhita.util.DateListUtil;
import com.zhita.util.ListPageUtil;
import com.zhita.util.PageUtil;
import com.zhita.util.Timestamps;

@Service
public class UserServiceImp implements IntUserService{
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private OrdersMapper ordersMapper;
	
	//后台管理----用户列表(公司id，page,姓名，手机号，注册开始时间，注册结束时间，用户认证状态，银行卡认证状态，运营商认证状态)
	public Map<String, Object> queryUserList(Integer companyId,Integer page,String name,String phone,String registeTimeStart,String registeTimeEnd,String userattestationstatus,String bankattestationstatus,String operaattestationstatus){
		if((registeTimeStart!=null&&!"".equals(registeTimeStart))&&(registeTimeEnd!=null&&!"".equals(registeTimeEnd))){
			try {
				registeTimeStart=Timestamps.dateToStamp(registeTimeStart);
				registeTimeEnd=(Long.parseLong(Timestamps.dateToStamp(registeTimeEnd))+86400000)+"";
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		List<User> list=new ArrayList<>();
		List<User> listto=new ArrayList<>();
		PageUtil pageUtil=null;
		list=userMapper.queryUserList(companyId, name, phone,registeTimeStart, registeTimeEnd, userattestationstatus, bankattestationstatus, operaattestationstatus);
		
		if(list!=null && !list.isEmpty()){
			System.out.println("if");
			ListPageUtil listPageUtil=new ListPageUtil(list,page,10);
    		listto.addAll(listPageUtil.getData());
    		
    		for (int i = 0; i < listto.size(); i++) {
        		listto.get(i).setRegistetime(Timestamps.stampToDate(listto.get(i).getRegistetime()));
    		}
    		pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
		}else{
			System.out.println("else");
			pageUtil=new PageUtil(1, 10,0);
		}
		
		HashMap<String,Object> map=new HashMap<>();
		map.put("userlist", listto);
		map.put("pageutil", pageUtil);
    	return map;
	}
	
	//后台管理---添加黑名单
	public int insertBlacklist(Integer companyId,Integer userId,Integer operator){
		userMapper.upaBlacklistStatus(userId);
		String operationTime=System.currentTimeMillis()+"";//获取当前时间戳
		int num=userMapper.addBlacklist(companyId, userId, operator, operationTime);
		return num;
	}
	
	//后台管理---解除黑名单
	public int removeBlacklist(Integer companyId,Integer userId){
		userMapper.upaBlacklistStatus1(userId);
		int num=userMapper.upaBlacklist(userId);
		return num;
	}
	
	//后台管理----订单 查询（公司id，page,订单号，姓名，手机号，注册开始时间，注册结束时间     渠道id  用户id）-用户认证信息——借款信息
  	public Map<String,Object> queryAllOrdersByUserid(OrderQueryParameter orderQueryParameter){
  		if((orderQueryParameter.getRegistestarttime()!=null&&!"".equals(orderQueryParameter.getRegistestarttime()))&&(orderQueryParameter.getRegisteendtime()!=null&&!"".equals(orderQueryParameter.getRegisteendtime()))){
			try {
				orderQueryParameter.setRegistestarttime(Timestamps.dateToStamp(orderQueryParameter.getRegistestarttime()));
				orderQueryParameter.setRegisteendtime((Long.parseLong(Timestamps.dateToStamp(orderQueryParameter.getRegisteendtime()))+86400000)+"");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
  		Integer companyId=orderQueryParameter.getCompanyid();//得到公司id
  		Integer page=orderQueryParameter.getPage();//得到page
  		List<Orders> list=new ArrayList<>();
		List<Orders> listto=new ArrayList<>();
		PageUtil pageUtil=null;
		list=ordersMapper.queryAllOrdersByUserid(orderQueryParameter);
		
		if(list!=null && !list.isEmpty()){
	    	ListPageUtil listPageUtil=new ListPageUtil(list,page,10);
	    	listto.addAll(listPageUtil.getData());
	    		
	    	for (int i = 0; i < listto.size(); i++) {
		    	listto.get(i).setOrderCreateTime(Timestamps.stampToDate(listto.get(i).getOrderCreateTime()));
				List<DeferredAndOrder> listdefer=ordersMapper.queryDefer(listto.get(i).getId());
				if(listdefer.size()!=0){
					listto.get(i).setDeferrTime(listdefer.size());
					listto.get(i).setDeferAfterReturntime(listdefer.get(listdefer.size()-1).getDeferAfterReturntime());
				}
			}
		    DateListUtil.ListSort2(listto);
	    
	    	pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
	    }else{
	    	pageUtil=new PageUtil(1, 10,0);
	    }
	    
	    
	    
	    List<Source> listsource=ordersMapper.querysource(companyId);	    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("listorderto", listto);
		map.put("sourcelist", listsource);
		map.put("pageutil", pageUtil);
		return map;
		
  	}
  	
	//后台管理----订单 查询（公司id  page，姓名，手机号，身份证号，注册开始时间，注册结束时间     渠道id）——黑名单用户  机审判定黑名单
  	public Map<String,Object> queryAllOrdersByUserid1(OrderQueryParameter orderQueryParameter){
  		if((orderQueryParameter.getRegistestarttime()!=null&&!"".equals(orderQueryParameter.getRegistestarttime()))&&(orderQueryParameter.getRegisteendtime()!=null&&!"".equals(orderQueryParameter.getRegisteendtime()))){
			try {
				orderQueryParameter.setRegistestarttime(Timestamps.dateToStamp(orderQueryParameter.getRegistestarttime()));
				orderQueryParameter.setRegisteendtime((Long.parseLong(Timestamps.dateToStamp(orderQueryParameter.getRegisteendtime()))+86400000)+"");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
  		Integer page=orderQueryParameter.getPage();
  		List<Orders> list=new ArrayList<>();
		List<Orders> listto=new ArrayList<>();
		PageUtil pageUtil=null;
		list=ordersMapper.queryAllOrdersByUserid1(orderQueryParameter);
		
		
	    if(list!=null && !list.isEmpty()){
	    	ListPageUtil listPageUtil=new ListPageUtil(list,page,10);
	    	listto.addAll(listPageUtil.getData());
	    	
	    	for (int i = 0; i <listto.size(); i++) {
				listto.get(i).setOrderCreateTime(Timestamps.stampToDate(listto.get(i).getOrderCreateTime()));
			}
			
			DateListUtil.ListSort2(listto);
	    		
	    	pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
	    }else{
	    	pageUtil=new PageUtil(1, 10,0);
	    }
	    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("listorderto", listto);
		map.put("pageutil", pageUtil);
		return map;
  	}
  	
	//后台管理---用户认证信息
	public Map<String,Object> queryUserAttesta(Integer userid){
		UserAttestation userAttestation=userMapper.queryUserAttesta(userid);//用户认证信息
		Bankcard bankcard=userMapper.queryBankcard(userid);//用户银行卡信息
		Operator operator=userMapper.queryOperator(userid);//运营商信息
		System.out.println(operator+"-------");
		//String json=operator.getOperatorjson();//取出里面的json串
		
		//System.out.println("------"+operator.getOperatorjson()+"*****");
		//String operatorjson="\""+json.substring(6)+"\"";//只取出json串里   去掉"运营商链接:"后面的内容
		
		
		//JsonBean jsonBean=JSON.parseObject(JSON.parse(operatorjson),JsonBean.class);
		/*List<DatalistJsonBean> jsondata=jsonBean.getWd_api_mobilephone_getdatav2_response().getData().getData_list();
		DatalistJsonBean datalistjsonbean=jsondata.get(0);
		List<TelDataJsonBean> listTel=datalistjsonbean.getTeldata();//通话记录信息
		List<MsgDataJsonBean> listmsg=datalistjsonbean.getMsgdata();//短信记录信息
*/		
		
		///--------------------
		
		HashMap<String,Object> map=new HashMap<>();
		map.put("userAttestation", userAttestation);
		map.put("bankcard",bankcard );
		//map.put("listTel",listTel );
		//map.put("listmsg",listmsg );
		return map;
	}

	@Override
	public void updateScore(int score,int userId) {
		userMapper.updateScore(score,userId);
		
	}

	@Override
	public int getRiskControlPoints(int userId) {
		 int riskControlPoints = userMapper.getRiskControlPoints(userId);
		return riskControlPoints;
	}
}
