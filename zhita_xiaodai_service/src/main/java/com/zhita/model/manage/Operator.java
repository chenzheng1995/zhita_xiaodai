package com.zhita.model.manage;

public class Operator {
    private Integer id;

    private Integer userid;

    private String phone;

    private String attestationstatus;

    private String reqid;

    private String searchId;

    private String operatorjson;
    
    private String authentime;//认证时间

    public Operator() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAttestationstatus() {
        return attestationstatus;
    }

    public void setAttestationstatus(String attestationstatus) {
        this.attestationstatus = attestationstatus == null ? null : attestationstatus.trim();
    }

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid == null ? null : reqid.trim();
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId == null ? null : searchId.trim();
    }

    public String getOperatorjson() {
        return operatorjson;
    }

    public void setOperatorjson(String operatorjson) {
        this.operatorjson = operatorjson == null ? null : operatorjson.trim();
    }

	public String getAuthentime() {
		return authentime;
	}

	public void setAuthentime(String authentime) {
		this.authentime = authentime;
	}

	@Override
	public String toString() {
		return "Operator [id=" + id + ", userid=" + userid + ", phone=" + phone + ", attestationstatus="
				+ attestationstatus + ", reqid=" + reqid + ", searchId=" + searchId + ", operatorjson=" + operatorjson
				+ ", authentime=" + authentime + "]";
	}

}