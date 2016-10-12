package com.jelly.sso.module;

/**
 * Created by jelly on 2016-10-12.
 */
public class Jsonp {
    private boolean flag = false;
    private String json;

    public Jsonp() {
    }

    public Jsonp(boolean flag, String json) {
        this.flag = flag;
        this.json = json;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
