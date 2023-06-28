package com.hospital.hospital.db.mapper;


import com.hospital.hospital.model.Patient;
import org.apache.ibatis.annotations.*;


public interface PatientMapperJava {

    @Select("select patient_id, name from Patients where patient_id = #{id}")
    @Results({
            @Result(property = "id", column = "patient_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "doctorId", column = "doctor_id"),
            @Result(property = "dateOfBirth", column = "DOB"),
            @Result(property = "insuranceId", column = "insurance_id")
    })
    Patient selectPatientById(int id);

    @Select("select * from Patients where name = #{name}")
    Patient selectPatientByName(String name);

    @Insert("INSERT INTO Patients (name, phone,  doctor_id, DOB, insurance_id) VALUES (#{name}, #{phone}, #{doctorId}, #{dateOfBirth}, #{insuranceId})\n")
    void addPatient(Patient patient);

    @Update("UPDATE Patients SET name=#{name}, DOB=#{dateOfBirth}, phone=#{phone} WHERE id=#{id}")
    void updatePatient(Patient Patient);

    @Delete("DELETE FROM Patients WHERE id=#{id}")
    void deletePatient(int id);
}
