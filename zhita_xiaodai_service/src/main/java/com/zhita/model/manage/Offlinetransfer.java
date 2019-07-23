package com.zhita.model.manage;

import java.math.BigDecimal;

//线下调账表
public class Offlinetransfer {
    private Integer id;

    private Integer orderid;//订单id

    private String projectname;//项目名

    private String state;//线下调账状态(收入  支出)

    private String channel;//渠道

    private String number;//流水号

    private BigDecimal money;//减免金额

    private String remarks;//备注

    private String offinetransfertime;//时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname == null ? null : projectname.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getOffinetransfertime() {
        return offinetransfertime;
    }

    public void setOffinetransfertime(String offinetransfertime) {
        this.offinetransfertime = offinetransfertime == null ? null : offinetransfertime.trim();
    }
}