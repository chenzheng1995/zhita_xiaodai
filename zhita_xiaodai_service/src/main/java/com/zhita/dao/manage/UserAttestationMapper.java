package com.zhita.dao.manage;

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
}