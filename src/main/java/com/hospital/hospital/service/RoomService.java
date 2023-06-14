package com.hospital.hospital.service;

import com.hospital.GenericDAO;
import com.hospital.hospital.model.Room;

import java.util.List;

public class RoomService implements Service<Room> {
    private final GenericDAO<Room> roomDao;

    public RoomService(GenericDAO<Room> roomDao) {
        this.roomDao = roomDao;
    }

    @Override
    public Room findById(int id) {
        return roomDao.findById(id);
    }

    @Override
    public List<Room> findAll() {
        return roomDao.findAll();
    }

    @Override
    public void save(Room entity) {
        roomDao.save(entity);
    }

    @Override
    public void update(Room entity) {
        roomDao.update(entity);
    }

    @Override
    public void delete(Room entity) {
        roomDao.delete(entity);
    }
}
