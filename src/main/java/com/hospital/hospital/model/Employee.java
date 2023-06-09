package com.hospital.hospital.model;

public class Employee {

    private int employeeId;
    private String name;
    private String dateOfBirth;
    private int payrollId;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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

    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    @Override
    public String toString() {
        return String.format("Employee[employeeId=%d, name=%s, DOB=%s, payrollId=%d]"
                , employeeId, name, dateOfBirth, payrollId);
    }
}
