package com.zhita.service.manage.operator;

import java.util.Map;

public interface OperatorService {


	int setredIdAndPhone(String reqId, int userId, String phone, String authentime);

	int updateSearch_id(String search_id, String userId);

	Map<String, Object> getOperator(int userId);

	int updateOperatorJson(String url, int userId);

	int getuserId(int userId);

	void updatereqId(int userId, String reqId, String authentime);

	void updateAttestationStatus(String attestationStatus, int userId);

	String getattestationStatus(int id);

	String getoperatorJson(int userId);

	String getphone(int userId);

	int setwhitelistuser(String attestationStatus, int userId, String authentime);

	String getauthentime(int userId);

	int setoperator(int userId, String authentime);

	void updateoperator(int userId, String authentime);

}
