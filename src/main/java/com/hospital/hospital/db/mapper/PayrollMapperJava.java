package com.hospital.hospital.db.mapper;

import com.hospital.hospital.model.Payroll;
import org.apache.ibatis.annotations.*;

public interface PayrollMapperJava {

    @Select("SELECT * FROM Payrolls WHERE payroll_id = #{id}")
    @Results({
            @Result(property = "payrollId", column = "payroll_id"),
            @Result(property = "netSalary", column = "net_salary"),
            @Result(property = "bonusSalary", column = "bonus_salary"),
            @Result(property = "accountNumber", column = "account_number")
    })
    Payroll selectPayrollById(int id);

    @Insert("INSERT INTO Payrolls (net_salary, bonus_salary, account_number) VALUES (#{netSalary}, #{bonusSalary}, #{accountNumber})")
    void addPayroll(Payroll payroll);

    @Update("UPDATE Payrolls SET net_salary=#{netSalary}, bonus_salary=#{bonusSalary}, account_number=#{accountNumber} WHERE payroll_id=#{payrollId}")
    void updatePayroll(Payroll payroll);

    @Delete("DELETE FROM Payrolls WHERE payroll_id=#{payrollId}")
    void deletePayroll(int id);
}