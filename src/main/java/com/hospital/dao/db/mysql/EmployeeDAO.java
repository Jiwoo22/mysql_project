package com.hospital.dao.db.mysql;

import com.hospital.AbstractDAO;
import com.hospital.dao.model.Employee;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO extends AbstractDAO<Employee> {
    private ConnectionPool connectionPool; // JDBC connection object;

    public EmployeeDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Employee findById(int id) {
        Employee employee = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Employees where employee_id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                employee = getEmployeeFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding employee by id", e);
        }
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Employees")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Employee employee = getEmployeeFromResultSet(resultSet);
                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding all employees", e);
        }
        return employees;
    }

    @Override
    public void save(Employee entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Employee entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Employees (employee_id, name, DOB, payroll_id) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getEmployeeId());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getDateOfBirth());
            statement.setInt(4, entity.getPayrollId());
            statement.executeUpdate();

            // Retrieve the generated ID
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                entity.setEmployeeId(generatedId);
            }

        } catch (SQLException e) {
            throw new DAOException("Error while saving employee", e);
        }
    }

    @Override
    public void update(Employee entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Employee entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Employees SET name = ?, DOB = ?, payroll_id = ? WHERE employee_id = ?")) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDateOfBirth());
            statement.setInt(3, entity.getPayrollId());
            statement.setInt(4, entity.getEmployeeId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while updating employee", e);
        }
    }

    @Override
    public void delete(Employee entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Employee entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Employees where employee_id =?")) {
            statement.setInt(1, entity.getEmployeeId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while deleting employee", e);
        }
    }


    private Employee getEmployeeFromResultSet(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeId(resultSet.getInt("employee_id"));
        employee.setName(resultSet.getString("name"));
        employee.setDateOfBirth(resultSet.getString("DOB"));
        employee.setPayrollId(resultSet.getInt("payroll_id"));
        return employee;
    }
}
