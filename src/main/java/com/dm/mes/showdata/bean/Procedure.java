package com.dm.mes.showdata.bean;


import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */
public class Procedure {
    private String name;
    private Boolean isMain;
    private List<Procedure> children;
    private Boolean isWork;

    @Override
    public String toString() {
        return "Procedure{" +
                "name='" + name + '\'' +
                ", isMain=" + isMain +
                ", children=" + children +
                ", isWork=" + isWork +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getMain() {
        return isMain;
    }

    public void setMain(Boolean main) {
        isMain = main;
    }

    public List<Procedure> getChildren() {
        return children;
    }

    public void setChildren(List<Procedure> children) {
        this.children = children;
    }

    public Boolean getWork() {
        return isWork;
    }

    public void setWork(Boolean work) {
        isWork = work;
    }
}
