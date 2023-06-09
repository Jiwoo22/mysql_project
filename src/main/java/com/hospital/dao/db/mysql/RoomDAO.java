package com.hospital.dao.db.mysql;

import com.hospital.AbstractDAO;
import com.hospital.dao.model.Block;
import com.hospital.dao.model.Payroll;
import com.hospital.dao.model.Room;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO extends AbstractDAO<Room> {
    private ConnectionPool connectionPool;

    public RoomDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Room findById(int number) {
        Room room = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Rooms WHERE number = ?")) {
            statement.setInt(1, number);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                room = getRoomFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding room by number", e);
        }
        return room;
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Rooms")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Room room = getRoomFromResultSet(resultSet);
                rooms.add(room);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding all rooms", e);
        }
        return rooms;
    }

    @Override
    public void save(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Rooms (number, type, capacity, Block_floor, Block_code) VALUES (?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, room.getNumber());
            statement.setString(2, room.getType());
            statement.setInt(3, room.getCapacity());
            statement.setInt(4, room.getBlock().getFloor());
            statement.setInt(5, room.getBlock().getCode());
            statement.executeUpdate();

            // Retrieve the generated ID
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                room.setNumber(generatedId);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while saving room", e);
        }
    }

    @Override
    public void update(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Rooms SET type = ?, capacity = ?, Block_floor = ?, Block_code = ? WHERE number = ?")) {
            statement.setString(1, room.getType());
            statement.setInt(2, room.getCapacity());
            statement.setInt(3, room.getBlock().getFloor());
            statement.setInt(4, room.getBlock().getCode());
            statement.setInt(5, room.getNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while updating room", e);
        }
    }

    @Override
    public void delete(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room entity must not be null");
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Rooms WHERE number = ?")) {
            statement.setInt(1, room.getNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while deleting room", e);
        }
    }

    private Room getRoomFromResultSet(ResultSet resultSet) throws SQLException {
        Room newRoom = new Room();
        newRoom.setNumber(resultSet.getInt("number"));
        newRoom.setType(resultSet.getString("type"));
        newRoom.setCapacity(resultSet.getInt("capacity"));
        int blockFloor = resultSet.getInt("Block_floor");
        int blockCode = resultSet.getInt("Block_code");
        Block block = new Block();
        block.setFloor(blockFloor);
        block.setCode(blockCode);
        newRoom.setBlock(block);
        return newRoom;
    }
}
