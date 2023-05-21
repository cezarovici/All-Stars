package PaooGame.DataBase;

import PaooGame.GameObjects.Player;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DBPlayer extends DataBaseManager {
    public DBPlayer(String databasePath) {
        super(databasePath);
    }

    public void savePlayers(String tableName, String columnName, List<Player> players) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO " + tableName + " (" + columnName + ") VALUES (?)")) {

            for (Player player : players) {

                statement.setString(1, player.getName());
              //  statement.setInt(3, player.getPosition().getId());

                statement.executeUpdate();
            }

            System.out.println("Players saved successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
