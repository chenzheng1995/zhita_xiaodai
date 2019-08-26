package com.zhita.dao.manage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


import com.zhita.model.manage.UserAttestation;

public interface UserAttestationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAttestation record);

    int insertSelective(UserAttestation record);

    UserAttestation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAttestation record);

    int updateByPrimaryKey(UserAttestation record);

	int insertSign(@Param("sign") String sign,@Param("userId") int userId);

	String selectSign(int userId);

	void updateSign(@Param("sign")String sign,@Param("userId") int userId);

	void updateBizToken(@Param("biz_token")String biz_token,@Param("userId") int userId);

	Map<String, Object> getuserAttestation(int userId);

	int updateUserAttestation(@Param("name")String name,@Param("gender") String gender,@Param("nationality") String nationality,@Param("birth_year") String birth_year,
			@Param("birth_month")String birth_month,@Param("birth_day")String birth_day,@Param("address") String address,@Param("issued_by") String issued_by,
			@Param("valid_date_start")String valid_date_start,@Param("valid_date_end") String valid_date_end,@Param("frontsidePath")String frontsidePath,
			@Param("backsidePath")String backsidePath,@Param("userId") int userId,@Param("idcard_number") String idcard_number,@Param("homeAddressLongitude") String homeAddressLongitude,@Param("homeAddressLatitude") String homeAddressLatitude,@Param("detailAddress") String detailAddress,@Param("headossPath") String headossPath,@Param("nationalEmblemossPath") String nationalEmblemossPath,@Param("authenticationSteps") String authenticationSteps);

	Map<String, Object> selectUserAttestationService(int userId);

	void updateFaceBizToken(@Param("biz_token")String biz_token,@Param("userId") int userId);

	int setAddress(@Param("homeAddressLongitude")String homeAddressLongitude,@Param("homeAddressLatitude") String homeAddressLatitude,@Param("detailAddress") String detailAddress,@Param("userId") int userId);

	int setlinkman(@Param("linkmanOneRelation")String linkmanOneRelation,@Param("linkmanOneName") String linkmanOneName,@Param("linkmanOnePhone") String linkmanOnePhone,
			@Param("linkmanTwoRelation")String linkmanTwoRelation,@Param("linkmanTwoName")String linkmanTwoName,@Param("linkmanTwoPhone") String linkmanTwoPhone,@Param("userId") int userId);

	void updateAttestationStatus(@Param("attestationStatus")String attestationStatus,@Param("userId") int userId,@Param("authenticationSteps")String authenticationSteps);

	int updateAuthenticationSteps(@Param("userId")int userId,@Param("authenticationSteps") String authenticationSteps);

	String getauthenticationSteps(int userId);

	int insertUserAttestation(@Param("name")String name,@Param("gender") String gender,@Param("nationality") String nationality,@Param("birth_year") String birth_year,
			@Param("birth_month")String birth_month,@Param("birth_day")String birth_day,@Param("address") String address,@Param("issued_by") String issued_by,
			@Param("valid_date_start")String valid_date_start,@Param("valid_date_end") String valid_date_end,@Param("frontsidePath")String frontsidePath,
			@Param("backsidePath")String backsidePath,@Param("userId") int userId,@Param("idcard_number") String idcard_number,@Param("homeAddressLongitude") String homeAddressLongitude,@Param("homeAddressLatitude") String homeAddressLatitude,@Param("detailAddress") String detailAddress,@Param("authenticationSteps") String authenticationSteps);

	String getshareOfState(int userId);

	List<Integer> getuserId(String idCard);

	int updatefacePhoto(@Param("userId")int userId,@Param("facePhotoPath") String facePhotoPath);

}