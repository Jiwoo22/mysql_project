package com.hospital.dao.service;

import com.hospital.GenericDAO;
import com.hospital.dao.model.Appointment;

import java.util.List;

public class AppointmentService implements Service<Appointment> {
    private final GenericDAO<Appointment> appointmentDao;

    public AppointmentService(GenericDAO<Appointment> appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    @Override
    public Appointment findById(int id) {
        return appointmentDao.findById(id);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentDao.findAll();
    }

    @Override
    public void save(Appointment entity) {
        appointmentDao.save(entity);
    }

    @Override
    public void update(Appointment entity) {
        appointmentDao.update(entity);
    }

    @Override
    public void delete(Appointment entity) {
        appointmentDao.delete(entity);
    }
}
