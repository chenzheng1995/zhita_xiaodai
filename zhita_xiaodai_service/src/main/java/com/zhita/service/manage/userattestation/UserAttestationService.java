package com.zhita.service.manage.userattestation;

import java.util.List;
import java.util.Map;



public interface UserAttestationService {

	int insertSign(String sign, int userId);

	String selectSign(int userId);

	void updateSign(String sign, int userId);

	void updateBizToken(String biz_token, int userId);

	Map<String, Object> getuserAttestation(int userId);


	int updateUserAttestation(String name, String gender, String nationality, String birth_year, String birth_month,
			String birth_day, String address, String issued_by, String valid_date_start, String valid_date_end,
			String frontsidePath, String backsidePath, int userId, String idcard_number, String homeAddressLongitude, String homeAddressLatitude, String detailAddress, String headossPath, String nationalEmblemossPath, String authenticationSteps);

	Map<String, Object> selectUserAttestationService(int userId);

	void updateFaceBizToken(String biz_token, int userId);

	int setAddress(String homeAddressLongitude, String homeAddressLatitude, String detailAddress, int userId);

	int setlinkman(String linkmanOneRelation, String linkmanOneName, String linkmanOnePhone, String linkmanTwoRelation,
			String linkmanTwoName, String linkmanTwoPhone, int userId);

	void updateAttestationStatus(String attestationStatus, int userId, String authenticationSteps);

	int updateAuthenticationSteps(int userId, String authenticationSteps);

	String getauthenticationSteps(int userId);

	int insertUserAttestation(String name, String gender, String nationality, String birth_year, String birth_month,
			String birth_day, String address, String issued_by, String valid_date_start, String valid_date_end,
			String frontsidePath, String backsidePath, int userId, String idcard_number, String homeAddressLongitude,
			String homeAddressLatitude, String detailAddress, String nationalEmblemossPath);

	String getshareOfState(int userId);

	List<Integer> getuserId(String idCard);

	int updatefacePhoto(int userId, String facePhotoPath);

	String getidCard(int userId);






}
