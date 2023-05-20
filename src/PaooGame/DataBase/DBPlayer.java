package PaooGame.DataBase;

import PaooGame.GameObjects.Player;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBPlayer extends DataBaseManager {
    public DBPlayer(String databasePath) {
        super(databasePath);
    }

    public void savePlayers(String tableName, String columnName, List<Player> players) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO " + tableName + " (" + columnName + ") VALUES (?)")) {

            for (Player player : players) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(player);
                byte[] dataBytes = baos.toByteArray();
                ByteArrayInputStream bais = new ByteArrayInputStream(dataBytes);

                statement.setBinaryStream(1, bais, dataBytes.length);
                statement.executeUpdate();
            }

            System.out.println("Players saved successfully.");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void savePlayerWithPosition(Player player) {
        try {
            // Save the position first
            Position position = player.getPosition();
            saveData("position", "x, y", position.getX() + ", " + position.getY());

            // Save the player
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO players (position_id) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, getLastInsertedRowId("position"));

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to save player.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int playerId = generatedKeys.getInt(1);
                    player.setPlayerId(playerId);
                } else {
                    throw new SQLException("Failed to retrieve generated player ID.");
                }
            }

            System.out.println("Player saved successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
