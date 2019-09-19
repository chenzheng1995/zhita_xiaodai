package com.zhita.dao.manage;

import org.apache.ibatis.annotations.Param;

import com.zhita.model.manage.Thirdcalltongji;

public interface ThirdcalltongjiMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Thirdcalltongji record);

    int insertSelective(Thirdcalltongji record);

    Thirdcalltongji selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Thirdcalltongji record);

    int updateByPrimaryKey(Thirdcalltongji record);

	void setthirdcalltongji(@Param("companyId") int companyId,@Param("thirdtypeid")  String thirdtypeid,@Param("date")  String date);

	void setthirdcalltongji2(@Param("companyId") int companyId,@Param("thirdtypeid")  String thirdtypeid,@Param("date")  String date,@Param("userId") int userId);
}