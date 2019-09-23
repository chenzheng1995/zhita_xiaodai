package com.zhita.model.manage;

//第三方接口配置表
public class ThirdpartyInterface {
    private Integer id;

    private Integer companyid;//公司id

    private String idcardfaceauthentication;//身份证及人脸认证

    private String operatorsauthentication;//运营商认证

    private String loansource;//放款渠道

    private String repaymentsource;//还款渠道
    
    private String phonethreeelements;//手机三要素
    
    private String bankfourelements;//银行卡四要素

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public String getIdcardfaceauthentication() {
		return idcardfaceauthentication;
	}

	public void setIdcardfaceauthentication(String idcardfaceauthentication) {
		this.idcardfaceauthentication = idcardfaceauthentication;
	}

	public String getOperatorsauthentication() {
		return operatorsauthentication;
	}

	public void setOperatorsauthentication(String operatorsauthentication) {
		this.operatorsauthentication = operatorsauthentication;
	}

	public String getLoansource() {
		return loansource;
	}

	public void setLoansource(String loansource) {
		this.loansource = loansource;
	}

	public String getRepaymentsource() {
		return repaymentsource;
	}

	public void setRepaymentsource(String repaymentsource) {
		this.repaymentsource = repaymentsource;
	}

	public String getPhonethreeelements() {
		return phonethreeelements;
	}

	public void setPhonethreeelements(String phonethreeelements) {
		this.phonethreeelements = phonethreeelements;
	}

	public String getBankfourelements() {
		return bankfourelements;
	}

	public void setBankfourelements(String bankfourelements) {
		this.bankfourelements = bankfourelements;
	}
	
}