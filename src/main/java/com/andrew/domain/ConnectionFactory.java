package com.andrew.domain;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() {
        String databaseUrl = "jdbc:mysql://localhost:3306/anime_store";
        String username = "root";
        String password = "root";

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(databaseUrl, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    public static JdbcRowSet getJdbcRowSet() throws SQLException {
        String databaseUrl = "jdbc:mysql://localhost:3306/anime_store";
        String username = "root";
        String password = "root";
        JdbcRowSet jdbcRowSet = RowSetProvider.newFactory().createJdbcRowSet();
        jdbcRowSet.setUrl(databaseUrl);
        jdbcRowSet.setUsername(username);
        jdbcRowSet.setPassword(password);
        return jdbcRowSet;
    }

    public static CachedRowSet getCachedRowSet() throws SQLException {
        return RowSetProvider.newFactory().createCachedRowSet();
    }

}
