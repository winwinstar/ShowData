package com.dm.mes.showdata.bean;

/**
 * Created by Administrator on 2017/2/13.
 */
public class AuthItem {
    private String title;
    private String Id;
    private String parentId;
    private Boolean wether;

    @Override
    public String toString() {
        return "AuthItem{" +
                "title='" + title + '\'' +
                ", Id='" + Id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", wether=" + wether +
                '}';
    }

    public AuthItem(String title, String id, String parentId, Boolean wether) {
        this.title = title;
        Id = id;
        this.parentId = parentId;
        this.wether = wether;
    }

    public AuthItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Boolean getWether() {
        return wether;
    }

    public void setWether(Boolean wether) {
        this.wether = wether;
    }
}
