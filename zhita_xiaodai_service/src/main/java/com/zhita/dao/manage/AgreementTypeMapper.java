package com.zhita.dao.manage;

import org.apache.ibatis.annotations.Param;

public interface AgreementTypeMapper {

	int getagreementId(@Param("companyId")int companyId,@Param("agreementName") String agreementName);

}
