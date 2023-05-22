package PaooGame.Match;

import PaooGame.DataBase.DBMatches;
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


    public Match  setMatch(Basket basketRight, Basket basketLeft, Player player1, Player player2, Ball ball, Clock clock, Background background, ArrayList<RunningAd> runningAds, Fan[] fans, DBMatches dbMatches) {
        this.setBasketRight(basketRight);
        this.setBasketLeft(basketLeft);
        this.setPlayerLeft(player1);
        this.setPlayerRight(player2);
        this.setBall(ball);
        this.setClock(clock);
        this.setBackground(background);
        Match.setRunningAds(runningAds);
        Match.setFans(fans);

        dbMatches.saveMatch("match", this);

        return this;
    }
}
