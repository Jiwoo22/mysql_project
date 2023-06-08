package com.hospital.dao.db.mysql;

import com.hospital.AbstractDAO;
import com.hospital.dao.model.Payroll;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollDAO extends AbstractDAO<Payroll> {
    private ConnectionPool connectionPool;

    public PayrollDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Payroll findById(int id) {
        Payroll payroll = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Payrolls where payroll_id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                payroll = getPayrollFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding payroll by id", e);
        }
        return payroll;
    }

    public Payroll findByAcctNum(int number) {
        Payroll payroll = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Payrolls where account_number = ?")) {
            statement.setInt(1, number);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                payroll = getPayrollFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding payroll by account number", e);
        }
        return payroll;
    }

    @Override
    public List<Payroll> findAll() {
        List<Payroll> payrolls = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Payrolls")) {
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
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Payrolls (payroll_id, net_salary, bonus_salary, account_number) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getPayrollId());
            statement.setFloat(2, entity.getNetSalary());
            statement.setFloat(3, entity.getBonusSalary());
            statement.setInt(4, entity.getAccountNumber());
            statement.executeUpdate();

            // Retrieve the generated ID
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
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Payrolls SET net_salary = ?, bonus_salary = ?, account_number = ? WHERE payroll_id = ?")) {
            statement.setFloat(1, entity.getNetSalary());
            statement.setFloat(2, entity.getBonusSalary());
            statement.setInt(3, entity.getAccountNumber());
            statement.setInt(4, entity.getPayrollId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error while updating payroll", e);
        }
    }

    @Override
    public void delete(Payroll payroll) {
        if (payroll == null) {
            throw new IllegalArgumentException("Payroll entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Payrolls where payroll_id = ?")) {
            statement.setInt(1, payroll.getPayrollId());
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
}
