package com.hospital.hospital.db.mysql;

import com.hospital.AbstractDAO;
import com.hospital.hospital.model.Procedure;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProcedureDAO extends AbstractDAO<Procedure> {
    private ConnectionPool connectionPool;

    private static final String SQL_FIND_BY_ID = "SELECT * FROM Procedures WHERE code = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM Procedures";
    private static final String SQL_SAVE = "INSERT INTO Procedures (name, cost) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE Procedures SET name = ?, cost = ? WHERE code = ?";
    private static final String SQL_DELETE = "DELETE FROM Procedures WHERE code = ?";

    public ProcedureDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Procedure findById(int id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     SQL_FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getProcedureFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding procedure by ID", e);
        }
        return null;
    }

    @Override
    public List<Procedure> findAll() {
        List<Procedure> procedures = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     SQL_FIND_ALL)) {
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
                     SQL_SAVE,
                     Statement.RETURN_GENERATED_KEYS)) {
            setProcedureStatementParameters(statement, entity);
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
                    SQL_UPDATE )) {
            setProcedureStatementParameters(statement, entity);
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
                         SQL_DELETE)) {
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

        private void setProcedureStatementParameters(PreparedStatement statement, Procedure entity) throws SQLException {
            statement.setString(1, entity.getName());
            statement.setFloat(2, entity.getCost());
        }
    }
