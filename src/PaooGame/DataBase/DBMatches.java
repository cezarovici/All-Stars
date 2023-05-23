package PaooGame.DataBase;

import PaooGame.GameObjects.Ball;
import PaooGame.GameObjects.Player;
import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Clock;
import PaooGame.Graphics.ImageLoader;
import PaooGame.ImpulseEngine.Vec2;
import PaooGame.Match.Match;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBMatches extends DataBaseManager{
    public DBMatches(String databasePath) {
        super(databasePath);
    }

    public static  final int hitBoxBall = 75;

    public static final int hitBoxXPlayer = 170;
    public static final int hitBoxYPlayer = 200;

    @Override
    public void  createTable(){
        super.createTable("match","\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\tplayerLeft_id INTEGER,\n" +
                "\tplayerLeft_x INTEGER,\n" +
                "\tplayerLeft_y INTEGER,\n" +
                "\tplayerRight_id INTEGER,\n" +
                "\tplayerRight_x INTEGER,\n" +
                "\tplayerRight_y INTEGER,\n" +
                "\tball_x INTEGER,\n" +
                "\tball_y INTEGER,\n" +
                "\ttime Integer");
   }

    public void saveMatch(String tableName, Match match) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO " + tableName +
                        " ("
                        + "playerLeft_id," +
                        "playerLeft_x," +
                        "playerLeft_y,"+
                        "playerRight_id,"+
                        "playerRight_x,"+
                        "playerRight_y,"+
                        "ball_x,"+
                        "ball_y,"+
                        "time"+
                        ") VALUES (?,?,?,?,?,?,?,?,?)")) {

            statement.setInt(1,match.getPlayerRight().getId());
            statement.setInt(2,match.getPlayerLeft().getX());
            statement.setInt(3,match.getPlayerLeft().getY());

            statement.setInt(4,match.getPlayerLeft().getId());
            statement.setInt(5,match.getPlayerLeft().getX());
            statement.setInt(6,match.getPlayerLeft().getY());

            statement.setInt(7,match.getBall().getX());
            statement.setInt(8,match.getBall().getY());

            statement.setInt(9,match.getClock().getTimeLeft());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadLastMatch(Match match, String tableName) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName + " ORDER BY id DESC LIMIT 1");
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                int playerLeftId = resultSet.getInt("playerLeft_id");
                int playerLeftX = resultSet.getInt("playerLeft_x");
                int playerLeftY = resultSet.getInt("playerLeft_y");

                int playerRightId = resultSet.getInt("playerRight_id");
                int playerRightX = resultSet.getInt("playerRight_x");
                int playerRightY = resultSet.getInt("playerRight_y");

                int ballX = resultSet.getInt("ball_x");
                int ballY = resultSet.getInt("ball_y");

                int time = resultSet.getInt("time");


                match.playerLeft.setPosition(playerLeftX,playerLeftY);
                match.playerRight.setPosition(playerRightX,playerRightY);

                match.ball.setPosition(ballX,ballY);

                match.clock.setTimeLeft(time);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

