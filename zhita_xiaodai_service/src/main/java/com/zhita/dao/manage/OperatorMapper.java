package com.zhita.dao.manage;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhita.model.manage.Operator;

public interface OperatorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Operator record);

    int insertSelective(Operator record);

    Operator selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Operator record);

    int updateByPrimaryKeyWithBLOBs(Operator record);

    int updateByPrimaryKey(Operator record);

	int updateSearch_id(@Param("search_id")String search_id,@Param("userId") String userId);

	int setredIdAndPhone(@Param("reqId")String reqId,@Param("userId") int userId,@Param("phone") String phone,@Param("authentime") String authentime);

	Map<String, Object> getOperator(int userId);

	int updateOperatorJson(@Param("url")String url,@Param("userId") int userId);

	int getuserId(int userId);

	void updatereqId(@Param("userId")int userId,@Param("reqId") String reqId);

	void updateAttestationStatus(@Param("attestationStatus")String attestationStatus,@Param("userId") int userId);

	String getattestationStatus(int id);

	String getoperatorJson(int userId);

	String getphone(int userId);

	int setwhitelistuser(@Param("attestationStatus")String attestationStatus,@Param("userId") int userId,@Param("authentime") String authentime);


}