package PaooGame.DataBase;

import java.sql.*;

public class DataBaseManager {
        private Connection conn;

        public DataBaseManager(String databasePath) {
            try {
                // Register the SQLite JDBC driver
                Class.forName("org.sqlite.JDBC");

                // Create a connection to the database
                conn = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }

        // Create a table in the database
        public void createTable() {
            String sql = "CREATE TABLE IF NOT EXISTS players (id INTEGER PRIMARY KEY, name TEXT, score INTEGER)";
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.execute();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Insert a new player into the database
        public void insertPlayer(String name, int score) {
            String sql = "INSERT INTO players (name, score) VALUES (?, ?)";
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setInt(2, score);
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Update a player's score in the database
        public void updatePlayerScore(String name, int newScore) {
            String sql = "UPDATE players SET score = ? WHERE name = ?";
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, newScore);
                stmt.setString(2, name);
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Delete a player from the database
        public void deletePlayer(String name) {
            String sql = "DELETE FROM players WHERE name = ?";
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Close the database connection
        public void closeConnection() {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}
