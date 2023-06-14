package com.hospital.hospital.db.mysql;

import com.hospital.AbstractDAO;
import com.hospital.hospital.model.Block;
import com.hospital.hospital.model.Room;
import com.hospital.exceptions.DAOException;
import com.hospital.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO extends AbstractDAO<Room> {
    private ConnectionPool connectionPool;

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM Rooms WHERE number = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM Rooms";
    private static final String INSERT_QUERY = "INSERT INTO Rooms (type, capacity, Block_floor, Block_code) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE Rooms SET type = ?, capacity = ?, Block_floor = ?, Block_code = ? WHERE number = ?";
    private static final String DELETE_QUERY = "DELETE FROM Rooms WHERE number = ?";

    public RoomDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Room findById(int number) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     FIND_BY_ID_QUERY)) {
            statement.setInt(1, number);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getRoomFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding room by number", e);
        }
        return null;
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     FIND_ALL_QUERY)) {
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
                     INSERT_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            setRoomStatementParameters(statement, room);
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
                     UPDATE_QUERY)) {
            setRoomStatementParameters(statement, room);
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
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, room.getNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while deleting room", e);
        }
    }

    private void setRoomStatementParameters(PreparedStatement statement, Room room) throws SQLException {
        statement.setString(1, room.getType());
        statement.setInt(2, room.getCapacity());
        statement.setInt(3, room.getBlock().getFloor());
        statement.setInt(4, room.getBlock().getCode());
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
