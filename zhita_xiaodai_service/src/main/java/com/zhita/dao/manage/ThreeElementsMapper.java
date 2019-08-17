package com.zhita.dao.manage;

import org.apache.ibatis.annotations.Param;

import com.zhita.model.manage.ThreeElements;

public interface ThreeElementsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ThreeElements record);

    int insertSelective(ThreeElements record);

    ThreeElements selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThreeElements record);

    int updateByPrimaryKey(ThreeElements record);

	int getnum(int userId);

	void setThreeElements(@Param("userId")int userId,@Param("code") String code,@Param("trans_id") String trans_id,@Param("certification_number") int certification_number);

	void updateThreeElements(@Param("userId")int userId,@Param("code") String code,@Param("trans_id") String trans_id,@Param("certification_number") int certification_number);

	int getCertificationnumber(int userId);
}