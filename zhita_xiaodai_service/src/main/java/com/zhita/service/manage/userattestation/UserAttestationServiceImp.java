package com.zhita.service.manage.userattestation;

import java.util.Map;

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

	@Override
	public void updateBizToken(String biz_token, int userId) {
		userAttestationMapper.updateBizToken(biz_token, userId);
		
	}

	@Override
	public Map<String, Object> getuserAttestation(int userId) {
		Map<String, Object> userAttestation = userAttestationMapper.getuserAttestation(userId);
		return userAttestation;
	}

	@Override
	public int updateUserAttestation(String name, String gender, String nationality, String birth_year,
			String birth_month, String birth_day, String address, String issued_by, String valid_date_start,
			String valid_date_end, String frontsidePath, String backsidePath, int userId,String idcard_number) {
		int number = userAttestationMapper.updateUserAttestation(name, gender, nationality, birth_year, birth_month, birth_day, address,issued_by,valid_date_start,valid_date_end,frontsidePath,backsidePath,userId,idcard_number);
		return number;
	}

	@Override
	public Map<String, Object> selectUserAttestationService(int userId) {
		Map<String, Object> userAttestation = userAttestationMapper.selectUserAttestationService(userId);
		return userAttestation;
	}

	@Override
	public void updateFaceBizToken(String biz_token, int userId) {
		userAttestationMapper.updateFaceBizToken(biz_token, userId);
		
	}

	@Override
	public int setAddress(String homeAddressLongitude, String homeAddressLatitude, String detailAddress,int userId) {
   	 int number = userAttestationMapper.setAddress(homeAddressLongitude,homeAddressLatitude,detailAddress,userId);
		return number;
	}

	@Override
	public int setlinkman(String linkmanOneRelation, String linkmanOneName, String linkmanOnePhone,
			String linkmanTwoRelation, String linkmanTwoName, String linkmanTwoPhone, int userId) {
		int number = userAttestationMapper.setlinkman(linkmanOneRelation,linkmanOneName,linkmanOnePhone,linkmanTwoRelation,linkmanTwoName,linkmanTwoPhone,userId);
		return number;
	}

	@Override
	public void updateAttestationStatus(String attestationStatus, int userId) {
		userAttestationMapper.updateAttestationStatus(attestationStatus,userId);
		
	}


	
	


	
}