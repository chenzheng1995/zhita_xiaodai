package com.zhita.dao.manage;

import com.zhita.model.manage.Thirdparty_interface;

public interface NewMapper {
	
	//查询放款名称  和  还款名称
	Thirdparty_interface NewloanRepayment(Integer companyId);

}
