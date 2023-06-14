package com.hospital.hospital.db.mysql;

import com.hospital.AbstractDAO;
import com.hospital.hospital.model.Block;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlockDAO extends AbstractDAO<Block> {
    private ConnectionPool connectionPool;

    public BlockDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Block findByFloorAndCode(int floor, int code) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Block WHERE floor = ? AND code = ?")) {
            statement.setInt(1, floor);
            statement.setInt(2, code);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getBlockFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding block by floor and code", e);
        }
        return null;
    }

    @Override
    public Block findById(int id) {
        return null;
    }

    @Override
    public List<Block> findAll() {
        List<Block> blocks = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Block")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Block block = getBlockFromResultSet(resultSet);
                blocks.add(block);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding all blocks", e);
        }
        return blocks;
    }

    @Override
    public void save(Block entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Block entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Block (floor, code) VALUES (?, ?)")) {
            setBlockStatementParameters(statement, entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while saving block", e);
        }
    }

    @Override
    public void update(Block entity) {
        return;
    }

    public void update(Block entity, int prevFloor, int prevCode) {
        if (entity == null) {
            throw new IllegalArgumentException("Block entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Block SET floor = ?, code = ? WHERE floor = ? AND code = ?")) {
            setBlockStatementParameters(statement, entity);
            statement.setInt(3, prevFloor);
            statement.setInt(4, prevCode);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while updating block", e);
        }
    }

    @Override
    public void delete(Block entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Block entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM Block WHERE floor = ? AND code = ?")) {
            setBlockStatementParameters(statement, entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while deleting block", e);
        }
    }

    private void setBlockStatementParameters(PreparedStatement statement, Block entity) throws SQLException {
        statement.setInt(1, entity.getFloor());
        statement.setInt(2, entity.getCode());
    }
    private Block getBlockFromResultSet(ResultSet resultSet) throws SQLException {
        Block block = new Block();
        block.setFloor(resultSet.getInt("floor"));
        block.setCode(resultSet.getInt("code"));
        return block;
    }
}
