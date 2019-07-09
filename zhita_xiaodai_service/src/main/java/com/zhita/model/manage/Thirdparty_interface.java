package com.zhita.model.manage;


//第三方接口配置
public class Thirdparty_interface {
	
	private Integer id;
	
	private Integer companyId;//公司ID
	
	private String idcardFaceAuthentication;//身份证及人脸认证
	
	private String operatorsAuthentication;//运营商认证
	
	private String loanSource;//放款渠道
	
	private String repaymentSource;//还款渠道

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getIdcardFaceAuthentication() {
		return idcardFaceAuthentication;
	}

	public void setIdcardFaceAuthentication(String idcardFaceAuthentication) {
		this.idcardFaceAuthentication = idcardFaceAuthentication;
	}

	public String getOperatorsAuthentication() {
		return operatorsAuthentication;
	}

	public void setOperatorsAuthentication(String operatorsAuthentication) {
		this.operatorsAuthentication = operatorsAuthentication;
	}

	public String getLoanSource() {
		return loanSource;
	}

	public void setLoanSource(String loanSource) {
		this.loanSource = loanSource;
	}

	public String getRepaymentSource() {
		return repaymentSource;
	}

	public void setRepaymentSource(String repaymentSource) {
		this.repaymentSource = repaymentSource;
	}
	
	
	
	
}
