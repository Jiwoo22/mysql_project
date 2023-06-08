package com.hospital.dao.service;

import com.hospital.GenericDAO;
import com.hospital.dao.model.Doctor;

import java.util.List;

public class DoctorService implements Service<Doctor> {
    private final GenericDAO<Doctor> doctorDao;

    public DoctorService(GenericDAO<Doctor> doctorDao) {
        this.doctorDao = doctorDao;
    }

    @Override
    public Doctor findById(int id) {
        return doctorDao.findById(id);
    }

    @Override
    public List<Doctor> findAll() {
        return doctorDao.findAll();
    }

    @Override
    public void save(Doctor entity) {
        doctorDao.save(entity);
    }

    @Override
    public void update(Doctor entity) {
        doctorDao.update(entity);
    }

    @Override
    public void delete(Doctor entity) {
        doctorDao.delete(entity);
    }
}
