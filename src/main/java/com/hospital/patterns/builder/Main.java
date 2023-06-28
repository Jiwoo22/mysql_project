package com.hospital.patterns.builder;

public class Main {
    public static void main(String[] args) {

        // patient builder
        Patient patient = new Patient.PatientBuilder()
                .setName("Jiwoo")
                .setDateOfBirth("2010-10-10")
                .setInsuranceId(8)
                .setPhone("123-23-2333")
                .setDoctorId(17)
                .build();

        System.out.println("Patient name: " + patient.getName());
        System.out.println("DOB: " + patient.getDateOfBirth());
        System.out.println("Insurance id: " + patient.getInsuranceId());
        System.out.println("Doctor id: " + patient.getDoctorId());
        System.out.println("phone: " + patient.getPhone());

        // doctor builder
        Doctor doctor = new Doctor.DoctorBuilder()
                .setDoctorId(1)
                .setName("John Doe")
                .setSpecialization("Cardiology")
                .setEmail("johndoe@example.com")
                .setEmployeeId(1)
                .build();

        System.out.println("Doctor name: " + doctor.getName());
        System.out.println("Specialization: " + doctor.getSpecialization());
        System.out.println("Email: " + doctor.getEmail());
        System.out.println("Employee id: " + doctor.getEmployeeId());

        // nurse builder
        Nurse nurse = new Nurse.NurseBuilder()
                .setNurseId(1)
                .setName("Jane Doe")
                .setEmail("janedoe@example.com")
                .setEmployeeId(2)
                .build();

        System.out.println("Nurse name: " + nurse.getName());
        System.out.println("Email: " + nurse.getEmail());
        System.out.println("Employee id: " + nurse.getEmployeeId());

        // payroll builder
        Payroll payroll = new Payroll.PayrollBuilder()
                .setPayrollId(1)
                .setNetSalary(30000.0F)
                .setBonusSalary(2000.0F)
                .setAccountNumber(123456)
                .build();

        System.out.println("Net Salary: " + payroll.getNetSalary());
        System.out.println("Bonus Salary: " + payroll.getBonusSalary());
        System.out.println("Account Number: " + payroll.getAccountNumber());

    }
}
