package com.jelly.sso.module;

import com.google.gson.Gson;

/**
 * Created by jelly on 2016-9-21.
 */
public class User implements Cloneable{
    private Long id;
    private String name;
    private String password;

    public User() {
    }

    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }

    @Override
    public User clone() throws CloneNotSupportedException {
        User user = (User)super.clone();
        return user;
    }
}
