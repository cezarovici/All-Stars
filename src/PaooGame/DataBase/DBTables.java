package PaooGame.DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTables{
    private static void createMatchesTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS matches (" +
                "PlayerLeft_id INTEGER, " +
                "PlayerRight_id INTEGER, " +
                "Ball_id INTEGER, " +
                "Clock_id INTEGER, " +
                "FOREIGN KEY (PlayerLeft_id) REFERENCES players (player_id), " +
                "FOREIGN KEY (PlayerRight_id) REFERENCES players (player_id), " +
                "FOREIGN KEY (Ball_id) REFERENCES balls (ball_id), " +
                "FOREIGN KEY (Clock_id) REFERENCES clock (clock_id) " +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void createPlayersTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS players (" +
                "player_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "position_id INTEGER, " +
                "FOREIGN KEY (position_id) REFERENCES position (position_id)" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void createBallsTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS balls (" +
                "ball_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "position_id INTEGER, " +
                "FOREIGN KEY (position_id) REFERENCES position (position_id)" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void createPositionTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS position (" +
                "position_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "x INTEGER, " +
                "y INTEGER" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void createClockTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS clock (" +
                "clock_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "time TIMESTAMP" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}
