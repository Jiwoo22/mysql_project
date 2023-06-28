package com.hospital;

import com.hospital.hospital.db.mysql.*;
import com.hospital.hospital.model.*;
import com.hospital.hospital.service.*;
import com.hospital.utils.ConnectionPool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoMain {

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
                , props.getProperty("db.user"), props.getProperty("db.password"), 12);

        // Create instances of DAOs with the ConnectionPool
        GenericDAO<Payroll> payrollDAO = new PayrollDAO(connectionPool);
        GenericDAO<Employee> employeeDao = new EmployeeDAO(connectionPool);
        GenericDAO<Doctor> doctorDao = new DoctorDAO(connectionPool);
        GenericDAO<Nurse> nurseDao = new NurseDAO(connectionPool);
        GenericDAO<Patient> patientDao = new PatientDAO(connectionPool);
        GenericDAO<Insurance> insuranceDao = new InsuranceDAO(connectionPool);
//        GenericDAO<Department>  departmentDao = new DepartmentDAO(connectionPool);
//        AppointmentDAO appointmentDAO = new AppointmentDAO(connectionPool);
//        RoomDAO roomDao = new RoomDAO(connectionPool);
//        MedicationDAO medicationDAO = new MedicationDAO(connectionPool);
//        ProcedureDAO procedureDao = new ProcedureDAO(connectionPool);
//        BlockDAO blockDAO = new BlockDAO(connectionPool);

        // Create instances of Services
        PayrollService payrollService = new PayrollService((PayrollDAO) payrollDAO);
        EmployeeService employeeService = new EmployeeService(employeeDao);
        DoctorService doctorService = new DoctorService(doctorDao);
        NurseService nurseService = new NurseService(nurseDao);
        PatientService patientService = new PatientService(patientDao);
        InsuranceService insuranceService = new InsuranceService(insuranceDao);
//        DepartmentService departmentService = new DepartmentService(departmentDao);
//        AppointmentService appointmentService = new AppointmentService(appointmentDAO);
//        RoomService roomService = new RoomService(roomDao);
//        MedicationService medicationService = new MedicationService(medicationDAO);
//        ProcedureService procedureService = new ProcedureService(procedureDao);
//        BlockService blockService = new BlockService(blockDAO);


        // Create operation
        Payroll payroll1 = new Payroll();
        payroll1.setNetSalary(270000.0F);
        payroll1.setBonusSalary(30000.0F);
        payroll1.setAccountNumber(1000024);
        payrollService.save(payroll1);
        System.out.println(payroll1);
//
        Payroll payroll2 = new Payroll();
        payroll2.setNetSalary(200000.0F);
        payroll2.setBonusSalary(20000.0F);
        payroll2.setAccountNumber(1000025);
        payrollService.save(payroll2);
        System.out.println(payroll2);

        Employee employee1 = new Employee();
        employee1.setName("Olivia Kim");
        employee1.setDateOfBirth("2000-07-01");
        employee1.setPayrollId(payroll1.getPayrollId());
        employeeService.save(employee1);
        System.out.println("New employee created: " + employee1);

        Employee employee2 = new Employee();
        employee2.setName("Liam Lee");
        employee2.setDateOfBirth("1990-07-01");
        employee2.setPayrollId(payroll2.getPayrollId());
        employeeService.save(employee2);
        System.out.println("New employee created: " + employee2);
//
        Doctor doctor = new Doctor();
        doctor.setName(employee1.getName());
        doctor.setSpecialization("Family Medicine");
        doctor.setEmail("olivia@example.com");
        doctor.setEmployeeId(employee1.getEmployeeId());
        doctorService.save(doctor);
        System.out.println("New doctor created: " + doctor);

        Nurse nurse = new Nurse();
        nurse.setName(employee2.getName());
        nurse.setEmail("liam@example.com");
        nurse.setEmployeeId(employee2.getEmployeeId());
        nurseService.save(nurse);
        System.out.println("New nurse created: " + nurse);
//
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

//        // -------Payroll-----
//        // Read operations
//        Payroll foundPayroll = payrollService.findById(38);
//        System.out.println("Found payroll by id: " + foundPayroll);
//
//        // Update operations
//        foundPayroll.setNetSalary(2000000);
//        payrollService.update(foundPayroll);
//        System.out.println("Updated payroll" + foundPayroll);
//
//        // Delete operations
//        payrollService.delete(foundPayroll);
//        System.out.println("Deleted payroll" + foundPayroll);

//        // -------Employee-----
//        // Find all employees
//        List<Employee> allEmployees = employeeService.findAll();
//        System.out.println("All employees: ");
//        for (Employee emp : allEmployees) {
//            System.out.println("\t" + emp);
//        }

//        // Read an employee by ID
//        Employee foundEmployee = employeeService.findById(24);
//        System.out.println("Found employee by ID: " + foundEmployee);
//
//        // Update the employee's name
//        foundEmployee.setName("Michael Choi");
//        employeeService.update(foundEmployee);
//        System.out.println("Updated employee: " + foundEmployee);
//
//        // Delete the employee
//        employeeService.delete(foundEmployee);
//        System.out.println("Employee deleted: " + foundEmployee);


//        //---------Doctor----------
//        // Find all doctors
//        List<Doctor> allDoctors = doctorService.findAll();
//        System.out.println("All doctors: ");
//        for (Doctor doc : allDoctors) {
//            System.out.println("\t" + doc);
//        }
//
//        // Read a doctor by ID
//        Doctor foundDoctor = doctorService.findById(13);
//        System.out.println("Found doctor by ID: " + foundDoctor);
//
//        // Update the doctor's name
//        foundDoctor.setSpecialization("Internal Medicine");
//        doctorService.update(foundDoctor);
//        System.out.println("Updated doctor: " + foundDoctor);
//
//        // Delete the doctor
//        doctorService.delete(foundDoctor);
//        System.out.println("Doctor deleted: " + foundDoctor);


//        //---------Nurse----------
//        // Find all nurses
//        List<Nurse> allNurses = nurseService.findAll();
//        System.out.println("All nurses: ");
//        for (Nurse n : allNurses) {
//            System.out.println("\t" + n);
//        }

//        // Read a nurse by ID
//        Nurse foundNurse = nurseService.findById(11);
//        System.out.println("Found nurse by ID: " + foundNurse);
//
//        // Update the nurse's name
//        foundNurse.setEmail("jlee@example.com");
//        nurseService.update(foundNurse);
//        System.out.println("Updated nurse: " + foundNurse);
//
//        // Delete the nurse
//        nurseService.delete(foundNurse);
//        System.out.println("Nurse deleted: " + foundNurse);


        //---------Department----------
//        Department department = new Department();
//        department.setName("Cardiology");
//        department.setHead(14); // Set the head doctor ID
//
//        departmentService.save(department);
//        System.out.println("New department created with ID: " + department.getDepartmentId());
//
//        department.setName("General Medicine");
//        departmentService.update(department);
//        System.out.println("Updated department : " + department);
//
//        departmentService.delete(department);
//        System.out.println("Deleted department: " + department);


//        //---------Doctors_Departments----------


////        //---------Procedure----------//
//        Procedure procedure = new Procedure();
//        procedure.setName("X-Ray");
//        procedure.setCost(150.0f);
//
//        procedureService.save(procedure);
//        System.out.println("New procedure created with code: " + procedure.getCode());

//        Procedure retrievedProcedure = procedureService.findById(procedure.getCode());
//        System.out.println("Retrieved procedure: " + retrievedProcedure);
//
//        retrievedProcedure.setName("MRI");
//        procedureService.update(retrievedProcedure);
//        System.out.println("Procedure updated: " + retrievedProcedure);
//
//        procedureService.delete(retrievedProcedure);
//        System.out.println("Deleted updated: " + retrievedProcedure);


//        //---------Block----------
//        // create
//        Block block1 = new Block();
//        block1.setFloor(3);
//        block1.setCode(301);
//        blockService.save(block1);
//        System.out.println("New block created: " + block1);
//
//        // read
//        Block retrievedBlock = blockService.findById(block1.getFloor(), block1.getCode());
//        System.out.println("Retrieved Block: " + retrievedBlock);
//
//        // Update
//        retrievedBlock.setCode(302);
//        blockService.update(retrievedBlock);
//        System.out.println("Updated block: " + retrievedBlock);
//
//        List<Block> allBlocks = blockService.findAll();
//        System.out.println("All Blocks: " + allBlocks);


        //---------Room----------
//        Room room1 = new Room(105, "Standard", 2, new Block(1, 105));
//        roomService.save(room1);
//
//        Room room2 = roomService.findById(105);
//        System.out.println("Found room: " + room2);


//        //---------Appointment---------
//        GenericDAO<Doctor> doctorDao = new DoctorDAO(connectionPool);
//        GenericDAO<Nurse> nurseDao = new NurseDAO(connectionPool);
//        GenericDAO<Patient> patientDao = new PatientDAO(connectionPool);
//        AppointmentDAO appointmentDAO = new AppointmentDAO(connectionPool);
//        RoomDAO roomDao = new RoomDAO(connectionPool);
//
//        DoctorService doctorService = new DoctorService(doctorDao);
//        NurseService nurseService = new NurseService(nurseDao);
//        RoomService roomService = new RoomService(roomDao);
//        PatientService patientService = new PatientService(patientDao);
//        AppointmentService appointmentService = new AppointmentService(appointmentDAO);
//
//        Room room = roomService.findById(105);

//        List<Nurse> allNurses = nurseService.findAll();
//        System.out.println("All nurses: ");
//        for (Nurse n : allNurses) {
//            System.out.println("\t" + n);
//        }
//
//        List<Doctor> allDoctors = doctorService.findAll();
//        System.out.println("All doctors: ");
//        for (Doctor doc : allDoctors) {
//            System.out.println("\t" + doc);
//        }

//        List<Patient> allPatients = patientService.findAll();
//        System.out.println("All patients: ");
//        for (Patient pat : allPatients) {
//            System.out.println("\t" + pat);
//        }

//        Appointment appointment = new Appointment();
//        appointment.setPatient(patientService.findById(8));
//        appointment.setDoctor(doctorService.findById(14));
//        appointment.setNurse(nurseService.findById(12));
//        appointment.setStartTime(Timestamp.valueOf("2023-07-01 12:00:00"));
//        appointment.setEndTime(Timestamp.valueOf("2023-07-01 12:30:00"));
//        appointment.setRoom(room);
//        appointmentService.save(appointment);
//        System.out.println("Saved appointment: " + appointment);
//
//        appointment.setEndTime(Timestamp.valueOf("2023-07-01 1:00:00"));
//        appointmentService.update(appointment);
//        System.out.println("Updated appointment: " +
//                appointmentService.findById(appointment.getAppointmentId()));
//
//        appointmentService.delete(appointment);
//        System.out.println("Delete appointment: " + appointment);


//        //---------Medication----------
//        // create
//        Medication medication1 = new Medication();
//        medication1.setName("Medication 1");
//        medication1.setBrand("Brand 1");
//        medication1.setDescription("Description 1");
//        medicationService.save(medication1);
//        System.out.println("New medication created with ID: " + medication1.getMedicationId());
//
//        // read
//        Medication retrievedMedication = medicationService.findById(medication1.getMedicationId());
//        System.out.println("Retrieved Medication: " + retrievedMedication);
//
//        // update
//        retrievedMedication.setName("Updated Medication");
//        medicationService.update(retrievedMedication);
//        System.out.println("Medication updated: " + retrievedMedication);

    }
}
