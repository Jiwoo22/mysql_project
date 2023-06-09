package com.hospital.dao.db.mysql;

import com.hospital.AbstractDAO;
import com.hospital.dao.model.*;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO extends AbstractDAO<Appointment> {
    private ConnectionPool connectionPool;

    public AppointmentDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Appointment findById(int id) {
        Appointment appointment = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Appointments WHERE appointment_id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                appointment = getAppointmentFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding appointment by id", e);
        }
        return appointment;
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Appointments")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Appointment appointment = getAppointmentFromResultSet(resultSet);
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding all appointments", e);
        }
        return appointments;
    }

    @Override
    public void save(Appointment entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Appointment entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Appointments (room_id, start_time, end_time, doctor_id, nurse_id, patient_id) " +
                             "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getRoom().getNumber());
            statement.setTimestamp(2, entity.getStartTime());
            statement.setTimestamp(3, entity.getEndTime());
            statement.setInt(4, entity.getDoctor().getDoctorId());
            statement.setInt(5, entity.getNurse().getNurseId());
            statement.setLong(6, entity.getPatient().getId());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating appointment failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    entity.setAppointmentId(generatedId);
                }
//                else {
//                    throw new DAOException("Creating appointment failed, no ID obtained.");
//                }
            } catch (SQLException e) {
                throw new DAOException("Creating appointment failed", e);
            }

        } catch (SQLException e) {
            throw new DAOException("Error while saving appointment", e);
        }
    }

//
//    @Override
//    public void save(Appointment entity) {
//        if (entity == null) {
//            throw new IllegalArgumentException("Appointment entity must not be null");
//        }
//
//        try (Connection connection = connectionPool.getConnection();
//             PreparedStatement statement = connection.prepareStatement(
//                     "INSERT INTO Appointments (room_id, start_time, end_time, doctor_id, nurse_id, patient_id) " +
//                             "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
//            statement.setInt(1, entity.getRoom().getNumber());
//            statement.setTimestamp(2, entity.getStartTime());
//            statement.setTimestamp(3, entity.getEndTime());
//            statement.setInt(4, entity.getDoctor().getDoctorId());
//            statement.setInt(5, entity.getNurse().getNurseId());
//            statement.setLong(6, entity.getPatient().getId());
//            statement.executeUpdate();
//
//            // Retrieve the generated ID
//            ResultSet generatedKeys = statement.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                int generatedId = generatedKeys.getInt(1);
//                entity.setAppointmentId(generatedId);
//            }
//
//        } catch (SQLException e) {
//            throw new DAOException("Error while saving appointment", e);
//        }
//    }

    @Override
    public void update(Appointment entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Appointment entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Appointments SET room_id = ?, start_time = ?, end_time = ?, doctor_id = ?, " +
                             "nurse_id = ?, patient_id = ? WHERE appointment_id = ?")) {
            statement.setInt(1, entity.getRoom().getNumber());
            statement.setTimestamp(2, entity.getStartTime());
            statement.setTimestamp(3, entity.getEndTime());
            statement.setInt(4, entity.getDoctor().getDoctorId());
            statement.setInt(5, entity.getNurse().getNurseId());
            statement.setLong(6, entity.getPatient().getId());
            statement.setInt(7, entity.getAppointmentId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error while updating appointment", e);
        }
    }

    @Override
    public void delete(Appointment entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Appointment entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM Appointments WHERE appointment_id = ?")) {
            statement.setInt(1, entity.getAppointmentId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error while deleting appointment", e);
        }
    }

    private Appointment getAppointmentFromResultSet(ResultSet resultSet) throws SQLException {
        int appointmentId = resultSet.getInt("appointment_id");
        int roomId = resultSet.getInt("room_id");
        Timestamp startTime = resultSet.getTimestamp("start_time");
        Timestamp endTime = resultSet.getTimestamp("end_time");
        int doctorId = resultSet.getInt("doctor_id");
        int nurseId = resultSet.getInt("nurse_id");
        long patientId = resultSet.getLong("patient_id");

        RoomDAO roomDAO = new RoomDAO(connectionPool);
        Room room = roomDAO.findById(roomId);

        DoctorDAO doctorDAO = new DoctorDAO(connectionPool);
        Doctor doctor = doctorDAO.findById(doctorId);

        NurseDAO nurseDAO = new NurseDAO(connectionPool);
        Nurse nurse = nurseDAO.findById(nurseId);

        PatientDAO patientDAO = new PatientDAO(connectionPool);
        Patient patient = patientDAO.findById((int) patientId);

        return new Appointment(appointmentId, room, startTime, endTime, doctor, nurse, patient);
    }
}
