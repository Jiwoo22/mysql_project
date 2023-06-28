package com.hospital;

import com.hospital.hospital.db.mapper.*;
import com.hospital.hospital.model.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisRunner {

    public static void main(String[] args) throws IOException {
        try(InputStream stream = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession(true)){

            // For Patient
            PatientMapperJava patientMapperJava = session.getMapper(PatientMapperJava.class);
            Patient patient2 = patientMapperJava.selectPatientById(8);
            System.out.println("Patient from DB: \n" + patient2);

            // For Employee
            EmployeeMapperJava employeeMapperJava = session.getMapper(EmployeeMapperJava.class);
            Employee employee = employeeMapperJava.selectEmployeeById(23);
            System.out.println("Employee from DB: \n" + employee);

            // For Nurse
            NurseMapperJava nurseMapper = session.getMapper(NurseMapperJava.class);
            Nurse nurse = nurseMapper.selectNurseById(12);
            System.out.println("Nurse from DB: \n" + nurse);

            // For Doctor
            DoctorMapperJava doctorMapper = session.getMapper(DoctorMapperJava.class);
            Doctor doctor = doctorMapper.selectDoctorById(14);
            System.out.println("Doctor from DB: \n" + doctor);

            // For Payroll
            PayrollMapperJava payrollMapper = session.getMapper(PayrollMapperJava.class);
            Payroll payroll = payrollMapper.selectPayrollById(39);
            System.out.println("Payroll from DB: \n" + payroll);

        }
    }
}
