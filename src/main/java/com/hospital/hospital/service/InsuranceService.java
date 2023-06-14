package com.hospital.hospital.service;

import com.hospital.GenericDAO;
import com.hospital.hospital.model.Insurance;

import java.util.List;

public class InsuranceService implements Service<Insurance> {
    private final GenericDAO<Insurance> insuranceDao;

    public InsuranceService(GenericDAO<Insurance> insuranceDao) {
        this.insuranceDao = insuranceDao;
    }

    @Override
    public Insurance findById(int id) {
        return insuranceDao.findById(id);
    }

    @Override
    public List<Insurance> findAll() {
        return insuranceDao.findAll();
    }

    @Override
    public void save(Insurance entity) {
        insuranceDao.save(entity);
    }

    @Override
    public void update(Insurance entity) {
        insuranceDao.update(entity);
    }

    @Override
    public void delete(Insurance entity) {
        insuranceDao.delete(entity);
    }
}
