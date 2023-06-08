package com.hospital;

import com.hospital.dao.db.mysql.*;
import com.hospital.dao.model.*;
import com.hospital.dao.service.*;
import com.hospital.utils.ConnectionPool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {

        Properties props = new Properties();

        try (InputStream input = new FileInputStream("src/main/resources/db.properties")) {
            props.load(input);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create a ConnectionPool and initialize it
        ConnectionPool connectionPool = new ConnectionPool(props.getProperty("db.url")
                , props.getProperty("db.user"), props.getProperty("db.password"), 10);

        // Create instances of DAOs with the ConnectionPool
        GenericDAO<Payroll> payrollDAO = new PayrollDAO(connectionPool);
        GenericDAO<Employee> employeeDao = new EmployeeDAO(connectionPool);
        GenericDAO<Doctor> doctorDao = new DoctorDAO(connectionPool);
        GenericDAO<Nurse> nurseDao = new NurseDAO(connectionPool);
        GenericDAO<Patient> patientDao = new PatientDAO(connectionPool);
        GenericDAO<Insurance> insuranceDao = new InsuranceDAO(connectionPool);

        // Create instances of Services
        PayrollService payrollService = new PayrollService((PayrollDAO) payrollDAO);
        EmployeeService employeeService = new EmployeeService(employeeDao);
        DoctorService doctorService = new DoctorService(doctorDao);
        NurseService nurseService = new NurseService(nurseDao);
        PatientService patientService = new PatientService(patientDao);
        InsuranceService insuranceService = new InsuranceService(insuranceDao);

        // Create operation
        Payroll payroll1 = new Payroll();
        payroll1.setNetSalary(270000.0F);
        payroll1.setBonusSalary(30000.0F);
        payroll1.setAccountNumber(1000013);
        payrollService.save(payroll1);
        System.out.println(payroll1);

        Payroll payroll2 = new Payroll();
        payroll2.setNetSalary(200000.0F);
        payroll2.setBonusSalary(20000.0F);
        payroll2.setAccountNumber(1000014);
        payrollService.save(payroll2);
        System.out.println(payroll2);

        Employee employee1 = new Employee();
        employee1.setName("Theo Doe");
        employee1.setDateOfBirth("2000-07-01");
        employee1.setPayrollId(payroll1.getPayrollId());
        employeeService.save(employee1);
        System.out.println("New employee created: " + employee1);

        Employee employee2 = new Employee();
        employee2.setName("Michael Lee");
        employee2.setDateOfBirth("1990-07-01");
        employee2.setPayrollId(payroll2.getPayrollId());
        employeeService.save(employee2);
        System.out.println("New employee created: " + employee2);

        Doctor doctor = new Doctor();
        doctor.setName(employee1.getName());
        doctor.setSpecialization("Family Medicine");
        doctor.setEmail("cyndelee@example.com");
        doctor.setEmployeeId(employee1.getEmployeeId());
        doctorService.save(doctor);
        System.out.println("New doctor created: " + doctor);

        Nurse nurse = new Nurse();
        nurse.setName(employee2.getName());
        nurse.setEmail("mayDoe@example.com");
        nurse.setEmployeeId(employee2.getEmployeeId());
        nurseService.save(nurse);
        System.out.println("New nurse created: " + nurse);

        Insurance insurance = new Insurance();
        insurance.setType("PPO");
        insurance.setExpirationDate("2033-01-01");
        insuranceService.save(insurance);
        System.out.println("New insurance created: " + insurance);

        Patient patient = new Patient();
        patient.setName("Amy Smith");
        patient.setDateOfBirth("2010-01-01");
        patient.setPhone("123456789");
        patient.setDoctorId(doctor.getDoctorId());
        patient.setInsuranceId(insurance.getInsuranceId());
        patientService.save(patient);
        System.out.println("New patient created: " + patient);


//        // Read by account number
//        Payroll addedPayroll = payrollService.findByAccountNumber(payroll.getAccountNumber());
//        System.out.println("Found payroll : " + addedPayroll);
//
//        // Update payroll ID after saving
//        payroll.setPayrollId(addedPayroll.getPayrollId());
//        System.out.println(payroll);
//
//        // Update BonusSalary
//        payroll.setBonusSalary(40000.0F);
//        payrollService.update(payroll);
//        System.out.println("Updated payroll: " + payroll);
//
//        // Find all payrolls
//        List<Payroll> allPayrolls = payrollService.findAll();
//        System.out.println("All payrolls: ");
//        for (Payroll p : allPayrolls) {
//            System.out.println("\t" + p);
//        }

//        // Delete the payroll
//        payrollService.delete(payroll);
//        System.out.println("Payroll deleted: " + payroll);

//
//        //---------Employee----------
//        GenericDAO<Employee> employeeDao = new EmployeeDAO(connectionPool);
//        EmployeeService employeeService = new EmployeeService(employeeDao);
//
//        Employee employee = new Employee();
//        employee.setName("Theo Doe");
//        employee.setDateOfBirth("2000-07-01");
//        employee.setPayrollId(5);
//        employeeService.save(employee);
//        System.out.println("New employee created: " + employee);

//        // Find an employee by ID
//        Employee foundEmployee = employeeService.findById(5);
//        System.out.println("Found employee by ID: " + foundEmployee);

//        // Update the employee's name
//        foundEmployee.setName("Theo Choi");
//        employeeService.update(foundEmployee);
//        System.out.println("Updated employee: " + foundEmployee);
//
//        // Find all employees
//        List<Employee> allEmployees = employeeService.findAll();
//        System.out.println("All employees: ");
//        for (Employee emp : allEmployees) {
//            System.out.println("\t" + emp);
//        }
//
//        // Delete the employee
//        employeeService.delete(foundEmployee);
//        System.out.println("Employee deleted: " + foundEmployee);


//        //---------Doctor----------
//        // Create an instance of DoctorDAO with the ConnectionPool
//        GenericDAO<Doctor> doctorDao = new DoctorDAO(connectionPool);
//
//        // Create an instance of DoctorService with the DoctorDAO
//        DoctorService doctorService = new DoctorService(doctorDao);
//
//        // Create a new doctor
//        Doctor doctor = new Doctor();
//        doctor.setName("Cynde Lee");
//        doctor.setSpecialization("Family Medicine");
//        doctor.setEmail("cyndelee@example.com");
//        doctor.setEmployeeId(4);
//        doctorService.save(doctor);
//        System.out.println("New doctor created: " + doctor);
//
//        // Find a doctor by ID
//        Doctor foundDoctor = doctorService.findById(4);
//        System.out.println("Found doctor by ID: " + foundDoctor);
//
//        // Update the doctor's name
//        foundDoctor.setName("Cindy Lee");
//        doctorService.update(foundDoctor);
//        System.out.println("Updated doctor: " + foundDoctor);
//
//        // Find all doctors
//        List<Doctor> allDoctors = doctorService.findAll();
//        System.out.println("All doctors: ");
//        for (Doctor doc : allDoctors) {
//            System.out.println("\t" + doc);
//        }
//
//        // Delete the doctor
//        doctorService.delete(foundDoctor);
//        System.out.println("Doctor deleted: " + foundDoctor);

//
//        //---------Nurse----------
//        // Create an instance of NurseDAO with the ConnectionPool
//        GenericDAO<Nurse> nurseDao = new NurseDAO(connectionPool);
//
//        // Create an instance of NurseService with the NurseDAO
//        NurseService nurseService = new NurseService(nurseDao);
//
//        // Create a new nurse
//        Nurse nurse = new Nurse();
//        nurse.setName("May Doe");
//        nurse.setEmail("mayDoe@example.com");
//        nurse.setEmployeeId(2);
//        nurseService.save(nurse);
//        System.out.println("New nurse created: " + nurse);
//
//        // Find a nurse by ID
//        Nurse foundNurse = nurseService.findById(2);
//        System.out.println("Found nurse by ID: " + foundNurse);
//
//        // Update the nurse's name
//        foundNurse.setName("May Smith");
//        nurseService.update(foundNurse);
//        System.out.println("Updated nurse: " + foundNurse);
//
//        // Find all nurses
//        List<Nurse> allNurses = nurseService.findAll();
//        System.out.println("All nurses: ");
//        for (Nurse n : allNurses) {
//            System.out.println("\t" + n);
//        }
//
//        // Delete the nurse
//        nurseService.delete(foundNurse);
//        System.out.println("Nurse deleted: " + foundNurse);
//
//        // Find the deleted nurse by ID
//        Nurse deletedNurse = nurseService.findById(nurseId);
//        System.out.println("Deleted nurse: " + deletedNurse);






        // Test the CRUD operations

        // Create a new nurse
        //---------Department----------
        //---------Doctors_Departments----------
        //---------Procedure----------
        //---------Block----------
        //---------Room----------

        //---------Insurance----------
        //---------Patient----------
        //---------Appointment---------
        //---------Medication----------
        //---------Prescribe----------
        //---------Undergoes----------


//
//
//        // Create an instance of PatientDAO with the ConnectionPool
//        GenericDAO<Patient> patientDAO = new PatientDAO(connectionPool);
//
//        // Create an instance of PatientService with the PatientDAO
//        PatientService patientService = new PatientService(patientDAO);
//
//        // Create a new patient
//        Patient patient = new Patient();
//        patient.setName("Amy Smith");
//        patient.setDateOfBirth("1990-01-01");
//        patient.setPhone("123456789");
//        patient.setDoctorId(1);
//        patient.setInsuranceId(1);
//        patientService.save(patient);
//        System.out.println("New patient created: " + patient);

//        Patient patient = patientService.findById(2);
//        System.out.println("Patient ID 2 : " + patient);
//
//        List<Patient> allPatients = patientService.findAll();
//        System.out.println("All patients : ");
//        for (Patient each : allPatients) {
//            System.out.println("\t" + each);
//        }
//
////        Patient patient3 = new Patient(3, "Dylon", "1990-05-05", "123-456-6789", 3, 2);
////        patientService.save(patient3);

    }
}
