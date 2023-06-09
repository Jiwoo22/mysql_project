package com.hospital.dao.model;

public class Doctor {

    private int doctorId;
    private String name;
    private String specialization;
    private String email;
    private int employeeId;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return String.format("Doctor[doctorId=%d, name=%s, specialization=%s, email=%s, employeeId=%d]"
                , doctorId, name, specialization, email, employeeId);
    }
}
