package it.unimol.player_manager.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreConnection {

    private String url = "jdbc:postgresql://localhost:5432/calcio";
    private String user = "lelio";
    private String pass = "Oilel2025+";

    public PostgreConnection() {

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            System.out.println("Connected to PostgreSQL!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String query(String sql) {
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                Statement stmt = conn.createStatement()) {
            return stmt.executeQuery(sql).toString();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return "Query failed: " + e.getMessage();
        }
    }

    public void createDatabase(String dbName) {
        String sql = "CREATE DATABASE " + dbName;
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully!");
        } catch (SQLException e) {
            System.out.println("Database creation failed: " + e.getMessage());
        }
    }
}
