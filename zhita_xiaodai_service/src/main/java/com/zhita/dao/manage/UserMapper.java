package com.zhita.dao.manage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.BlacklistUser;
import com.zhita.model.manage.Operator;
import com.zhita.model.manage.User;
import com.zhita.model.manage.UserAttestation;
import com.zhita.model.manage.UserLikeParameter;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    Integer findphone(@Param("newPhone")String newPhone,@Param("companyId") int companyId);

	int insertUser1(@Param("newPhone")String newPhone,@Param("loginStatus")String loginStatus,@Param("companyId")int companyId,@Param("registeClient")String registeClient,
			@Param("registrationTime")String registrationTime,@Param("merchantId")int merchantId,@Param("useMarket")String useMarket);

	int getId(@Param("newPhone")String newPhone,@Param("companyId") int companyId);

	int updateStatus(@Param("loginStatus")String loginStatus,@Param("newPhone") String newPhone,@Param("companyId") int companyId,@Param("loginTime") String loginTime);

	String getPwd(int id);

	int updatelogOutStatus(@Param("loginStatus")String loginStatus,@Param("userId") int userId,@Param("companyId") String companyId);
	
	//后台管理----用户列表(公司id，page,pagesize,姓名，手机号，注册开始时间，注册结束时间，用户认证状态，银行卡认证状态，运营商认证状态)
	List<User> queryUserList(UserLikeParameter userLikeParameter);
	
	//后台管理----用户列表(公司id，page,pagesize,姓名，手机号，注册开始时间，注册结束时间，用户认证状态，银行卡认证状态，运营商认证状态)——查询数量
	int queryUserListcount(UserLikeParameter userLikeParameter);
	
	//后台管理---添加黑名单(修改当前用户的黑名单状态)
	int upaBlacklistStatus(Integer userid);
	
	//后台管理---通过用户id查询该用户姓名，手机号，身份证号
	BlacklistUser queryByUserid(Integer userid);
	
	//后台管理---解除黑名单(修改当前用户的黑名单状态)
	int upaBlacklistStatus1(Integer userid);
	
	//后台管理---添加黑名单（将当前用户存进黑名单里）
	int addBlacklist(Integer companyId,Integer userId,Integer operator,String operationTime,String blackType);
	
	//后台管理---解除黑名单（修改当前用在户黑名单表里的假删除状态）
	int upaBlacklist(Integer userId);
	
	//后台管理---查询当前用户在用户认证表的信息
	UserAttestation queryUserAttesta(Integer userid);
	
	//后台管理---查询当前用户在银行卡表的信息
	Bankcard queryBankcard(Integer userid);
	
	//后台管理----查询当前用户在运营商表的信息
	Operator queryOperator(Integer userid);

	int updatePwd(@Param("newPhone")String newPhone,@Param("md5Pwd") String md5Pwd,@Param("companyId") int companyId);

	String getMd5pwd(@Param("newPhone")String newPhone,@Param("companyId") int companyId);

	int setPwd(@Param("userId")int userId,@Param("md5Pwd") String md5Pwd);

	void updateScore(@Param("score")int score,@Param("userId") int userId,@Param("shareOfState") String shareOfState);

	int getRiskControlPoints(int userId);


	String getshareOfState(int userId);

	int getdelayTimes(int userId);


	/**
	 * 机审状态用户【包含机审拒绝和机审通过用户】（公司id，page，pagesize,申请编号，姓名，手机号，申请时间开始，申请时间结束）
	 */
	List<User> queryAllUser(UserLikeParameter userLikeParameter);
	/**
	 * 机审状态用户【包含机审拒绝和机审通过用户】（公司id，page，pagesize,申请编号，姓名，手机号，申请时间开始，申请时间结束）——查询数量
	 */
	int queryAllUsercount(UserLikeParameter userLikeParameter);
	
	
	/**
	 * 人审状态用户【包含机审拒绝和机审通过用户】（公司id，page，pagesize,申请编号，姓名，手机号，申请时间开始，申请时间结束）
	 */
	List<User> queryAllUserPeople(UserLikeParameter userLikeParameter);
	/**
	 * 人审状态用户【包含机审拒绝和机审通过用户】（公司id，page，pagesize,申请编号，姓名，手机号，申请时间开始，申请时间结束）——查询数量
	 */
	int queryAllUserPeoplecount(UserLikeParameter userLikeParameter);
	
	/**
	 * 人审通过按钮
	 */
	int updateShareOfState(Integer sysuserid,String operationTime,Integer userid);
	
	/**
	 * 人审不通过按钮
	 */
	int updateShareOfStateNo(Integer sysuserid,String operationTime,Integer userid);
	
	/**
	 * 人审过后状态用户【包括人审不通过和人审通过】（公司id，page,pagesize,申请编号，姓名，手机号，申请时间开始，申请时间结束，审核员id）
	 */
	List<User> queryAllUserPeopleYet(UserLikeParameter userLikeParameter);
	
	/**
	 * 人审过后状态用户【包括人审不通过和人审通过】（公司id，page,pagesize,申请编号，姓名，手机号，申请时间开始，申请时间结束，审核员id）——查询数量
	 */
	int queryAllUserPeopleYetcount(UserLikeParameter userLikeParameter);

	void updateshareOfState(@Param("userId")int userId,@Param("shareOfState") String shareOfState);

}