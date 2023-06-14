package com.hospital.hospital.db.mysql;

import com.hospital.GenericDAO;
import com.hospital.hospital.model.Nurse;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NurseDAO implements GenericDAO<Nurse> {
    private ConnectionPool connectionPool;
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Nurses WHERE nurse_id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Nurses";
    private static final String INSERT_QUERY = "INSERT INTO Nurses (name, email, employee_id) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE Nurses SET name = ?, email = ?, employee_id = ? WHERE nurse_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM Nurses WHERE nurse_id = ?";

    public NurseDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Nurse findById(int id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getNurseFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding nurse by ID", e);
        }
        return null;
    }

    @Override
    public List<Nurse> findAll() {
        List<Nurse> nurses = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Nurse nurse = getNurseFromResultSet(resultSet);
                nurses.add(nurse);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding all nurses", e);
        }
        return nurses;
    }

    @Override
    public void save(Nurse entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Nurse entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            setNurseStatementParameters(statement, entity);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                entity.setNurseId(generatedId);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while saving nurse", e);
        }
    }

    @Override
    public void update(Nurse entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Nurse entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            setNurseStatementParameters(statement, entity);
            statement.setInt(4, entity.getNurseId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while updating nurse", e);
        }
    }

    @Override
    public void delete(Nurse entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Nurse entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, entity.getNurseId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while deleting nurse", e);
        }
    }

    private void setNurseStatementParameters(PreparedStatement statement, Nurse entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getEmail());
        statement.setInt(3, entity.getEmployeeId());
    }

    private Nurse getNurseFromResultSet(ResultSet resultSet) throws SQLException {
        Nurse nurse = new Nurse();
        nurse.setNurseId(resultSet.getInt("nurse_id"));
        nurse.setName(resultSet.getString("name"));
        nurse.setEmail(resultSet.getString("email"));
        nurse.setEmployeeId(resultSet.getInt("employee_id"));
        return nurse;
    }
}
