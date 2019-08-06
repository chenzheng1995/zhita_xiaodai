package com.zhita.dao.manage;

import com.zhita.model.manage.Company;

public interface CompanyMapper {
    int deleteByPrimaryKey(Integer companyid);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Integer companyid);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);

	int getCompanyId(String company);
}