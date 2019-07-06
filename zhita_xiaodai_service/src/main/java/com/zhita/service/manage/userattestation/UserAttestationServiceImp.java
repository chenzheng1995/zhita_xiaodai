package com.zhita.service.manage.userattestation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.UserAttestationMapper;

@Service
public class UserAttestationServiceImp implements UserAttestationService{

	@Autowired
	UserAttestationMapper userAttestationMapper;

	@Override
	public int insertSign(String sign, int userId) {
		int number = userAttestationMapper.insertSign(sign,userId);
		return number;
	}

	@Override
	public String selectSign(int userId) {
		String sign =userAttestationMapper.selectSign(userId);
		return sign;
	}

	@Override
	public void updateSign(String sign, int userId) {
		userAttestationMapper.updateSign(sign,userId);
		
	}
	
}
