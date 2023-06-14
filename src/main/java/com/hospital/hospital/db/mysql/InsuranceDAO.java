package com.hospital.hospital.db.mysql;

import com.hospital.GenericDAO;
import com.hospital.hospital.model.Insurance;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsuranceDAO implements GenericDAO<Insurance> {
    private ConnectionPool connectionPool;
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Insurance WHERE Insurance_id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Insurance";
    private static final String INSERT_QUERY = "INSERT INTO Insurance (type, expiration_date) VALUES (?, ?)";

    public InsuranceDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Insurance findById(int id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getInsuranceFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding insurance by ID", e);
        }
        return null;
    }

    @Override
    public List<Insurance> findAll() {
        List<Insurance> insurances = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
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
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            setInsuranceStatementParameters(statement, entity);
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
            setInsuranceStatementParameters(statement, entity);
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

    private void setInsuranceStatementParameters(PreparedStatement statement, Insurance entity) throws SQLException {
        statement.setString(1, entity.getType());
        statement.setString(2, entity.getExpirationDate());
    }
}
