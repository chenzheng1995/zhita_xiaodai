package com.zhita.service.manage.projecttimer;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhita.dao.manage.BlacklistUserMapper;
import com.zhita.dao.manage.ProjecttimerMapper;
import com.zhita.dao.manage.UserMapper;
import com.zhita.model.manage.BlacklistUser;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.OverdueClass;
import com.zhita.model.manage.OverdueSettings;
import com.zhita.util.DateListUtil;
import com.zhita.util.PhoneDeal;
import com.zhita.util.Timestamps;

@Service
public class ProjecttimerServiceImp implements IntProjecttimerService{
	@Autowired
	private ProjecttimerMapper projecttimerMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private BlacklistUserMapper blacklistUserMapper;
	
	//后台管理----查询订单表     所有期限中，已逾期，已延期的订单(定时任务     控制逾期)
	@Transactional
	public void selAllover(){
		Integer companyId = 3;
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		String date = sdf.format(d);//当天时间（年月日时分秒格式）
		
		List<Orders> list=projecttimerMapper.queryAllover(companyId);
		for (int i = 0; i < list.size(); i++) {
			String shouldReturnTime = Timestamps.stampToDate(list.get(i).getShouldReturnTime());//应还时间（String类型）
			try {
				if(sdf.parse(date).getTime()>sdf.parse(shouldReturnTime).getTime()){//转成long类型比较
					Integer orderid = list.get(i).getId();//订单id
					projecttimerMapper.updateover(orderid);//修改订单状态为逾期
					
					DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
					String shouldReturnTime1 = Timestamps.stampToDate1(list.get(i).getShouldReturnTime());//应还时间（String类型）
					Date shoulddate = sdf1.parse(shouldReturnTime1);//应还时间（date类型）
					Date d1 = new Date();
					Integer overdueNumberOfDays = DateListUtil.differentDaysByMillisecond(shoulddate,d1);//逾期天数****
					
					
					List<OverdueSettings> listover = projecttimerMapper.queryAllOversett(companyId);//查询逾期设置表所有信息
					List<BigDecimal> list1 = new ArrayList<BigDecimal>();
					for (int j = 0; j < listover.size(); j++) {
						if(overdueNumberOfDays<=listover.get(j).getOverduehowmanydaysage()){
							list1.add(listover.get(j).getPenaltyinterestrates());
							break;
						}
					}
					if(list1.isEmpty()||list1.size()==0){
						list1.add(new BigDecimal("0.00"));
					}
					BigDecimal interestPenaltyDay = list1.get(0);//日均罚息（20%  存的就是20）
					
					BigDecimal interestPenaltySumfor=list.get(i).getInterestPenaltySum();//逾期总罚息（叠加的金额）
					if(interestPenaltySumfor==null){
						interestPenaltySumfor=new BigDecimal("0.00");
					}
					
					BigDecimal interestPenaltySum = new BigDecimal("0.00");//逾期总罚息
					if(list.get(i).getRealityBorrowMoney().compareTo(list.get(i).getMakeLoans())==0){//代表是后置模式
						if(String.valueOf(overdueNumberOfDays).equals(list.get(i).getOverdueNumberOfDays())){
							interestPenaltySum = interestPenaltySumfor;
						}else{
							interestPenaltySum = (list.get(i).getRealityBorrowMoney().add(list.get(i).getTechnicalServiceMoney())).multiply(interestPenaltyDay).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP).add(interestPenaltySumfor);
						}
					}else{//代表是前置模式
						if(String.valueOf(overdueNumberOfDays).equals(list.get(i).getOverdueNumberOfDays())){
							interestPenaltySum = interestPenaltySumfor;
						}else{
							interestPenaltySum = list.get(i).getRealityBorrowMoney().multiply(interestPenaltyDay).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP).add(interestPenaltySumfor);
						}
					}
					
					BigDecimal interestInAll = list.get(i).getInterestSum().add(interestPenaltySum);//总利息
					
					projecttimerMapper.upaorderdetail(overdueNumberOfDays, interestPenaltyDay, interestPenaltySum, interestInAll, list.get(i).getId());
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//后台管理----控制逾期等级（定时任务）
	@Transactional
	public void upaoverclass(){
		Integer companyId = 3;
		List<Orders> listord = projecttimerMapper.queryAlloverdue(companyId);
		List<OverdueClass> listover = projecttimerMapper.queryAlloverclass(companyId);
		for (int i = 0; i < listord.size(); i++) {
			Integer overdueNumberOfDays = Integer.parseInt(listord.get(i).getOverdueNumberOfDays());//逾期天数
			for (int j = 0; j < listover.size(); j++) {
				String describes = listover.get(j).getDescribes();//对应逾期天数(区间段)
				String[] describesstr = describes.split("-");
				Integer describesStart = Integer.parseInt(describesstr[0]);
				Integer describesEnd = Integer.parseInt(describesstr[1]);
				if(overdueNumberOfDays>=describesStart&&overdueNumberOfDays<=describesEnd){
					projecttimerMapper.upaoverclass(listover.get(j).getGrade(), listord.get(i).getId());
				}
			}
		}
		
	}
	
	//后台管理----控制逾期超过30天，打入黑名单（定时任务）
	@Transactional
	public void addblack(){
		PhoneDeal pd = new PhoneDeal();//手机号加密解密工具类
		Integer companyId = 3;
		List<Orders> list = projecttimerMapper.queryAlloverdue(companyId);
		int blackline=projecttimerMapper.queryblackline();//黑名单分界线值
		for (int i = 0; i < list.size(); i++) {
			Integer overdueNumberOfDays = Integer.parseInt(list.get(i).getOverdueNumberOfDays());//逾期天数
			if(overdueNumberOfDays>=blackline){
				projecttimerMapper.upaBlacklistStatus(list.get(i).getUserId());//添加黑名单(修改当前用户的黑名单状态)
				String operationTime=System.currentTimeMillis()+"";//获取当前时间戳
				String blackType="1";//黑名单类型（1：逾期自动判定）
				BlacklistUser blacklistUser=userMapper.queryByUserid(list.get(i).getUserId());
				blacklistUser.setPhone(pd.decryption(blacklistUser.getPhone()));
				blacklistUser.setCompanyid(companyId);
				blacklistUser.setOperationtime(operationTime);
				blacklistUser.setBlackType(blackType);
				blacklistUser.setUserid(list.get(i).getUserId());
				blacklistUserMapper.insert(blacklistUser);//将该用户添加进黑名单表
				//projecttimerMapper.addBlacklist(companyId, list.get(i).getUserId(), blackType);
			}
		}
	}
}
