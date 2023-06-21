package com.hospital.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Doctor {
    @JsonProperty("doctorId")
    private int doctorId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("specialization")
    private String specialization;

    @JsonProperty("email")
    private String email;

    @JsonProperty("employeeId")
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
