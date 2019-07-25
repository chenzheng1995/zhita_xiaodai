package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.AgreementType;
import com.zhita.model.manage.EditAgreement;

public interface EditAgreementMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(EditAgreement record);

    int insertSelective(EditAgreement record);
    
    //后台管理----根据agreementType（协议分类）查询
    EditAgreement selectByPrimaryKey(Integer typeid);

    int updateByPrimaryKeySelective(EditAgreement record);
    
    //后台管理----更新保存
    int updateByPrimaryKeyWithBLOBs(EditAgreement record);

    int updateByPrimaryKey(EditAgreement record);
    
    //后台管理----查询协议分类表所有的协议
    List<AgreementType> queryAll(Integer companyId);
}