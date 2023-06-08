package com.hospital.dao.model;

public class Nurse {

    private int nurseId;
    private String name;
    private String email;
    private int employeeId;

    public int getNurseId() {
        return nurseId;
    }

    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return String.format("Nurse[nurseId=%d, name=%s, email=%s, employeeId=%d]",
                nurseId, name, email, employeeId);
    }
}
