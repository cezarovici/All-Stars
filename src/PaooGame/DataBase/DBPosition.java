package PaooGame.DataBase;

import PaooGame.GameObjects.Player;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBPosition extends DataBaseManager{
    public DBPosition(String databasePath) {
        super(databasePath);
    }

    public void savePosition(String tableName, String columnName,Player player) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO " + tableName + " (" + columnName + ") VALUES (?,?)")) {

            statement.setInt(1, player.getX());
            statement.setInt(2, player.getY());

            statement.executeUpdate();

            System.out.println("Players saved successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
