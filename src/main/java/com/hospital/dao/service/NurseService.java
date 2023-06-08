package com.hospital.dao.service;

import com.hospital.GenericDAO;
import com.hospital.dao.model.Nurse;

import java.util.List;

public class NurseService implements Service<Nurse> {
    private final GenericDAO<Nurse> nurseDao;

    public NurseService(GenericDAO<Nurse> nurseDao) {
        this.nurseDao = nurseDao;
    }

    @Override
    public Nurse findById(int id) {
        return nurseDao.findById(id);
    }

    @Override
    public List<Nurse> findAll() {
        return nurseDao.findAll();
    }

    @Override
    public void save(Nurse entity) {
        nurseDao.save(entity);
    }

    @Override
    public void update(Nurse entity) {
        nurseDao.update(entity);
    }

    @Override
    public void delete(Nurse entity) {
        nurseDao.delete(entity);
    }
}
