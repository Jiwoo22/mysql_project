package com.hospital.dao.model;

public class Procedure {
    private int code;
    private String name;
    private float cost;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Procedure{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}
