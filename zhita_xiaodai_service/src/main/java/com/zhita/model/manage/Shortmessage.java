package com.zhita.model.manage;

import java.util.List;



//短信记录
public class Shortmessage {
	
	
	private Integer id;
	
	private String send_time;//发送时间
	
	private String phonenumber;//手机号
	
	private Integer phonenum;//发送数量
	
	private Integer companyid;
	
	private String smg;//内容
	
	private String collection_time;//逾期前应还时间
	
	private Integer successnum;
	
	private Integer page=0;
	
	private Integer pagesize=10;
	
	private Integer shortmessagesize;
	
	private List<String> phonesa;

	public Integer getShortmessagesize() {
		return shortmessagesize;
	}

	public void setShortmessagesize(Integer shortmessagesize) {
		this.shortmessagesize = shortmessagesize;
	}

	public List<String> getPhonesa() {
		return phonesa;
	}

	public void setPhonesa(List<String> phonesa) {
		this.phonesa = phonesa;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public Integer getSuccessnum() {
		return successnum;
	}

	public void setSuccessnum(Integer successnum) {
		this.successnum = successnum;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSend_time() {
		return send_time;
	}

	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public Integer getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(Integer phonenum) {
		this.phonenum = phonenum;
	}

	public Integer getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public String getSmg() {
		return smg;
	}

	public void setSmg(String smg) {
		this.smg = smg;
	}

	public String getCollection_time() {
		return collection_time;
	}

	public void setCollection_time(String collection_time) {
		this.collection_time = collection_time;
	}
	
	
}
