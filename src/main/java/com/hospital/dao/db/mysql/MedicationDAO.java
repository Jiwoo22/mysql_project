package com.hospital.dao.db.mysql;

import com.hospital.AbstractDAO;
import com.hospital.dao.model.Medication;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicationDAO extends AbstractDAO<Medication> {
    private ConnectionPool connectionPool;

    public MedicationDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Medication findById(int id) {
        Medication medication = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Medications WHERE medication_id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                medication = getMedicationFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding medication by id", e);
        }
        return medication;
    }

    @Override
    public List<Medication> findAll() {
        List<Medication> medications = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Medications")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Medication medication = getMedicationFromResultSet(resultSet);
                medications.add(medication);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding all medications", e);
        }
        return medications;
    }

    @Override
    public void save(Medication entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Medication entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Medications (name, brand, description) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getBrand());
            statement.setString(3, entity.getDescription());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setMedicationId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new DAOException("Error while saving medication", e);
        }
    }

    @Override
    public void update(Medication entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Medication entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Medications SET name = ?, brand = ?, description = ? WHERE medication_id = ?")) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getBrand());
            statement.setString(3, entity.getDescription());
            statement.setInt(4, entity.getMedicationId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while updating medication", e);
        }
    }

    @Override
    public void delete(Medication entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Medication entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM Medications WHERE medication_id = ?")) {
            statement.setInt(1, entity.getMedicationId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while deleting medication", e);
        }
    }

    private Medication getMedicationFromResultSet(ResultSet resultSet) throws SQLException {
        Medication medication = new Medication();
        medication.setMedicationId(resultSet.getInt("medication_id"));
        medication.setName(resultSet.getString("name"));
        medication.setBrand(resultSet.getString("brand"));
        medication.setDescription(resultSet.getString("description"));
        return medication;
    }
}