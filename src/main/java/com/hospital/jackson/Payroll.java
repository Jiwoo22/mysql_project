package com.hospital.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payroll {
    @JsonProperty("payrollId")
    private int payrollId;

    @JsonProperty("netSalary")
    private float netSalary;

    @JsonProperty("bonusSalary")
    private float bonusSalary;

    @JsonProperty("accountNumber")
    private int accountNumber;

    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public float getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(float netSalary) {
        this.netSalary = netSalary;
    }

    public Float getBonusSalary() {
        return bonusSalary;
    }

    public void setBonusSalary(Float bonusSalary) {
        this.bonusSalary = bonusSalary;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return String.format("Payroll[payrollId=%d, netSalary=%f, bonusSalary=%f, accountNumber=%d]"
                , payrollId, netSalary, bonusSalary, accountNumber);
    }
}
