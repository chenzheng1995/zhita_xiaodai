package com.zhita.service.manage.editagreement;

import java.util.List;

import com.zhita.model.manage.AgreementType;
import com.zhita.model.manage.EditAgreement;

public interface IntEditagreementService {
	//后台管理----查询协议分类表所有的协议
    public List<AgreementType> queryAll(Integer companyId);
    
    //后台管理----根据agreementType（协议分类）查询
    public EditAgreement selectByPrimaryKey(Integer typeid);
    
    //后台管理----更新保存
    public  int updateByPrimaryKeyWithBLOBs(EditAgreement record);
}
