package PaooGame.Match;

import PaooGame.GameObjects.Ball;
import PaooGame.GameObjects.Basket;
import PaooGame.GameObjects.Player;
import PaooGame.Graphics.Background;
import PaooGame.Graphics.Clock;
import PaooGame.Graphics.Fan;
import PaooGame.Graphics.RunningAd;

import java.sql.*;
import java.util.ArrayList;

public class Match {
    private Basket basketRight;
    private Basket basketLeft;
    private Player playerLeft;
    private Player playerRight;
    private Ball ball;
    private Clock clock;
    private Background background;
    private static ArrayList<RunningAd> runningAds = new ArrayList<>();
    private static Fan[] fans = new Fan[100];

    private Connection connection;

    public Match() {
        // Initialize the connection to the SQLite database
        String url = "jdbc:sqlite:match.db"; // Replace with your database path
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        createTables();
    }

    public void createTables() {
        createPlayersTable();
        createBallsTable();
        createClocksTable();
        createTable();
    }

    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS matches (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "playerLeftID INTEGER," +
                "playerRightID INTEGER," +
                "ballID INTEGER," +
                "clockID INTEGER," +
                "background TEXT," +
                "FOREIGN KEY (playerLeftID) REFERENCES players (id)," +
                "FOREIGN KEY (playerRightID) REFERENCES players (id)," +
                "FOREIGN KEY (ballID) REFERENCES balls (id)," +
                "FOREIGN KEY (clockID) REFERENCES clocks (id)" +
                ")";

        try (Statement statement = connection.createStatement()) {
            // Execute the query to create the table
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPlayersTable() {
        String query = "CREATE TABLE IF NOT EXISTS players (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "positionX INTEGER," +
                "positionY INTEGER," +
                "type TEXT" +
                ")";

        // Execute the query to create the players table
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createBallsTable() {
        String query = "CREATE TABLE IF NOT EXISTS balls (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "positionX INTEGER," +
                "positionY INTEGER," +
                "type TEXT" +
                ")";

        // Execute the query to create the balls table
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createClocksTable() {
        String query = "CREATE TABLE IF NOT EXISTS clocks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "positionX INTEGER," +
                "positionY INTEGER," +
                "type TEXT" +
                ")";

        // Execute the query to create the clocks table
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveMatch() {
        String query = "INSERT INTO matches (basketRight, basketLeft, playerLeft, playerRight, ball, clock, background) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the values for the match properties in the query
            // Set the values for the match properties in the query
            statement.setString(1, getBasketRight().toString());
            statement.setString(2, getBasketLeft().toString());
            statement.setInt(3, getPlayerLeft().getId()); // Assuming playerLeft has an 'id' property
            statement.setInt(4, getPlayerRight().getId()); // Assuming playerRight has an 'id' property
            statement.setInt(5, getBall().getId()); // Assuming ball has an 'id' property
            statement.setInt(6, getClock().getId()); // Assuming clock has an 'id' property
            statement.setString(7, getBackground().toString());

            // Execute the query to insert the match data into the database
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Getters and Setters

    public Basket getBasketRight() {
        return basketRight;
    }

    public void setBasketRight(Basket basketRight) {
        this.basketRight = basketRight;
    }

    public Basket getBasketLeft() {
        return basketLeft;
    }

    public void setBasketLeft(Basket basketLeft) {
        this.basketLeft = basketLeft;
    }

    public Player getPlayerLeft() {
        return playerLeft;
    }

    public void setPlayerLeft(Player playerLeft) {
        this.playerLeft = playerLeft;
    }

    public Player getPlayerRight() {
        return playerRight;
    }

    public void setPlayerRight(Player playerRight) {
        this.playerRight = playerRight;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public static ArrayList<RunningAd> getRunningAds() {
        return runningAds;
    }

    public static void setRunningAds(ArrayList<RunningAd> runningAds) {
        Match.runningAds = runningAds;
    }

    public static Fan[] getFans() {
        return fans;
    }

    public static void setFans(Fan[] fans) {
        Match.fans = fans;
    }
}
