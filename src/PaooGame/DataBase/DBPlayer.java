package PaooGame.DataBase;

import PaooGame.GameObjects.Player;
import PaooGame.ImpulseEngine.Vec2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public static List<Player> loadPlayers(String tableName) {
        List<Player> players = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int xPosition = resultSet.getInt("x_position");
                int yPosition = resultSet.getInt("y_position");

                // Create a Vec2 object for the player's position
                Vec2 position = new Vec2(xPosition, yPosition);

                // Create a new Player object
                Player player = new Player(id,xPosition, yPosition,200,200);

                players.add(player);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }

}
