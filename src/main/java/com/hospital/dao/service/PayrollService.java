package com.hospital.dao.service;

import com.hospital.GenericDAO;
import com.hospital.dao.db.mysql.PayrollDAO;
import com.hospital.dao.model.Payroll;

import java.util.List;

public class PayrollService implements Service<Payroll>{
//    private final GenericDAO<Payroll> payrollDao;
    private final PayrollDAO payrollDao;

//    public PayrollService(GenericDAO<Payroll> payrollDao) {
//        this.payrollDao = payrollDao;
//    }

    public PayrollService(PayrollDAO payrollDao) {
        this.payrollDao = payrollDao;
    }

    @Override
    public Payroll findById(int id) {
        return payrollDao.findById(id);
    }

    public Payroll findByAccountNumber(int acctNumber) {
        return payrollDao.findByAccountNumber(acctNumber);
    }

    @Override
    public List<Payroll> findAll() {
        return payrollDao.findAll();
    }

    @Override
    public void save(Payroll entity) {
        payrollDao.save(entity);
    }

    @Override
    public void update(Payroll entity) {
        payrollDao.update(entity);
    }

    @Override
    public void delete(Payroll entity) {
        payrollDao.delete(entity);
    }
}
