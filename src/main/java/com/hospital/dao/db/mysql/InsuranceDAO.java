package com.hospital.dao.db.mysql;

import com.hospital.GenericDAO;
import com.hospital.dao.model.Insurance;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsuranceDAO implements GenericDAO<Insurance> {
    private ConnectionPool connectionPool;

    public InsuranceDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Insurance findById(int id) {
        Insurance insurance = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Insurance WHERE Insurance_id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                insurance = getInsuranceFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding insurance by ID", e);
        }
        return insurance;
    }

    @Override
    public List<Insurance> findAll() {
        List<Insurance> insurances = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Insurance")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Insurance insurance = getInsuranceFromResultSet(resultSet);
                insurances.add(insurance);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding all insurances", e);
        }
        return insurances;
    }

    @Override
    public void save(Insurance entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Insurance entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Insurance (insurance_id, type, expiration_date) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getInsuranceId());
            statement.setString(2, entity.getType());
            statement.setString(3, entity.getExpirationDate());
            statement.executeUpdate();

            // Retrieve the generated ID
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                entity.setInsuranceId(generatedId);
            }

        } catch (SQLException e) {
            throw new DAOException("Error while saving insurance", e);
        }
    }

    @Override
    public void update(Insurance entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Insurance entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Insurance SET type = ?, expiration_date = ? WHERE Insurance_id = ?")) {
            statement.setString(1, entity.getType());
            statement.setString(2, entity.getExpirationDate());
            statement.setInt(3, entity.getInsuranceId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while updating insurance", e);
        }
    }

    @Override
    public void delete(Insurance entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Insurance entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM Insurance WHERE Insurance_id = ?")) {
            statement.setInt(1, entity.getInsuranceId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while deleting insurance", e);
        }
    }


    private Insurance getInsuranceFromResultSet(ResultSet resultSet) throws SQLException {
        Insurance insurance = new Insurance();
        insurance.setInsuranceId(resultSet.getInt("Insurance_id"));
        insurance.setType(resultSet.getString("type"));
        insurance.setExpirationDate(resultSet.getString("expiration_date"));
        return insurance;
    }
}
