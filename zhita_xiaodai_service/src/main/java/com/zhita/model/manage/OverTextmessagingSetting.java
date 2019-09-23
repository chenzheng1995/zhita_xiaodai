package com.zhita.model.manage;

//逾前短信发送设置表
public class OverTextmessagingSetting {
    private Integer id;

    private Integer companyid;//公司id

    private Integer overdueday;//逾期天数

    private String timing;//时间点
    
    private String templateid;//模板id
    
    private String name;//模板名字
    
    private String content;//模板内容

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

    public Integer getOverdueday() {
        return overdueday;
    }

    public void setOverdueday(Integer overdueday) {
        this.overdueday = overdueday;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing == null ? null : timing.trim();
    }

    public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted == null ? null : deleted.trim();
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
}