package org.example.presenters.database;

import org.example.configs.database.DatabaseInfos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static DatabaseConnector databaseConnection;
    private Connection connection;

    public static DatabaseConnector getConnector() {
        if (databaseConnection == null)
            databaseConnection = new DatabaseConnector();
        return databaseConnection;
    }

    public Connection startConnection() throws SQLException {
        this.connection = DriverManager.getConnection(DatabaseInfos.url, DatabaseInfos.userName, DatabaseInfos.password);
        return this.connection;
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }
}
