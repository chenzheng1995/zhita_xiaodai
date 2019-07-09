package com.zhita.model.manage;

public class SourceTemplate {
    private Integer id;

    private String name;

    private String templateimage;

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

    public String getTemplateimage() {
        return templateimage;
    }

    public void setTemplateimage(String templateimage) {
        this.templateimage = templateimage == null ? null : templateimage.trim();
    }
}