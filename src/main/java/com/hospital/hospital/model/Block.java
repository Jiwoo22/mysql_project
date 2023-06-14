package com.hospital.hospital.model;

public class Block {
    private int floor;
    private int code;

    public Block() {

    }

    public Block(int floor, int code) {
        this.floor = floor;
        this.code = code;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Block [floor=" + floor + ", code=" + code + "]";
    }
}
