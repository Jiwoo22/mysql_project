package com.hospital.dao.db.mysql;

import com.hospital.AbstractDAO;
import com.hospital.dao.model.Appointment;
import com.hospital.dao.model.Doctor;
import com.hospital.dao.model.Nurse;
import com.hospital.dao.model.Patient;
import com.hospital.dao.model.Room;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO extends AbstractDAO<Appointment> {
    private ConnectionPool connectionPool;
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Appointments WHERE appointment_id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Appointments";
    private static final String INSERT_QUERY = "INSERT INTO Appointments (room_id, start_time, end_time, doctor_id, nurse_id, patient_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE Appointments SET room_id = ?, start_time = ?, end_time = ?, doctor_id = ?, nurse_id = ?, patient_id = ? WHERE appointment_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM Appointments WHERE appointment_id = ?";

    public AppointmentDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Appointment findById(int id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getAppointmentFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding appointment by id", e);
        }
        return null;
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
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
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            setAppointmentStatementParameters(statement, entity);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating appointment failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    entity.setAppointmentId(generatedId);
                }

            } catch (SQLException e) {
                throw new DAOException("Creating appointment failed", e);
            }

        } catch (SQLException e) {
            throw new DAOException("Error while saving appointment", e);
        }
    }

    @Override
    public void update(Appointment entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Appointment entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            setAppointmentStatementParameters(statement, entity);
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
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, entity.getAppointmentId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error while deleting appointment", e);
        }
    }

    private void setAppointmentStatementParameters(PreparedStatement statement, Appointment entity) throws SQLException {
        statement.setInt(1, entity.getRoom().getNumber());
        statement.setTimestamp(2, entity.getStartTime());
        statement.setTimestamp(3, entity.getEndTime());
        statement.setInt(4, entity.getDoctor().getDoctorId());
        statement.setInt(5, entity.getNurse().getNurseId());
        statement.setLong(6, entity.getPatient().getId());
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
