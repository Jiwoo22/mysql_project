package com.hospital.hospital.db.mysql;

import com.hospital.GenericDAO;
import com.hospital.hospital.model.Patient;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO implements GenericDAO<Patient> {
    private ConnectionPool connectionPool;
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Patients WHERE patient_id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Patients";
    private static final String INSERT_QUERY = "INSERT INTO Patients (name, phone, DOB, doctor_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE Patients SET name = ?, phone = ?, DOB = ?, doctor_id = ? WHERE patient_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM Patients WHERE patient_id = ?";

    public PatientDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Patient findById(int id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getPatientFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding patient by ID", e);
        }
        return null;
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Patient patient = getPatientFromResultSet(resultSet);
                patients.add(patient);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding all patients", e);
        }
        return patients;
    }

    @Override
    public void save(Patient entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Patient entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            setPatientStatementParameters(statement, entity);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                entity.setId(generatedId);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while saving patient", e);
        }
    }

    @Override
    public void update(Patient entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Patient entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            setPatientStatementParameters(statement, entity);
            statement.setInt(5, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while updating patient", e);
        }
    }

    @Override
    public void delete(Patient entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Patient entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while deleting patient", e);
        }
    }

    private Patient getPatientFromResultSet(ResultSet resultSet) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getInt("patient_id"));
        patient.setName(resultSet.getString("name"));
        patient.setPhone(resultSet.getString("phone"));
        patient.setDateOfBirth(String.valueOf(resultSet.getDate("DOB")));
        patient.setDoctorId(resultSet.getInt("doctor_id"));
        return patient;
    }

    private void setPatientStatementParameters(PreparedStatement statement, Patient entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getPhone());
        statement.setDate(3, java.sql.Date.valueOf(entity.getDateOfBirth()));
        statement.setInt(4, entity.getDoctorId());
    }
}
