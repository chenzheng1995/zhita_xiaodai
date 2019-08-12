package com.zhita.model.manage;

public class ZhimiRisk {
    private Integer id;

    private String requestId;

    private Integer mobile1hCnt;

    private Integer mobile3hCnt;

    private Integer mobile12hCnt;

    private Integer mobile1dCnt;

    private Integer mobile3dCnt;

    private Integer mobile7dCnt;

    private Integer mobile14dCnt;

    private Integer mobile30dCnt;

    private Integer mobile60dCnt;

    private Integer idcard1hCnt;

    private Integer idcard3hCnt;

    private Integer idcard12hCnt;

    private Integer idcard1dCnt;

    private Integer idcard3dCnt;

    private Integer idcard7dCnt;

    private Integer idcard14dCnt;

    private Integer idcard30dCnt;

    private Integer idcard60dCnt;
    
    private Integer userId;
    

    public ZhimiRisk(Integer id, String requestId, Integer mobile1hCnt, Integer mobile3hCnt, Integer mobile12hCnt, Integer mobile1dCnt, Integer mobile3dCnt, Integer mobile7dCnt, Integer mobile14dCnt, Integer mobile30dCnt, Integer mobile60dCnt, Integer idcard1hCnt, Integer idcard3hCnt, Integer idcard12hCnt, Integer idcard1dCnt, Integer idcard3dCnt, Integer idcard7dCnt, Integer idcard14dCnt, Integer idcard30dCnt, Integer idcard60dCnt,Integer userId) {
        this.id = id;
        this.requestId = requestId;
        this.mobile1hCnt = mobile1hCnt;
        this.mobile3hCnt = mobile3hCnt;
        this.mobile12hCnt = mobile12hCnt;
        this.mobile1dCnt = mobile1dCnt;
        this.mobile3dCnt = mobile3dCnt;
        this.mobile7dCnt = mobile7dCnt;
        this.mobile14dCnt = mobile14dCnt;
        this.mobile30dCnt = mobile30dCnt;
        this.mobile60dCnt = mobile60dCnt;
        this.idcard1hCnt = idcard1hCnt;
        this.idcard3hCnt = idcard3hCnt;
        this.idcard12hCnt = idcard12hCnt;
        this.idcard1dCnt = idcard1dCnt;
        this.idcard3dCnt = idcard3dCnt;
        this.idcard7dCnt = idcard7dCnt;
        this.idcard14dCnt = idcard14dCnt;
        this.idcard30dCnt = idcard30dCnt;
        this.idcard60dCnt = idcard60dCnt;
        this.userId = userId;
    }

    public ZhimiRisk() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId == null ? null : requestId.trim();
    }

    public Integer getMobile1hCnt() {
        return mobile1hCnt;
    }

    public void setMobile1hCnt(Integer mobile1hCnt) {
        this.mobile1hCnt = mobile1hCnt;
    }

    public Integer getMobile3hCnt() {
        return mobile3hCnt;
    }

    public void setMobile3hCnt(Integer mobile3hCnt) {
        this.mobile3hCnt = mobile3hCnt;
    }

    public Integer getMobile12hCnt() {
        return mobile12hCnt;
    }

    public void setMobile12hCnt(Integer mobile12hCnt) {
        this.mobile12hCnt = mobile12hCnt;
    }

    public Integer getMobile1dCnt() {
        return mobile1dCnt;
    }

    public void setMobile1dCnt(Integer mobile1dCnt) {
        this.mobile1dCnt = mobile1dCnt;
    }

    public Integer getMobile3dCnt() {
        return mobile3dCnt;
    }

    public void setMobile3dCnt(Integer mobile3dCnt) {
        this.mobile3dCnt = mobile3dCnt;
    }

    public Integer getMobile7dCnt() {
        return mobile7dCnt;
    }

    public void setMobile7dCnt(Integer mobile7dCnt) {
        this.mobile7dCnt = mobile7dCnt;
    }

    public Integer getMobile14dCnt() {
        return mobile14dCnt;
    }

    public void setMobile14dCnt(Integer mobile14dCnt) {
        this.mobile14dCnt = mobile14dCnt;
    }

    public Integer getMobile30dCnt() {
        return mobile30dCnt;
    }

    public void setMobile30dCnt(Integer mobile30dCnt) {
        this.mobile30dCnt = mobile30dCnt;
    }

    public Integer getMobile60dCnt() {
        return mobile60dCnt;
    }

    public void setMobile60dCnt(Integer mobile60dCnt) {
        this.mobile60dCnt = mobile60dCnt;
    }

    public Integer getIdcard1hCnt() {
        return idcard1hCnt;
    }

    public void setIdcard1hCnt(Integer idcard1hCnt) {
        this.idcard1hCnt = idcard1hCnt;
    }

    public Integer getIdcard3hCnt() {
        return idcard3hCnt;
    }

    public void setIdcard3hCnt(Integer idcard3hCnt) {
        this.idcard3hCnt = idcard3hCnt;
    }

    public Integer getIdcard12hCnt() {
        return idcard12hCnt;
    }

    public void setIdcard12hCnt(Integer idcard12hCnt) {
        this.idcard12hCnt = idcard12hCnt;
    }

    public Integer getIdcard1dCnt() {
        return idcard1dCnt;
    }

    public void setIdcard1dCnt(Integer idcard1dCnt) {
        this.idcard1dCnt = idcard1dCnt;
    }

    public Integer getIdcard3dCnt() {
        return idcard3dCnt;
    }

    public void setIdcard3dCnt(Integer idcard3dCnt) {
        this.idcard3dCnt = idcard3dCnt;
    }

    public Integer getIdcard7dCnt() {
        return idcard7dCnt;
    }

    public void setIdcard7dCnt(Integer idcard7dCnt) {
        this.idcard7dCnt = idcard7dCnt;
    }

    public Integer getIdcard14dCnt() {
        return idcard14dCnt;
    }

    public void setIdcard14dCnt(Integer idcard14dCnt) {
        this.idcard14dCnt = idcard14dCnt;
    }

    public Integer getIdcard30dCnt() {
        return idcard30dCnt;
    }

    public void setIdcard30dCnt(Integer idcard30dCnt) {
        this.idcard30dCnt = idcard30dCnt;
    }

    public Integer getIdcard60dCnt() {
        return idcard60dCnt;
    }

    public void setIdcard60dCnt(Integer idcard60dCnt) {
        this.idcard60dCnt = idcard60dCnt;
    }
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}