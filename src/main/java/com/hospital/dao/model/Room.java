package com.hospital.dao.model;

public class Room {
    private int number;
    private String type;
    private int capacity;
    private Block block;

    public Room(int number, String type, int capacity, Block block) {
        this.number = number;
        this.type = type;
        this.capacity = capacity;
        this.block = block;
    }

    public Room() {

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    @Override
    public String toString() {
        return "Room{" +
                "number=" + number +
                ", type='" + type + '\'' +
                ", capacity=" + capacity +
                ", block=" + block +
                '}';
    }
}
