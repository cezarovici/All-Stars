package PaooGame.DataBase;

import PaooGame.GameObjects.Player;
import PaooGame.ImpulseEngine.Vec2;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DBPlayer extends DataBaseManager {
    public DBPlayer(String databasePath) {
        super(databasePath);
    }
    @Override
    public void createTable(){
        super.createTable("player","name,x_position,y_position");
    }

    public void savePlayers(String tableName, String columnName, List<Player> players) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO " + tableName + " (" + columnName + ") VALUES (?,?,?)")) {

            for (Player player : players) {

                statement.setString(1, player.getName());

                Vec2 position = player.getPosition();

                statement.setInt(2, (int) position.getX());
                statement.setInt(3,(int)position.getY());
                statement.executeUpdate();
            }

            System.out.println("Players saved successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
