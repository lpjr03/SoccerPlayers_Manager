package it.unimol.player_manager.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreConnection {

    private String dbName;
    private String user;
    private String pass;
    private String urlCalcio;
    private static PostgreConnection instance;
    private Connection connection;

    public static PostgreConnection getInstance() {
        if (instance == null) {
            instance = new PostgreConnection();
        }
        return instance;
    }

    private PostgreConnection() {
        dbName = "calcio";
        user = "lelio";
        pass = "Oilel2025+";
        urlCalcio = "jdbc:postgresql://localhost:5432/calcio";
        try {
            connection = DriverManager.getConnection(urlCalcio, user, pass);
            createDatabaseIfNotExists();
            createPlayersTable(connection);
            System.out.println("Connected to 'calcio' and checked/created table 'players'.");
        } catch (SQLException e) {
            System.out.println("Connection to 'calcio' failed: " + e.getMessage());
        }
    }

    private boolean createPlayersTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS players (" +
                "id SERIAL PRIMARY KEY, " +
                "first_name VARCHAR(50) NOT NULL, " +
                "last_name VARCHAR(50) NOT NULL, " +
                "birth_date DATE NOT NULL, " +
                "nationality VARCHAR(50) NOT NULL, " +
                "jersey_number INTEGER NOT NULL, " +
                "abilities JSONB NOT NULL" +
                ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Table 'players' checked/created.");
            return true;
        } catch (SQLException e) {
            System.out.println("Table creation failed: " + e.getMessage());
        }
        return false;
    }

    private void createDatabaseIfNotExists() throws SQLException {

        Statement stmt = connection.createStatement();
        {
            String checkDb = "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'";
            var rs = stmt.executeQuery(checkDb);
            if (!rs.next()) {
                String sql = "CREATE DATABASE " + dbName;
                stmt.executeUpdate(sql);
                System.out.println("Database '" + dbName + "' created successfully!");
            } else {
                System.out.println("Database '" + dbName + "' already exists.");
            }

        }
    }
}
