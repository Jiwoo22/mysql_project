package com.hospital.dao.db.mysql;

import com.hospital.AbstractDAO;
import com.hospital.dao.model.Patient;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO extends AbstractDAO<Patient> {
    private ConnectionPool connectionPool; // JDBC connection object;

    public PatientDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Patient findById(int id) {
        Patient patient = null;
        try (Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Patients where patient_id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                patient = getPatientFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding patient by id",e);
        }
        return patient;
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Patients")) {
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
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Patients (patient_id, name, phone, DOB, doctor_id, insurance_id) VALUES (?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getId());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getPhone());
            statement.setDate(4,java.sql.Date.valueOf(entity.getDateOfBirth()));
            statement.setInt(5, entity.getDoctorId());
            statement.setInt(6, entity.getInsuranceId());
            statement.executeUpdate();

            // Retrieve the generated ID
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                entity.setId(generatedId);
            }

        } catch (SQLException e) {
            throw new DAOException("Error while saving patient",e);
        }
    }

    @Override
    public void update(Patient entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Patient entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Patients SET name = ?, phone = ?, DOB = ?, doctor_id = ?, insurance_id = ? WHERE patient_id = ?")) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getPhone());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getDateOfBirth()));
            statement.setInt(4, entity.getDoctorId());
            statement.setInt(5, entity.getInsuranceId());
            statement.setInt(6, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while updating patient", e);
        }
    }

    @Override
    public void delete(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Patients where id =?")) {
            statement.setInt(1, patient.getId());
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
        patient.setDateOfBirth(String.valueOf(resultSet.getTimestamp("DOB")));
        patient.setDoctorId(resultSet.getInt("doctor_id"));
        patient.setInsuranceId(resultSet.getInt("insurance_id"));
        return patient;
    }
}
