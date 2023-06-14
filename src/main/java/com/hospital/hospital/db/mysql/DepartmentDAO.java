package com.hospital.hospital.db.mysql;

import com.hospital.AbstractDAO;
import com.hospital.hospital.model.Department;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO extends AbstractDAO<Department> {
    private ConnectionPool connectionPool;

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Departments WHERE department_id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Departments";
    private static final String INSERT_QUERY = "INSERT INTO Departments (name, head) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE Departments SET name = ?, head = ? WHERE department_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM Departments WHERE department_id = ?";

    public DepartmentDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Department findById(int id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getDepartmentFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding department by ID", e);
        }
        return null;
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     SELECT_ALL_QUERY)) {
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
                     INSERT_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            setDepartmentStatementParameters(statement, entity);
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
                     UPDATE_QUERY)) {
            setDepartmentStatementParameters(statement, entity);
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
                     DELETE_QUERY)) {
            statement.setInt(1, entity.getDepartmentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while deleting department", e);
        }
    }

    private void setDepartmentStatementParameters(PreparedStatement statement, Department department) throws SQLException {
        statement.setString(1, department.getName());
        statement.setInt(2, department.getHead());
    }

    private Department getDepartmentFromResultSet(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setDepartmentId(resultSet.getInt("department_id"));
        department.setName(resultSet.getString("name"));
        department.setHead(resultSet.getInt("head"));
        return department;
    }
}
