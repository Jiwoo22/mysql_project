package com.hospital.dao.db.mysql;

import com.hospital.AbstractDAO;
import com.hospital.dao.model.Procedure;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProcedureDAO extends AbstractDAO<Procedure> {
    private ConnectionPool connectionPool;

    public ProcedureDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Procedure findById(int id) {
        Procedure procedure = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Procedures WHERE code = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                procedure = getProcedureFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding procedure by ID", e);
        }
        return procedure;
    }

    @Override
    public List<Procedure> findAll() {
        List<Procedure> procedures = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Procedures")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Procedure procedure = getProcedureFromResultSet(resultSet);
                procedures.add(procedure);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding all procedures", e);
        }
        return procedures;
    }

    @Override
    public void save(Procedure entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Procedure entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Procedures (name, cost) VALUES (?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setFloat(2, entity.getCost());
            statement.executeUpdate();

            // Retrieve the generated ID
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                entity.setCode(generatedId);
            }

        } catch (SQLException e) {
            throw new DAOException("Error while saving procedure", e);
        }
    }

    @Override
    public void update(Procedure entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Procedure entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Procedures SET name = ?, cost = ? WHERE code = ?")) {
            statement.setString(1, entity.getName());
            statement.setFloat(2, entity.getCost());
            statement.setInt(3, entity.getCode());
            statement.executeUpdate();
        } catch (SQLException e) {
                throw new DAOException("Error while updating procedure", e);
            }
        }

        @Override
        public void delete(Procedure entity) {
            if (entity == null) {
                throw new IllegalArgumentException("Procedure entity must not be null");
            }

            try (Connection connection = connectionPool.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "DELETE FROM Procedures WHERE code = ?")) {
                statement.setInt(1, entity.getCode());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException("Error while deleting procedure", e);
            }
        }

        private Procedure getProcedureFromResultSet(ResultSet resultSet) throws SQLException {
            Procedure procedure = new Procedure();
            procedure.setCode(resultSet.getInt("code"));
            procedure.setName(resultSet.getString("name"));
            procedure.setCost(resultSet.getFloat("cost"));
            return procedure;
        }
    }
