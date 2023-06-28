package com.hospital.hospital.db.mapper;

import com.hospital.hospital.model.Nurse;
import org.apache.ibatis.annotations.*;

public interface NurseMapperJava {

    @Select("SELECT * FROM Nurses WHERE nurse_id = #{id}")
    @Results({
            @Result(property = "nurseId", column = "nurse_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "employeeId", column = "employee_id")
    })
    Nurse selectNurseById(int id);

    @Insert("INSERT INTO Nurses (name, email, employee_id) VALUES (#{name}, #{email}, #{employeeId})")
    void addNurse(Nurse nurse);

    @Update("UPDATE Nurses SET name=#{name}, email=#{email}, employee_id=#{employeeId} WHERE nurse_id=#{nurseId}")
    void updateNurse(Nurse nurse);

    @Delete("DELETE FROM Nurses WHERE nurse_id=#{nurseId}")
    void deleteNurse(int id);
}
