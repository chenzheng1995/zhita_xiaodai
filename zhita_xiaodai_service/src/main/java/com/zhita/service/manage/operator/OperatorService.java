package com.zhita.service.manage.operator;

import java.util.Map;

public interface OperatorService {


	int setredIdAndPhone(String reqId, int userId, String phone);

	int updateSearch_id(String search_id, String userId);

	Map<String, Object> getOperator(int userId);

	int updateOperatorJson(String url, int userId);

	int getuserId(int userId);

	void updatereqId(int userId, String reqId);

	void updateAttestationStatus(String attestationStatus, int userId);

	String getattestationStatus(int id);

}
