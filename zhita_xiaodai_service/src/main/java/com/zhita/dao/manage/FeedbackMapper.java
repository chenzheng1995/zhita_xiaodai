package com.zhita.dao.manage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhita.model.manage.Feedback;

public interface FeedbackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Feedback record);

    int insertSelective(Feedback record);

    Feedback selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Feedback record);

    int updateByPrimaryKey(Feedback record);

	int setfeedback(@Param("userId")int userId,@Param("advice") String advice,@Param("url1") String url1,@Param("url2") String url2,@Param("url3") String url3,@Param("time") String time);
	
	//后台管理----查询数量
	int queryAllcount();
	
	//后台管理----查询功能
	List<Feedback> queryAll(Integer page,Integer pagesize);


	List<Feedback> getfeedbackRecord(Integer userId);

	
	//后台管理----修改解决状态，添加回复内容
	int upastatus(String replycontent,Integer id);

}