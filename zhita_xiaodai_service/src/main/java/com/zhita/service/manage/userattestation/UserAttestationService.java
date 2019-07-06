package com.zhita.service.manage.userattestation;

public interface UserAttestationService {

	int insertSign(String sign, int userId);

	String selectSign(int userId);

	void updateSign(String sign, int userId);

}
