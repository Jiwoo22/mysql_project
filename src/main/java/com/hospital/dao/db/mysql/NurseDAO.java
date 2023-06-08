package com.hospital.dao.db.mysql;

import com.hospital.GenericDAO;
import com.hospital.dao.model.Nurse;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NurseDAO implements GenericDAO<Nurse> {
    private ConnectionPool connectionPool;

    public NurseDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Nurse findById(int id) {
        Nurse nurse = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Nurses WHERE nurse_id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                nurse = getNurseFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding nurse by ID", e);
        }
        return nurse;
    }

    @Override
    public List<Nurse> findAll() {
        List<Nurse> nurses = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Nurses")) {
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
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Nurses (nurse_id, name, email, employee_id) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getNurseId());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getEmail());
            statement.setInt(4, entity.getEmployeeId());
            statement.executeUpdate();

            // Retrieve the generated ID
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
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Nurses SET name = ?, email = ?, employee_id = ? WHERE nurse_id = ?")) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            statement.setInt(3, entity.getEmployeeId());
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
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Nurses WHERE nurse_id = ?")) {
            statement.setInt(1, entity.getNurseId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while deleting nurse", e);
        }
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






