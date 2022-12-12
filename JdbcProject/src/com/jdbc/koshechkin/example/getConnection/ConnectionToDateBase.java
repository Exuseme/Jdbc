package com.jdbc.koshechkin.example.getConnection;

import com.jdbc.koshechkin.example.getConnection.util.utilProperties;
import com.jdbc.koshechkin.example.throwException.sqlRuntimeException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDateBase {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";


    static {
        loadDriver();
    }
    public static Connection open() {
        try {
            return DriverManager.getConnection(utilProperties.getKey(URL_KEY), utilProperties.getKey(USERNAME_KEY), utilProperties.getKey(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new sqlRuntimeException(e);

        }
    }
    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
