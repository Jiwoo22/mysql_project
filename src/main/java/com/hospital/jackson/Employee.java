package com.hospital.jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {
    @JsonProperty("employeeId")
    private int employeeId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("dateOfBirth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd")
    private String dateOfBirth;

    @JsonProperty("payrollId")
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
