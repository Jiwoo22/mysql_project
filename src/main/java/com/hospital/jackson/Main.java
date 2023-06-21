package com.hospital.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.hospital.model.Doctor;
import com.hospital.hospital.model.Nurse;
import com.hospital.hospital.model.Payroll;

import javax.print.Doc;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Payroll payroll1 = new Payroll();
        payroll1.setPayrollId(1);
        payroll1.setNetSalary(270000.0F);
        payroll1.setBonusSalary(30000.0F);
        payroll1.setAccountNumber(1000024);

        Payroll payroll2 = new Payroll();
        payroll2.setPayrollId(2);
        payroll2.setNetSalary(200000.0F);
        payroll2.setBonusSalary(20000.0F);
        payroll2.setAccountNumber(1000025);

        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setName("John Doe");
        employee.setDateOfBirth("1988-01-01");
        employee.setPayrollId(payroll1.getPayrollId());

        Employee employee2 = new Employee();
        employee.setEmployeeId(2);
        employee2.setName("Liam Lee");
        employee2.setDateOfBirth("1990-07-01");
        employee2.setPayrollId(payroll2.getPayrollId());

        Doctor doctor = new Doctor();
        doctor.setDoctorId(1);
        doctor.setName(employee.getName());
        doctor.setSpecialization("Family Medicine");
        doctor.setEmail("john@example.com");
        doctor.setEmployeeId(employee.getEmployeeId());

        Nurse nurse = new Nurse();
        nurse.setNurseId(1);
        nurse.setName(employee2.getName());
        nurse.setEmail("liam@example.com");
        nurse.setEmployeeId(employee2.getEmployeeId());

        Patient patient = new Patient();
        patient.setId(1);
        patient.setDoctorId(1);
        patient.setPhone("123-456-7891");
        patient.setInsuranceId(1);
        patient.setDateOfBirth("2000-01-01");
        patient.setName("Susan Lee");

        try {
            // Serialization
            ObjectMapper objectMapper = new ObjectMapper();
            String payroll1Json = objectMapper.writeValueAsString(payroll1);
            String payroll2Json = objectMapper.writeValueAsString(payroll2);
            String employeeJson = objectMapper.writeValueAsString(employee);
            String employee2Json = objectMapper.writeValueAsString(employee2);
            String doctorJson = objectMapper.writeValueAsString(doctor);
            String nurseJson = objectMapper.writeValueAsString(nurse);
            String patientJson = objectMapper.writeValueAsString(patient);

            System.out.println(payroll1Json);
            System.out.println(payroll2Json);
            System.out.println(employeeJson);
            System.out.println(employee2Json);
            System.out.println(doctorJson);
            System.out.println(nurseJson);
            System.out.println(patientJson);


            // Deserialization
            Payroll payroll1Deserialized = objectMapper.readValue(payroll1Json, Payroll.class);
            Payroll payroll2Deserialized = objectMapper.readValue(payroll2Json, Payroll.class);
            Employee employeeDeserialized = objectMapper.readValue(employeeJson, Employee.class);
            Employee employee2Deserialized = objectMapper.readValue(employee2Json, Employee.class);
            Doctor doctorDeserialized = objectMapper.readValue(doctorJson, Doctor.class);
            Nurse nurseDeserialized = objectMapper.readValue(nurseJson, Nurse.class);
            Patient patientDeserialized = objectMapper.readValue(patientJson, Patient.class);

            System.out.println(payroll1Deserialized);
            System.out.println(payroll2Deserialized);
            System.out.println(employeeDeserialized);
            System.out.println(employee2Deserialized);
            System.out.println(doctorDeserialized);
            System.out.println(nurseDeserialized);
            System.out.println(patientDeserialized);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
