package com.hospital.dao.db.mysql;

import com.hospital.GenericDAO;
import com.hospital.dao.model.Payroll;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollDAO implements GenericDAO<Payroll> {
    private ConnectionPool connectionPool;
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Payrolls WHERE payroll_id = ?";
    private static final String SELECT_BY_ACCT_NUM_QUERY = "SELECT * FROM Payrolls WHERE account_number = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Payrolls";
    private static final String INSERT_QUERY = "INSERT INTO Payrolls (net_salary, bonus_salary, account_number) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE Payrolls SET net_salary = ?, bonus_salary = ?, account_number = ? WHERE payroll_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM Payrolls WHERE payroll_id = ?";

    public PayrollDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Payroll findById(int id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getPayrollFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding payroll by ID", e);
        }
        return null;
    }

    public Payroll findByAccountNumber(int accountNumber) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ACCT_NUM_QUERY)) {
            statement.setInt(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getPayrollFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding payroll by account number", e);
        }
        return null;
    }

    @Override
    public List<Payroll> findAll() {
        List<Payroll> payrolls = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Payroll payroll = getPayrollFromResultSet(resultSet);
                payrolls.add(payroll);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding all payrolls", e);
        }
        return payrolls;
    }

    @Override
    public void save(Payroll entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Payroll entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            setPayrollStatementParameters(statement, entity);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                entity.setPayrollId(generatedId);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while saving payroll", e);
        }
    }

    @Override
    public void update(Payroll entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Payroll entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            setPayrollStatementParameters(statement, entity);
            statement.setInt(4, entity.getPayrollId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while updating payroll", e);
        }
    }

    @Override
    public void delete(Payroll entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Payroll entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, entity.getPayrollId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while deleting payroll", e);
        }
    }

    private Payroll getPayrollFromResultSet(ResultSet resultSet) throws SQLException {
        Payroll payroll = new Payroll();
        payroll.setPayrollId(resultSet.getInt("payroll_id"));
        payroll.setNetSalary(resultSet.getFloat("net_salary"));
        payroll.setBonusSalary(resultSet.getFloat("bonus_salary"));
        payroll.setAccountNumber(resultSet.getInt("account_number"));
        return payroll;
    }

    private void setPayrollStatementParameters(PreparedStatement statement, Payroll entity) throws SQLException {
        statement.setFloat(1, entity.getNetSalary());
        statement.setFloat(2, entity.getBonusSalary());
        statement.setInt(3, entity.getAccountNumber());
    }
}
