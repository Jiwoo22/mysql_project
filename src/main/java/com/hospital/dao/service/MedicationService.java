package com.hospital.dao.service;

import com.hospital.GenericDAO;
import com.hospital.dao.model.Medication;

import java.util.List;

public class MedicationService implements Service<Medication> {
    private final GenericDAO<Medication> medicationDao;

    public MedicationService(GenericDAO<Medication> medicationDao) {
        this.medicationDao = medicationDao;
    }

    @Override
    public Medication findById(int id) {
        return medicationDao.findById(id);
    }

    @Override
    public List<Medication> findAll() {
        return medicationDao.findAll();
    }

    @Override
    public void save(Medication entity) {
        medicationDao.save(entity);
    }

    @Override
    public void update(Medication entity) {
        medicationDao.update(entity);
    }

    @Override
    public void delete(Medication entity) {
        medicationDao.delete(entity);
    }
}
