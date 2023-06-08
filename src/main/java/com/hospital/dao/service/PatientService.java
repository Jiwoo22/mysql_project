package com.hospital.dao.service;

import com.hospital.GenericDAO;
import com.hospital.dao.model.Patient;

import java.util.List;

public class PatientService implements Service<Patient>{
    private final GenericDAO<Patient> patientDao;

    public PatientService(GenericDAO<Patient> patientDao) {
        this.patientDao = patientDao;
    }

    @Override
    public Patient findById(int id) {
        return patientDao.findById(id);
    }

    @Override
    public List<Patient> findAll() {
        return patientDao.findAll();
    }

    @Override
    public void save(Patient entity) {
        patientDao.save(entity);
    }

    @Override
    public void update(Patient entity) {
        patientDao.update(entity);
    }

    @Override
    public void delete(Patient entity) {
        patientDao.delete(entity);
    }
}
