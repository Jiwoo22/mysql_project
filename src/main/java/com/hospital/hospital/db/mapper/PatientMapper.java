package com.hospital.hospital.db.mapper;


import com.hospital.hospital.model.Patient;


public interface PatientMapper {

    Patient selectPatientById(int id);

    Patient selectPatientByName(String name);

    void addPatient(Patient patient);
}
