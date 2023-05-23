package PaooGame.Match;

import PaooGame.DataBase.DBMatches;
import PaooGame.GameObjects.Ball;
import PaooGame.GameObjects.Basket;
import PaooGame.GameObjects.Player;
import PaooGame.Graphics.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static PaooGame.Graphics.Assets.dbMatches;

public class Match {
    public Basket basketRight;
    public Basket basketLeft;
    public Player playerLeft;
    public Player playerRight;
    public Ball ball;
    public Clock clock;
    public Background background;
    public  ArrayList<RunningAd> runningAds = new ArrayList<>();
    public static Fan[] fans = new Fan[100];
    public BufferedImage fanImage;

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

    public BufferedImage getFanImage() {
        return fanImage;
    }

    public void setFanImage(BufferedImage fanImage) {
        this.fanImage = fanImage;
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

    public  ArrayList<RunningAd> getRunningAds() {
        return runningAds;
    }

    public  void setRunningAds(ArrayList<RunningAd> runningAds) {
        this.runningAds = runningAds;
    }

    public static Fan[] getFans() {
        return fans;
    }

    public static void setFans(Fan[] fans) {
        Match.fans = fans;
    }


    public void setMatch(Basket basketRight, Basket basketLeft, Player player1, Player player2, Ball ball, Clock clock, Background background, ArrayList<RunningAd> runningAds, BufferedImage fanImage, DBMatches dbMatches) {
        this.setBasketRight(basketRight);
        this.setBasketLeft(basketLeft);
        this.setPlayerLeft(player1);
        this.setPlayerRight(player2);
        this.setBall(ball);
        this.setClock(clock);
        this.setBackground(background);
        this.fanImage = fanImage;

        this.setRunningAds(runningAds);


        Fan.setFansImage(fanImage,Match.fans);

       // dbMatches.saveMatch("match", this);
    }

    public void setTo(Match match){
        this.setBasketRight(match.basketRight);
        this.setBasketLeft(match.basketLeft);
        this.setPlayerLeft(match.playerLeft);
        this.setPlayerRight(match.playerRight);
        this.setBall(match.ball);
        this.setClock(match.clock);
        this.setBackground(match.background);

        this.setRunningAds(runningAds);

        Fan.setFansImage(match.fanImage,Match.fans);
    }
    public void saveMatch(){
        dbMatches.saveMatch("match", this);
    }
}
