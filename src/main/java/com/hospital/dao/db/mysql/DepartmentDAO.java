package com.hospital.dao.db.mysql;

import com.hospital.AbstractDAO;
import com.hospital.dao.model.Department;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO extends AbstractDAO<Department> {
    private ConnectionPool connectionPool;

    public DepartmentDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Department findById(int id) {
        Department department = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Departments WHERE department_id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                department = getDepartmentFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding department by ID", e);
        }
        return department;
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Departments")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Department department = getDepartmentFromResultSet(resultSet);
                departments.add(department);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding all departments", e);
        }
        return departments;
    }

    @Override
    public void save(Department entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Department entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Departments (name, head) VALUES (?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getHead());
            statement.executeUpdate();

            // Retrieve the generated ID
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                entity.setDepartmentId(generatedId);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while saving department", e);
        }
    }

    @Override
    public void update(Department entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Department entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Departments SET name = ?, head = ? WHERE department_id = ?")) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getHead());
            statement.setInt(3, entity.getDepartmentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while updating department", e);
        }
    }

    @Override
    public void delete(Department entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Department entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM Departments WHERE department_id = ?")) {
            statement.setInt(1, entity.getDepartmentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while deleting department", e);
        }
    }

    private Department getDepartmentFromResultSet(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setDepartmentId(resultSet.getInt("department_id"));
        department.setName(resultSet.getString("name"));
        department.setHead(resultSet.getInt("head"));
        return department;
    }
}
