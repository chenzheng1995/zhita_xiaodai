package com.zhita.service.manage.operator;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.OperatorMapper;

@Service
public class OperatorServiceimp implements OperatorService{
	
	@Autowired 
	OperatorMapper operatorMapper;

	@Override
	public int updateSearch_id(String search_id, String userId) {
		 int number = operatorMapper.updateSearch_id(search_id,userId);
		return number;
	}

	@Override
	public int setredIdAndPhone(String reqId, int userId, String phone,String authentime) {
		int number = operatorMapper.setredIdAndPhone(reqId,userId,phone,authentime);
		return number;
	}

	@Override
	public Map<String, Object> getOperator(int userId) {
		Map<String, Object> operator = operatorMapper.getOperator(userId);
		return operator;
	}

	@Override
	public int updateOperatorJson(String url,int userId) {
	int	number = operatorMapper.updateOperatorJson(url,userId);
		return number;
	}

	@Override
	public int getuserId(int userId) {
		int num = operatorMapper.getuserId(userId);
		return num;
	}

	@Override
	public void updatereqId(int userId, String reqId) {
		operatorMapper.updatereqId(userId,reqId);
		
	}

	@Override
	public void updateAttestationStatus(String attestationStatus, int userId) {
		operatorMapper.updateAttestationStatus(attestationStatus,userId);
		
	}

	@Override
	public String getattestationStatus(int id) {
		String attestationStatus = operatorMapper.getattestationStatus(id);
		return attestationStatus;
	}

	@Override
	public String getoperatorJson(int userId) {
		String fileContent = operatorMapper.getoperatorJson(userId);
		return fileContent;
	}

	@Override
	public String getphone(int userId) {
		String phone1 = operatorMapper.getphone(userId);
		return phone1;
	}

	@Override
	public int setwhitelistuser(String attestationStatus, int userId, String authentime) {
		int number = operatorMapper.setwhitelistuser(attestationStatus,userId,authentime);
		return number;
	}

}
