package com.zhita.model.manage;

public class Xtest {
    private Integer id;

    private String name;

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
}