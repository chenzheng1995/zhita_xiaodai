package com.zhita.controller.mozhang;
public enum Magicwand3Method {

    magicwand3_standard("moxie.api.risk.magicwand3.standard", "魔杖3.0标准版"),
    magicwand3_enhance("moxie.api.risk.magicwand3.enhance", "魔杖3.0增强版总接口"),
    magicwand3_enhance_attentionlist("moxie.api.risk.magicwand3.enhance-attentionlist", "魔杖3.0增强版关注名单"),
    magicwand3_enhance_multi_info("moxie.api.risk.magicwand3.enhance-multi-info", "魔杖3.0增强版多头"),
    magicwand3_enhance_qualification("moxie.api.risk.magicwand3.enhance-qualification", "魔杖3.0增强版资质"),
    magicwand3_enhance_circle("moxie.api.risk.magicwand3.enhance-circle", "魔杖3.0增强版朋友圈"),

    ;

    private String method;

    private String desc;

    Magicwand3Method(String method, String desc){
        this.method = method;
        this.desc = desc;
    }

    public String getMethod(){
        return this.method;
    }
}
