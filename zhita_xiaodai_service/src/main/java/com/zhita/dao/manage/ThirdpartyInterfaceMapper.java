package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.BankFourelements;
import com.zhita.model.manage.FaceRecognition;
import com.zhita.model.manage.LoanSetting;
import com.zhita.model.manage.OperatorSetting;
import com.zhita.model.manage.PhoneThreeelements;
import com.zhita.model.manage.RepaymentSetting;
import com.zhita.model.manage.ThirdpartyInterface;

public interface ThirdpartyInterfaceMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加功能
    int insert(ThirdpartyInterface record);

    int insertSelective(ThirdpartyInterface record);
    
    //后台管理---根据id查询当前对象信息
    ThirdpartyInterface selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThirdpartyInterface record);
    
    //后台管理---编辑保存功能
    int updateByPrimaryKey(ThirdpartyInterface record);
    
    //后台管理---查询出第三方接口配置表所有信息
    List<ThirdpartyInterface> queryAll(Integer companyId);
    
    //后台管理---查询人脸设置表所有信息
    List<FaceRecognition> queryface(Integer companyId);
    
    //后台管理---查询运营商设置表所有信息
    List<OperatorSetting> queryopera(Integer companyId);
    
    //后台管理---查询放款渠道设置表所有信息
    List<LoanSetting> queryloan(Integer companyId);
    
   //后台管理---查询还款渠道设置表所有信息
    List<RepaymentSetting> queryrepayment(Integer companyId);
    
    //后台管理---查询手机三要素表所有信息
    List<PhoneThreeelements> queryphonethree(Integer companyId);
    
    //后台管理---查询银行卡四要素表所有信息
    List<BankFourelements> querybankfour(Integer companyId);

	String getOperatorsAuthentication(int companyId);
}