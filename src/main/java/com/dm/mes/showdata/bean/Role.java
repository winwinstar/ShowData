package com.dm.mes.showdata.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/14.
 */
public class Role {
    private String role;
    private String createTime;
    private List<User> users;
    private List<AuthItem> authItems;

    public List<AuthItem> getAuthItems() {
        return authItems;
    }

    public void setAuthItems(List<AuthItem> authItems) {
        this.authItems = authItems;
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
