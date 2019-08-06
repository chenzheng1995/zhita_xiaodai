package com.zhita.model.manage;

public class Xtest {
    private Integer id;

    private String name;
    
    private String updateTime;

    public Xtest(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Xtest() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

	@Override
	public String toString() {
		return "Xtest [id=" + id + ", name=" + name + "]";
	}
    
}