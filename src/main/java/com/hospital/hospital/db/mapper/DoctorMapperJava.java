package com.hospital.hospital.db.mapper;

import com.hospital.hospital.model.Doctor;
import org.apache.ibatis.annotations.*;

public interface DoctorMapperJava {

    @Select("SELECT * FROM Doctors WHERE doctor_id = #{id}")
    @Results({
            @Result(property = "doctorId", column = "doctor_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "specialization", column = "specialization"),
            @Result(property = "email", column = "email"),
            @Result(property = "employeeId", column = "employee_id")
    })
    Doctor selectDoctorById(int id);

    @Insert("INSERT INTO Doctors (name, specialization, email, employee_id) VALUES (#{name}, #{specialization}, #{email}, #{employeeId})")
    void addDoctor(Doctor doctor);

    @Update("UPDATE Doctors SET name=#{name}, specialization=#{specialization}, email=#{email}, employee_id=#{employeeId} WHERE doctor_id=#{doctorId}")
    void updateDoctor(Doctor doctor);

    @Delete("DELETE FROM Doctors WHERE doctor_id=#{doctorId}")
    void deleteDoctor(int id);
}