package com.zhita.model.manage;

import java.math.BigDecimal;

//第三方单价查询表
public class Thirdpricefind {
    private Integer id;
    
    private Integer companyid;//公司id
    
    private String thirdpartytype;//第三方类型

    private String thirdpartyname;//第三方产品名

    private BigDecimal price;//价格

    private String deleted;//假删除（1：删除；0：没删除

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

	public String getThirdpartytype() {
		return thirdpartytype;
	}

	public void setThirdpartytype(String thirdpartytype) {
		this.thirdpartytype = thirdpartytype;
	}

	public String getThirdpartyname() {
		return thirdpartyname;
	}

	public void setThirdpartyname(String thirdpartyname) {
		this.thirdpartyname = thirdpartyname;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

}