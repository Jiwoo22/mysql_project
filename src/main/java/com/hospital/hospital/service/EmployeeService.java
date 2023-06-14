package com.hospital.hospital.service;

import com.hospital.GenericDAO;
import com.hospital.hospital.model.Employee;

import java.util.List;

public class EmployeeService implements Service<Employee> {
    private final GenericDAO<Employee> employeeDao;

    public EmployeeService(GenericDAO<Employee> employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public Employee findById(int id) {
        return employeeDao.findById(id);
    }

    @Override
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    @Override
    public void save(Employee entity) {
        employeeDao.save(entity);
    }

    @Override
    public void update(Employee entity) {
        employeeDao.update(entity);
    }

    @Override
    public void delete(Employee entity) {
        employeeDao.delete(entity);
    }
}
