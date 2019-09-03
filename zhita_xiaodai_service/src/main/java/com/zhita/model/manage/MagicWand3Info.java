package com.zhita.model.manage;

public class MagicWand3Info {
    private Integer id;

    private String transid;

    private String success;

    private String msg;

    private String fee;

    private Integer userid;

    public MagicWand3Info(Integer id, String transid, String success, String msg, String fee, Integer userid) {
        this.id = id;
        this.transid = transid;
        this.success = success;
        this.msg = msg;
        this.fee = fee;
        this.userid = userid;
    }

    public MagicWand3Info() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid == null ? null : transid.trim();
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success == null ? null : success.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee == null ? null : fee.trim();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}