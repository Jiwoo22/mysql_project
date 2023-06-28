package com.hospital.patterns.builder;

public class Payroll {
    private int payrollId;
    private float netSalary;
    private float bonusSalary;
    private int accountNumber;

    public Payroll(PayrollBuilder builder) {
        this.payrollId = builder.payrollId;
        this.netSalary = builder.netSalary;
        this.bonusSalary = builder.bonusSalary;
        this.accountNumber = builder.accountNumber;
    }

    public int getPayrollId() {
        return payrollId;
    }

    public float getNetSalary() {
        return netSalary;
    }

    public float getBonusSalary() {
        return bonusSalary;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public static class PayrollBuilder {
        private int payrollId;
        private float netSalary;
        private float bonusSalary;
        private int accountNumber;

        public PayrollBuilder setPayrollId(int payrollId) {
            this.payrollId = payrollId;
            return this;
        }

        public PayrollBuilder setNetSalary(float netSalary) {
            this.netSalary = netSalary;
            return this;
        }

        public PayrollBuilder setBonusSalary(float bonusSalary) {
            this.bonusSalary = bonusSalary;
            return this;
        }

        public PayrollBuilder setAccountNumber(int accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Payroll build() {
            return new Payroll(this);
        }
    }

    @Override
    public String toString() {
        return String.format("Payroll[payrollId=%d, netSalary=%f, bonusSalary=%f, accountNumber=%d]"
                , payrollId, netSalary, bonusSalary, accountNumber);
    }
}
