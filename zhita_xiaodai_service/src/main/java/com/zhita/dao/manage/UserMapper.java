package com.zhita.dao.manage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhita.model.manage.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	User findphone(@Param("newPhone")String newPhone,@Param("companyId") int companyId);

	int insertUser1(@Param("newPhone")String newPhone,@Param("loginStatus")String loginStatus,@Param("companyId")int companyId,@Param("registeClient")String registeClient,
			@Param("registrationTime")String registrationTime,@Param("merchantId")int merchantId,@Param("useMarket")String useMarket);

	int getId(@Param("newPhone")String newPhone,@Param("companyId") int companyId);

	int updateStatus(@Param("loginStatus")String loginStatus,@Param("newPhone") String newPhone,@Param("companyId") int companyId,@Param("loginTime") String loginTime);

	String getPwd(int id);

	int updatelogOutStatus(@Param("loginStatus")String loginStatus,@Param("userId") int userId,@Param("companyId") String companyId);
	
	//后台管理----用户列表(公司id，姓名，注册开始时间，注册结束时间，用户认证状态，银行卡认证状态，运营商认证状态)
	List<User> queryUserList(Integer companyId,String name,String registeTimeStart,String registeTimeEnd,String userattestationstatus,String bankattestationstatus,String operaattestationstatus);
	
	//后台管理---添加黑名单(修改当前用户的黑名单状态)
	int upaBlacklistStatus(Integer userid);
	
	//后台管理---添加黑名单（将当前用户存进黑名单里）
	int addBlacklist(Integer companyId,Integer userId,String operator,String operationTime);

	int updatePwd(@Param("newPhone")String newPhone,@Param("md5Pwd") String md5Pwd,@Param("companyId") int companyId);

	String getMd5pwd(@Param("newPhone")String newPhone,@Param("companyId") int companyId);

	int setPwd(@Param("userId")int userId,@Param("md5Pwd") String md5Pwd);
}