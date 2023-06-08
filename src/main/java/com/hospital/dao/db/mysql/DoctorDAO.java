package com.hospital.dao.db.mysql;

import com.hospital.AbstractDAO;
import com.hospital.dao.model.Doctor;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO extends AbstractDAO<Doctor> {
    private ConnectionPool connectionPool; // JDBC connection object;

    public DoctorDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Doctor findById(int id) {
        Doctor doctor = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Doctors where doctor_id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                doctor = getDoctorFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding doctor by id", e);
        }
        return doctor;
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Doctors")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Doctor doctor = getDoctorFromResultSet(resultSet);
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding all doctors", e);
        }
        return doctors;
    }

    @Override
    public void save(Doctor entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Doctor entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Doctors (doctor_id, name, specialization, email, employee_id) VALUES (?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getDoctorId());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getSpecialization());
            statement.setString(4, entity.getEmail());
            statement.setInt(5, entity.getEmployeeId());
            statement.executeUpdate();

            // Retrieve the generated ID
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                entity.setDoctorId(generatedId);
            };
        } catch (SQLException e) {
            throw new DAOException("Error while saving doctor", e);
        }
    }

    @Override
    public void update(Doctor entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Doctor entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Doctors SET name = ?, specialization = ?, email = ?, employee_id = ? WHERE doctor_id = ?")) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSpecialization());
            statement.setString(3, entity.getEmail());
            statement.setInt(4, entity.getEmployeeId());
            statement.setInt(5, entity.getDoctorId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while updating doctor", e);
        }
    }

    @Override
    public void delete(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Doctors where doctor_id =?")) {
            statement.setInt(1, doctor.getDoctorId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while deleting doctor", e);
        }
    }

    private Doctor getDoctorFromResultSet(ResultSet resultSet) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(resultSet.getInt("doctor_id"));
        doctor.setName(resultSet.getString("name"));
        doctor.setSpecialization(resultSet.getString("specialization"));
        doctor.setEmail(resultSet.getString("email"));
        doctor.setEmployeeId(resultSet.getInt("employee_id"));
        return doctor;
    }
}

