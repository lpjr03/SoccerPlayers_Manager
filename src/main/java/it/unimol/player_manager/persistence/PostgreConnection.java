package it.unimol.player_manager.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import io.github.cdimascio.dotenv.Dotenv;
import it.unimol.player_manager.entity.Player;

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
        dbName = Dotenv.load().get("POSTGRES_DB");
        user = Dotenv.load().get("POSTGRES_USER");
        pass = Dotenv.load().get("POSTGRES_PASSWORD");
        urlCalcio = "jdbc:postgresql://localhost:5432/" + dbName;
        try {
            connection = DriverManager.getConnection(urlCalcio, user, pass);
            createDatabaseIfNotExists();
            createPlayersTable(connection);
            System.out.println("Connected to '" + dbName + "' and checked/created table 'players'.");
        } catch (SQLException e) {
            System.out.println("Connection to '" + dbName + "' failed: " + e.getMessage());
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

    public void insertPlayer(Player player) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO players (first_name, last_name, birth_date, nationality, jersey_number, abilities) VALUES (?, ?, ?, ?, ?, ?::jsonb)")) {

            stmt.setString(1, player.getFirstName());
            stmt.setString(2, player.getLastName());
            stmt.setDate(3, Date.valueOf(player.getBirthDate()));
            stmt.setString(4, player.getNationality());
            stmt.setInt(5, player.getJerseyNumber());
            stmt.setString(6, player.abilitiesToJson());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
