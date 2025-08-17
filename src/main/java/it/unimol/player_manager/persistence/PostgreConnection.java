package it.unimol.player_manager.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import io.github.cdimascio.dotenv.Dotenv;
import it.unimol.player_manager.entity.Player;

/**
 * Manages the connection and operations on the PostgreSQL database for players.
 */
public final class PostgreConnection {
    
    /** Database name. */
    private String dbName;
    /** Username for the database. */
    private String user;
    /** Password for the database. */
    private String pass;
    /** Database connection URL. */
    private String urlCalcio;
    /** Singleton instance. */
    private static PostgreConnection instance;
    /** Database connection. */
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

    private boolean createPlayersTable(final Connection conn) {
        final String sql = "CREATE TABLE IF NOT EXISTS players ("
                + "id SERIAL PRIMARY KEY, "
                + "first_name VARCHAR(50) NOT NULL, "
                + "last_name VARCHAR(50) NOT NULL, "
                + "birth_date DATE NOT NULL, "
                + "nationality VARCHAR(50) NOT NULL, "
                + "jersey_number INTEGER NOT NULL, "
                + "abilities JSONB NOT NULL"
                + ")";
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
        final Statement stmt = connection.createStatement();
        final String checkDb = "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'";
        final var rs = stmt.executeQuery(checkDb);
        if (!rs.next()) {
            final String sql = "CREATE DATABASE " + dbName;
            stmt.executeUpdate(sql);
            System.out.println("Database '" + dbName + "' created successfully!");
        } else {
            System.out.println("Database '" + dbName + "' already exists.");
        }
    }

    public void insertPlayer(final Player player) {
        final int FIRST_NAME_INDEX = 1;
        final int LAST_NAME_INDEX = 2;
        final int BIRTH_DATE_INDEX = 3;
        final int NATIONALITY_INDEX = 4;
        final int JERSEY_NUMBER_INDEX = 5;
        final int ABILITIES_INDEX = 6;
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO players (first_name, last_name, birth_date, nationality, jersey_number, abilities) VALUES (?, ?, ?, ?, ?, ?::jsonb)")) {

            stmt.setString(FIRST_NAME_INDEX, player.getFirstName());
            stmt.setString(LAST_NAME_INDEX, player.getLastName());
            stmt.setDate(BIRTH_DATE_INDEX, Date.valueOf(player.getBirthDate()));
            stmt.setString(NATIONALITY_INDEX, player.getNationality());
            stmt.setInt(JERSEY_NUMBER_INDEX, player.getJerseyNumber());
            stmt.setString(ABILITIES_INDEX, player.abilitiesToJson());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlayer(final int jerseyNumber) {
        final String sql = "DELETE FROM players WHERE jersey_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, jerseyNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
