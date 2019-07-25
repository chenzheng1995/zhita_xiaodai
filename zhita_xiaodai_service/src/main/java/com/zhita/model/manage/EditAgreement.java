package com.zhita.model.manage;

public class EditAgreement {
    private Integer id;
    
    private String agreementtypeid;//协议分类id

    private String agreementcontent;//协议内容

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAgreementtypeid() {
		return agreementtypeid;
	}

	public void setAgreementtypeid(String agreementtypeid) {
		this.agreementtypeid = agreementtypeid;
	}

	public String getAgreementcontent() {
		return agreementcontent;
	}

	public void setAgreementcontent(String agreementcontent) {
		this.agreementcontent = agreementcontent;
	}

	

   
}