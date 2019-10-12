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
    
    private String crawlerId;
    
    private String crawlerToken;
    
    private String sms_verify_code;

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
	
	public String getCrawlerId() {
		return crawlerId;
	}

	public void setCrawlerId(String crawlerId) {
		this.crawlerId = crawlerId;
	}
	
	public String getCrawlerToken() {
		return crawlerToken;
	}

	public void setCrawlerToken(String crawlerToken) {
		this.crawlerToken = crawlerToken;
	}
	
	public String getSms_verify_code() {
		return sms_verify_code;
	}

	public void setSms_verify_code(String sms_verify_code) {
		this.sms_verify_code = sms_verify_code;
	}

	@Override
	public String toString() {
		return "Operator [id=" + id + ", userid=" + userid + ", phone=" + phone + ", attestationstatus="
				+ attestationstatus + ", reqid=" + reqid + ", searchId=" + searchId + ", operatorjson=" + operatorjson
				+ ", authentime=" + authentime + "]";
	}

}