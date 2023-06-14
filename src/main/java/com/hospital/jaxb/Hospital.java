package com.hospital.jaxb;

import java.util.Date;
import java.util.List;
import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "hospital")
@XmlAccessorType(XmlAccessType.FIELD)
public class Hospital {
    @XmlElementWrapper(name = "employees")
    @XmlElement(name = "employee")
    private List<Employee> employees;

    @XmlElementWrapper(name = "doctors")
    @XmlElement(name = "doctor")
    private List<Doctor> doctors;

    @XmlElementWrapper(name = "nurses")
    @XmlElement(name = "nurse")
    private List<Nurse> nurses;

    @XmlElementWrapper(name = "patients")
    @XmlElement(name = "patient")
    private List<Patient> patients;

    @XmlElementWrapper(name = "payrolls")
    @XmlElement(name = "payroll")
    private List<Payroll> payrolls;

    // Getters and setters
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Nurse> getNurses() {
        return nurses;
    }

    public void setNurses(List<Nurse> nurses) {
        this.nurses = nurses;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Payroll> getPayrolls() {
        return payrolls;
    }

    public void setPayrolls(List<Payroll> payrolls) {
        this.payrolls = payrolls;
    }
}

