package com.zhita.dao.manage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhita.model.manage.Source;
import com.zhita.model.manage.TongjiSorce;

public interface SourceMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加功能（判断该渠道名是否在渠道表存在    其假删除状态是1）
    int ifSourceNameIfExist(String sourceName);
    
    //后台管理---添加功能（该渠道在渠道表已经存在   只需做修改）
    int updateSource(Source source);
    
    //后台管理---添加功能
    int insert(Source record);

    int insertSelective(Source record);
    
    //后台管理---根据id查询当前对象信息
    Source selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Source record);
    
    //后台管理---编辑功能
    int updateByPrimaryKey(Source record);
    
    //后台管理---查询渠道表所有信息
    List<Source> queryAll(Integer comapnyId);
    
    //后台管理---根据id  对当前对象的假删除状态进行修改
    int updateFalDel(Integer id);
    
    //后台管理---查询当天各个渠道在用户表的注册数量
    List<TongjiSorce> queryAllSourceByUser(Integer companyid,String StartTime,String EndTime);
    
    //后台管理---查询当天各个渠道在用户表的注册数量(通过渠道查询)
    List<TongjiSorce> queryAllSourceBySouce(@Param("companyid") Integer companyid,@Param("StartTime")String StartTime,
    		@Param("EndTime")String EndTime,@Param("sourceid")Integer sourceid);
    
    //后台管理---查询某一天某个渠道的注册数量
    TongjiSorce queryAllSourceByUserDetail(Integer companyid,String StartTime,String EndTime,Integer sourceid);
    
    //后台管理---通过渠道名称查询出当前渠道的折扣率
    String queryDiscount(String sourceName,Integer companyId);

	int getsourceId(String sourceId);
    
    //后台管理  -----查询当前渠道在user表的所有注册时间
    List<String> queryTime(Integer companyId,String sourceName);
    
    //后台管理 ------查询统计申请数 
    int queryApplicationNumber(Integer companyId,String sourceName,String startTime,String endTime);
    
    //后台管理---根据渠道id查询渠道的折扣率
    String queryDiscountById(Integer sourceId);
    
    //后台管理---查询当前渠道下有多少用户是登录过得
    int queryCount(Integer sourceid,String startTime,String endTime);
    
    //后台管理---当前渠道下所有的用户id
    List<Integer> queryUserid(Integer sourceid);
    
    //后台管理---查询当前用户id是否在个人信息认证表有值
    int queryIfExist(Integer userid,String startTime,String endTime);
    
    //后台管理---查询当前用户id是否在银行卡表有值
    int queryIfExist1(Integer userid,String startTime,String endTime);
    
    //后台管理---查询当前用户id是否在运营商表有值
    int queryIfExist2(Integer userid,String startTime,String endTime);
    
    //后台管理---查询当前渠道所使用的风控       机审风控分数段的值
    String querymancon(String sourceName);

	int getmanageControlId(String sourceName);
	
	//后台管理----通过人数（包含机审通过和人审通过）
	int  querypass(Integer sourceid,String starttime,String endtime);
	
	//后台管理----已借款人数
	int queryorderpass(Integer sourceid,String starttime,String endtime);
	
	//后台管理---渠道统计模块——申请人数字段
	int queryNum(Integer companyId,Integer sourceid,String starttime,String endtime);

	List<String> getstateAndDeleted(@Param("companyId")int companyId,@Param("sourceName") String sourceName);

	List<String> getDeleted(@Param("companyId")int companyId,@Param("sourceName") String sourceName);
    
}