package com.hospital.hospital.db.mapper;

import com.hospital.hospital.model.Employee;
import org.apache.ibatis.annotations.*;

public interface EmployeeMapperJava {

    @Select("SELECT * FROM Employees WHERE employee_id = #{id}")
    @Results({
            @Result(property = "employeeId", column = "employee_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "dateOfBirth", column = "DOB"),
            @Result(property = "payrollId", column = "payroll_id")
    })
    Employee selectEmployeeById(int id);

    @Insert("INSERT INTO Employees (name, DOB, payroll_id) VALUES (#{name}, #{dateOfBirth}, #{payrollId})")
    void addEmployee(Employee employee);

    @Update("UPDATE Employees SET name=#{name}, DOB=#{dateOfBirth}, payroll_id=#{payrollId} WHERE employee_id=#{employeeId}")
    void updateEmployee(Employee employee);

    @Delete("DELETE FROM Employees WHERE employee_id=#{employeeId}")
    void deleteEmployee(int id);
}