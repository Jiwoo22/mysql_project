package com.hospital.dao.service;

import com.hospital.GenericDAO;
import com.hospital.dao.model.Department;

import java.util.List;

public class DepartmentService implements Service<Department> {
    private final GenericDAO<Department> departmentDao;

    public DepartmentService(GenericDAO<Department> departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public Department findById(int id) {
        return departmentDao.findById(id);
    }

    @Override
    public List<Department> findAll() {
        return departmentDao.findAll();
    }

    @Override
    public void save(Department entity) {
        departmentDao.save(entity);
    }

    @Override
    public void update(Department entity) {
        departmentDao.update(entity);
    }

    @Override
    public void delete(Department entity) {
        departmentDao.delete(entity);
    }
}
