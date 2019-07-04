package com.zhita.model.manage;

//风控设置管理表
public class ManageControlSettings {
    private Integer id;

    private Integer companyid;//公司id

    private String rmmodlename;//风控模型名

    private String totalscore;//总分段

    private String atrntlfractionalsegment;//机审拒绝不放款分数段

    private String roatnptfractionalsegment;//机审拒绝需人审分数段

    private String airappfractionalsegment;//机审通过分数段

    private String whetheruse;//是否使用（1：是；0：否）

    private String deleted;//假删除（1：删除；0：没删除）

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

	public String getRmmodlename() {
		return rmmodlename;
	}

	public void setRmmodlename(String rmmodlename) {
		this.rmmodlename = rmmodlename;
	}

	public String getTotalscore() {
		return totalscore;
	}

	public void setTotalscore(String totalscore) {
		this.totalscore = totalscore;
	}

	public String getAtrntlfractionalsegment() {
		return atrntlfractionalsegment;
	}

	public void setAtrntlfractionalsegment(String atrntlfractionalsegment) {
		this.atrntlfractionalsegment = atrntlfractionalsegment;
	}

	public String getRoatnptfractionalsegment() {
		return roatnptfractionalsegment;
	}

	public void setRoatnptfractionalsegment(String roatnptfractionalsegment) {
		this.roatnptfractionalsegment = roatnptfractionalsegment;
	}

	public String getAirappfractionalsegment() {
		return airappfractionalsegment;
	}

	public void setAirappfractionalsegment(String airappfractionalsegment) {
		this.airappfractionalsegment = airappfractionalsegment;
	}

	public String getWhetheruse() {
		return whetheruse;
	}

	public void setWhetheruse(String whetheruse) {
		this.whetheruse = whetheruse;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

}