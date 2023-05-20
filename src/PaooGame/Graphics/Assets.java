package PaooGame.Graphics;

import PaooGame.DataBase.DataBaseManager;
import PaooGame.GameObjects.Ball;
import PaooGame.GameObjects.Basket;
import PaooGame.GameObjects.Player;
import PaooGame.GameWindow.GameWindow;
import PaooGame.ImpulseEngine.*;
import PaooGame.ImpulseEngine.Polygon;
import PaooGame.Match.Match;
import PaooGame.UserInterface.Menu;
import PaooGame.UserInterface.Option;

import java.util.List;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Assets
{
        /// Referinte catre elementele grafice (dale) utilizate in joc.
    public static Player playerLeft;
    public static Player playerRight;

    private static final int[] PLAYER1_KEYS = {KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D};
    private static final int[] PLAYER2_KEYS = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};
    public static Background menuBackground;
    public static Basket basketLeft;
    public static Basket basketRight;
    public static Background field1;
    public static ArrayList<RunningAd> runningAds = new ArrayList<>();;
    public static Fan []fans = new Fan[100];
    public  static List<Player> arrayPlayers;
    public static Clock clock;
    public static ImpulseScene impulseScene;
    public static Ball ball;
    public static Menu menu;
    public static Menu levels;
    public static DataBaseManager dataBaseManager;

    public static Vec2 playerStart = new Vec2(0,1080/2);
    public static  final int hitBoxBall = 75;

    public static final int hitBoxXPlayer = 170;
    public static final int hitBoxYPlayer = 200;

    public static final int hitBoxXBasket = 150;
    public static final int hitBoxYBasket = 30;
    public static Match match;
    public static void Init()
    {
        SpriteSheet players = new SpriteSheet(ImageLoader.LoadImage("/textures/Players.png"));
        BufferedImage fanImage = ImageLoader.LoadImage("/textures/Fan.png");

        field1 = new Background(ImageLoader.LoadImage("/textures/NewYorkKnicksField.jpg"));
        menuBackground = new Background(ImageLoader.LoadImage("/textures/MenuBackground.png"));

        ball = Ball.getInstance(ImageLoader.LoadImage("/textures/ball.png"),GameWindow.GetWndWidth()/2-1000,GameWindow.GetWndHeight()/2,hitBoxBall);

        for (int column = 0 ; column < 3 ; column++){
            for(int line = 0 ; line < 3; line++){
                arrayPlayers.add(new Player(players.crop(column,line), (int) playerStart.x, (int) playerStart.y,hitBoxXPlayer,hitBoxYPlayer));
            }
        }


        playerLeft = arrayPlayers.get(0);
        playerLeft.setKeys(PLAYER1_KEYS);
        playerRight = arrayPlayers.get(1);
        playerRight.setKeys(PLAYER2_KEYS);

        RunningAd wizardGame2Add = new RunningAd("Play WizardGame2 FREE", GameWindow.GetWndWidth() / 8, 500, 5);
        RunningAd yourAddHere = new RunningAd("Casa lui Nea Cimpoi FREE", GameWindow.GetWndWidth() / 2, 500, 5);

        basketLeft = new Basket(ImageLoader.LoadImage("/textures/basketSpriteLeft.png"),0,1080-450-234,hitBoxXBasket,hitBoxYBasket);            // todo make this constants
        basketRight = new Basket(ImageLoader.LoadImage("/textures/basketSpriteRight.png"),1920-163,1080-450-234,hitBoxXBasket,hitBoxYBasket);   // todo make this constants

        impulseScene = new ImpulseScene(ImpulseMath.DT,10);

        Body p1 = impulseScene.add(playerLeft.shape,0,1080/2);
        Body p2 = impulseScene.add(playerRight.shape,1920,1080/2);

        p1.mass = 10f;
        p2.mass = 5f;

        Body poly = impulseScene.add( new Polygon( (float)240,(float)GameWindow.GetWndHeight()/2+200,500.0f, 10.0f ), 240, GameWindow.GetWndHeight()/2+200 );
        poly.setStatic();
        poly.setOrient( 0 );

        Body bBall  = impulseScene.add(ball.shape,GameWindow.GetWndWidth()/2,GameWindow.GetWndHeight()/2);
        bBall.setOrient( ImpulseMath.random( -ImpulseMath.PI, ImpulseMath.PI ) );
        bBall.invInertia = 0.5f;
        bBall.restitution =10f;
        bBall.mass = 10f;

        dataBaseManager = new DataBaseManager("player.db");


        menu = new Menu(GameWindow.GetWndWidth()/3, (int) (GameWindow.GetWndHeight()/5.5),150,menuBackground);
        menu.addOption(new Option("Start Game", Color.MAGENTA,Font.getFont(Font.SANS_SERIF),500,100));
        menu.addOption(new Option("Load Game", Color.MAGENTA,Font.getFont(Font.SANS_SERIF),500,100));
        menu.addOption(new Option("Save Game", Color.MAGENTA,Font.getFont(Font.SANS_SERIF),500,100));
        menu.addOption(new Option("Levels", Color.MAGENTA,Font.getFont(Font.SANS_SERIF),500,100));
        menu.addOption(new Option("EXIT", Color.MAGENTA,Font.getFont(Font.SANS_SERIF),500,100));

        levels = new Menu(GameWindow.GetWndWidth()/3, (int) (GameWindow.GetWndHeight()/5.5),150,menuBackground);
        levels.addOption(new Option("Default Game", Color.MAGENTA,Font.getFont(Font.SANS_SERIF),500,200));
        levels.addOption(new Option("Level 1", Color.MAGENTA,Font.getFont(Font.SANS_SERIF),500,200));
        levels.addOption(new Option("Level 2", Color.MAGENTA,Font.getFont(Font.SANS_SERIF),500,200));
        levels.addOption(new Option("Level 3", Color.MAGENTA,Font.getFont(Font.SANS_SERIF),500,200));
        levels.addOption(new Option("CUSTOM", Color.MAGENTA,Font.getFont(Font.SANS_SERIF),500,200));


        runningAds.add(wizardGame2Add);
        runningAds.add(yourAddHere);

        clock = new Clock(GameWindow.GetWndWidth()/2,50,60);

        // Set up the game bounds
        int gameWidth = GameWindow.GetWndWidth();
        int gameHeight = GameWindow.GetWndHeight();
        int numFans = 100; // The total number of fans
        int numRows = 4; // The number of rows
        int fansPerRow = numFans / numRows; // The number of fans per row

        int rowHeight = gameHeight / (numRows * 5); // The height of each row
        int fanSpacing = gameWidth / fansPerRow; // The spacing between fans in each row

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < fansPerRow; j++) {
                int x = j * fanSpacing + fanSpacing / 2; // The x-coordinate of the fan
                int y = i * rowHeight + rowHeight / 2; // The y-coordinate of the fan

                fans[i * fansPerRow + j] = new Fan(fanImage, x, y + 200); // Add the fan to the array
            }
        }


        match = new Match();

        match.setBasketRight(basketRight);
        match.setBasketLeft(basketLeft);
        match.setPlayerLeft(playerLeft);
        match.setPlayerRight(playerRight);
        match.setBall(ball);
        match.setClock(clock);
        match.setBackground(field1);
        Match.setRunningAds(runningAds);
        Match.setFans(fans);

        //match.saveMatch();
    }
}
