package com.hospital.hospital.model;

public class Patient {

    private int id;
    private String name;
    private String dateOfBirth;
    private String phone;
    private int insuranceId;
    private int doctorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(int insuranceId) {
        this.insuranceId = insuranceId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }



    @Override
    public String toString() {
        return String.format("Patient[id=%d, name=%s, DOB=%s, phone=%s, insuranceId=%d, doctorId=%d]"
                , id, name, dateOfBirth, phone, insuranceId, doctorId);
    }
}
