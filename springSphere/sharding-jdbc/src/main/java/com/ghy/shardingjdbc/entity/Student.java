package com.ghy.shardingjdbc.entity;

import lombok.Data;

//@Data
public class Student {
    private Integer sid;
    private String name;
    private String qq;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid=" + sid +
                ", name='" + name + '\'' +
                ", qq='" + qq + '\'' +
                '}';
    }
}
