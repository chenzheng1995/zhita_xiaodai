package com.zhita.model.manage;


//首页轮播图表
public class HomepageViewpager {
    private Integer id;

    private Integer companyid;//公司id

    private String viewpagerpicture;//轮播图图片

    private String isstick;//是否置顶（（1：置顶；0：不置顶））
    
    private Integer sort;//排序

    private String updatetime;//更新时间

    private String deleted;//假删除（删除：1；没删除0）

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

	public String getViewpagerpicture() {
		return viewpagerpicture;
	}

	public void setViewpagerpicture(String viewpagerpicture) {
		this.viewpagerpicture = viewpagerpicture;
	}

	public String getIsstick() {
		return isstick;
	}

	public void setIsstick(String isstick) {
		this.isstick = isstick;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

   
}