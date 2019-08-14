package com.zhita.service.manage.editagreement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.EditAgreementMapper;
import com.zhita.model.manage.AgreementType;
import com.zhita.model.manage.EditAgreement;

@Service
public class EditagreementServiceImp implements IntEditagreementService{
	@Autowired
	private EditAgreementMapper editAgreementMapper;
	
	//后台管理----查询协议分类表所有的协议
    public List<AgreementType> queryAll(Integer companyId){
    	List<AgreementType> list=editAgreementMapper.queryAll(companyId);
    	return list;
    }
    
    //后台管理----根据agreementType（协议分类）查询
    public EditAgreement selectByPrimaryKey(Integer typeid){
    	EditAgreement editAgreement=editAgreementMapper.selectByPrimaryKey(typeid);
    	return editAgreement;
    }
    
    //后台管理----更新保存
    public  int updateByPrimaryKeyWithBLOBs(EditAgreement record){
    	int num=editAgreementMapper.updateByPrimaryKeyWithBLOBs(record);
    	return num;
    }

	@Override
	public String getagreementContent(int agreementId) {
		String agreementContent = editAgreementMapper.getagreementContent(agreementId);
		return agreementContent;
	}
}
