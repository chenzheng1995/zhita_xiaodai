package com.zhita.controller.bankcard;

public class Result {
	
	private String bank_card_number;//银行卡号
	
	
	private String valid_date;
	
	
	private Integer bank_card_type;

	
	private String bank_name;//开户行
	
	
	private String bank_full_name;
	
	
	private String card_type;


	public String getBank_card_number() {
		return bank_card_number;
	}


	public void setBank_card_number(String bank_card_number) {
		this.bank_card_number = bank_card_number;
	}


	public String getValid_date() {
		return valid_date;
	}


	public void setValid_date(String valid_date) {
		this.valid_date = valid_date;
	}


	public Integer getBank_card_type() {
		return bank_card_type;
	}


	public void setBank_card_type(Integer bank_card_type) {
		this.bank_card_type = bank_card_type;
	}


	public String getBank_name() {
		return bank_name;
	}


	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}


	public String getBank_full_name() {
		return bank_full_name;
	}


	public void setBank_full_name(String bank_full_name) {
		this.bank_full_name = bank_full_name;
	}


	public String getCard_type() {
		return card_type;
	}


	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	
	
}
