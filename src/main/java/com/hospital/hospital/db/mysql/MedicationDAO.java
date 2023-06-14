package com.hospital.hospital.db.mysql;

import com.hospital.AbstractDAO;
import com.hospital.hospital.model.Medication;
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
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Medications WHERE medication_id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Medications";
    private static final String INSERT_QUERY = "INSERT INTO Medications (name, brand, description) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE Medications SET name = ?, brand = ?, description = ? WHERE medication_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM Medications WHERE medication_id = ?";

    public MedicationDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Medication findById(int id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getMedicationFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding medication by id", e);
        }
        return null;
    }

    @Override
    public List<Medication> findAll() {
        List<Medication> medications = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
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
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            setMedicationStatementParameters(statement, entity);
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
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            setMedicationStatementParameters(statement, entity);
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
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, entity.getMedicationId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while deleting medication", e);
        }
    }

    private void setMedicationStatementParameters(PreparedStatement statement, Medication entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getBrand());
        statement.setString(3, entity.getDescription());
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
