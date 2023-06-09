package com.hospital.dao.service;

import com.hospital.GenericDAO;
import com.hospital.dao.model.Procedure;

import java.util.List;

public class ProcedureService implements Service<Procedure> {
    private final GenericDAO<Procedure> procedureDao;

    public ProcedureService(GenericDAO<Procedure> procedureDao) {
        this.procedureDao = procedureDao;
    }

    @Override
    public Procedure findById(int id) {
        return procedureDao.findById(id);
    }

    @Override
    public List<Procedure> findAll() {
        return procedureDao.findAll();
    }

    @Override
    public void save(Procedure entity) {
        procedureDao.save(entity);
    }

    @Override
    public void update(Procedure entity) {
        procedureDao.update(entity);
    }

    @Override
    public void delete(Procedure entity) {
        procedureDao.delete(entity);
    }
}
