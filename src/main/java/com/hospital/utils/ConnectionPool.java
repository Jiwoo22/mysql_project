package com.hospital.utils;


import com.hospital.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ConnectionPool {

    private int maxConnections;
    private String url;
    private String user;
    private String password;

    private List<Connection> connections;

    private final Object lock = new Object(); // Monitor lock object


    public ConnectionPool(String url, String user, String password, int maxConnections) throws Exception {
        this.url = url;
        this.user = user;
        this.password = password;
        this.maxConnections = maxConnections;

        connections = new ArrayList<>();
        initializePool();
    }

    private void initializePool() throws Exception {
        try {
            for (int i = 0; i < maxConnections; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                connections.add(connection);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while initializing connection pool", e);
        }
    }

    public Connection getConnection() {
        synchronized (lock) {
            while (connections.isEmpty()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new DAOException("Error while getting connection", e);
                }
            }
            // Return a connection from the pool
            return connections.remove(connections.size() - 1);
        }
    }

    public void releaseConnection(Connection connection) {
        synchronized (lock) {
            // Add the connection back to the pool
            connections.add(connection);
            lock.notifyAll();
        }
    }
}
