package com.zhita.service.manage.iffengkong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.dao.manage.IffengkongMapper;
import com.zhita.model.manage.Iffengkong;

@Service
public class IffengkongServiceImp implements IntIffengkongService{
	
	@Autowired
	private IffengkongMapper iffengkongMapper;
	
	
	//后台管理——列表查询
	@ResponseBody
	@RequestMapping("/queryAll")
	public Iffengkong queryAll(Integer companyId){
		return iffengkongMapper.queryAll(companyId);
	}
	
	//后台管理——编辑功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(Iffengkong record){
    	return iffengkongMapper.updateByPrimaryKey(record);
    }
}
