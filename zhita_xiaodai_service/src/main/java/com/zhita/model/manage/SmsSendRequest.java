package com.zhita.model.manage;
/**
 *
 * @author tianyh
 * @Description:普通短信发送实体类
 */
public class SmsSendRequest {
    /**
     * 用户账号，必填
     */
    private String account;
    /**
     * 用户密码，必填
     */
    private String password;
    /**
     * 短信内容。长度不能超过536个字符，必填
     */
    private String msg;
    /**
     * 机号码。多个手机号码使用英文逗号分隔，必填
     */
    private String phone;
    /**
     * 数量
     */
    private Integer phonenum; 
    /**
     * 公司ID
     */
    private Integer companyid;
    
    private Integer biaoshi;//  1  当前时间前一天    2   前两天   3 前三天   0 今天
    
    private String statu_time;//开始时间
    
    private String end_time;//结束时间
    
    private String collection_time;


    public String getCollection_time() {
		return collection_time;
	}
	public void setCollection_time(String collection_time) {
		this.collection_time = collection_time;
	}
	public String getStatu_time() {
		return statu_time;
	}
	public void setStatu_time(String statu_time) {
		this.statu_time = statu_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public Integer getBiaoshi() {
		return biaoshi;
	}
	public void setBiaoshi(Integer biaoshi) {
		this.biaoshi = biaoshi;
	}
	/**
     * 定时发送短信时间。格式为yyyyMMddHHmm，值小于或等于当前时间则立即发送，默认立即发送，选填
     */
    private String sendtime;
    /**
     * 是否需要状态报告（默认false），选填
     */
    private String report;
    /**
     * 下发短信号码扩展码，纯数字，建议1-3位，选填
     */
    private String extend;
    /**
     * 该条短信在您业务系统内的ID，如订单号或者短信发送记录流水号，选填
     */
    private String uid;

    public SmsSendRequest() {

    }
    public SmsSendRequest(String account, String password, String msg, String phone) {
        super();
        this.account = account;
        this.password = password;
        this.msg = msg;
        this.phone = phone;
    }
    public SmsSendRequest(String account, String password, String msg, String phone, String report) {
        super();
        this.account = account;
        this.password = password;
        this.msg = msg;
        this.phone = phone;
        this.report=report;
    }

    public SmsSendRequest(String account, String password, String msg, String phone, String report,String sendtime) {
        super();
        this.account = account;
        this.password = password;
        this.msg = msg;
        this.phone = phone;
        this.sendtime=sendtime;
        this.report=report;
    }
    public SmsSendRequest(String account, String password, String msg, String phone, String sendtime,String report,String uid) {
        super();
        this.account = account;
        this.password = password;
        this.msg = msg;
        this.phone = phone;
        this.sendtime=sendtime;
        this.report=report;
        this.uid=uid;
    }

    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getSendtime() {
        return sendtime;
    }
    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }
    public String getReport() {
        return report;
    }
    public void setReport(String report) {
        this.report = report;
    }
    public String getExtend() {
        return extend;
    }
    public void setExtend(String extend) {
        this.extend = extend;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
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
    
}
