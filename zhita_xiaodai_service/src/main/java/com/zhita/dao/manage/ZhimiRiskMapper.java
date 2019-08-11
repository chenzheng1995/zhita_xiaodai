package com.zhita.dao.manage;

import org.apache.ibatis.annotations.Param;

import com.zhita.model.manage.ZhimiRisk;

public interface ZhimiRiskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZhimiRisk record);

    int insertSelective(ZhimiRisk record);

    ZhimiRisk selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZhimiRisk record);

    int updateByPrimaryKey(ZhimiRisk record);

	void setzhimiRisk(@Param("userId")int userId,@Param("request_id") String request_id,@Param("mobile_1h_cnt") int mobile_1h_cnt,@Param("mobile_3h_cnt") int mobile_3h_cnt,@Param("mobile_12h_cnt") int mobile_12h_cnt,
			@Param("mobile_1d_cnt")int mobile_1d_cnt,@Param("mobile_3d_cnt") int mobile_3d_cnt,@Param("mobile_7d_cnt") int mobile_7d_cnt,@Param("mobile_14d_cnt") int mobile_14d_cnt,@Param("mobile_30d_cnt") int mobile_30d_cnt,
			@Param("mobile_60d_cnt")int mobile_60d_cnt,@Param("idcard_1h_cnt") int idcard_1h_cnt,@Param("idcard_3h_cnt") int idcard_3h_cnt,@Param("idcard_12h_cnt") int idcard_12h_cnt,@Param("idcard_1d_cnt") int idcard_1d_cnt,
			@Param("idcard_3d_cnt")int idcard_3d_cnt,@Param("idcard_7d_cnt") int idcard_7d_cnt,@Param("idcard_14d_cnt") int idcard_14d_cnt,@Param("idcard_30d_cnt") int idcard_30d_cnt,@Param("idcard_60d_cnt") int idcard_60d_cnt);
}