package PaooGame.Graphics;

import PaooGame.GameObjects.Ball;
import PaooGame.GameObjects.Basket;
import PaooGame.GameObjects.Player;
import PaooGame.GameWindow.GameWindow;
import PaooGame.ImpulseEngine.Body;
import PaooGame.ImpulseEngine.ImpulseMath;
import PaooGame.ImpulseEngine.ImpulseScene;
import PaooGame.ImpulseEngine.Polygon;

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
    public static Basket basketLeft;
    public static Basket basketRight;
    public static Background field1;
    public static ArrayList<RunningAd> runningAds = new ArrayList<>();;
    public static Fan []fans = new Fan[100];
    public static Clock clock;
    public static ImpulseScene impulseScene;
    public static Ball ball;

    public static  final int hitBoxBall = 75;

    public static final int hitBoxXPlayer = 170;
    public static final int hitBoxYPlayer = 200;

    public static final int hitBoxXBasket = 150;
    public static final int hitBoxYBasket = 30;

    public static void Init()
    {
        /// Se creazcca temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet players = new SpriteSheet(ImageLoader.LoadImage("/textures/Players.png"));
        BufferedImage fanImage = ImageLoader.LoadImage("/textures/Fan.png");

        field1 = new Background(ImageLoader.LoadImage("/textures/NewYorkKnicksField.jpg"));

        ball = Ball.getInstance(ImageLoader.LoadImage("/textures/ball.png"),GameWindow.GetWndWidth()/2,GameWindow.GetWndHeight()/2,hitBoxBall);

        playerLeft = new Player(players.crop(0,0),0,1080/2,hitBoxXPlayer,hitBoxYPlayer);
        playerLeft.setKeys(PLAYER1_KEYS);
        playerRight = new Player(players.crop(1,0),1920,1080/2,hitBoxXPlayer,hitBoxYPlayer);
        playerRight.setKeys(PLAYER2_KEYS);

        RunningAd wizardGame2Add = new RunningAd("Play WizardGame2 FREE", GameWindow.GetWndWidth() / 8, 500, 5);
        RunningAd yourAddHere = new RunningAd("Casa lui Nea Cimpoi FREE", GameWindow.GetWndWidth() / 2, 500, 5);

        basketLeft = new Basket(ImageLoader.LoadImage("/textures/basketSpriteLeft.png"),0,1080-450-234,hitBoxXBasket,hitBoxYBasket);            // todo make this constants
        basketRight = new Basket(ImageLoader.LoadImage("/textures/basketSpriteRight.png"),1920-163,1080-450-234,hitBoxXBasket,hitBoxYBasket);   // todo make this constants

        impulseScene = new ImpulseScene(ImpulseMath.DT,10);
        impulseScene.add(playerLeft.shape,0,1080/2);
        impulseScene.add(playerRight.shape,1920,1080/2);

        Body b = null;
        b = impulseScene.add( new Polygon( (float)240,(float)GameWindow.GetWndHeight()/2+200,500.0f, 10.0f ), 240, GameWindow.GetWndHeight()/2+200 );
        b.setStatic();
        b.setOrient( 0 );

        b = impulseScene.add(ball.shape,GameWindow.GetWndWidth()/2,GameWindow.GetWndHeight()/2);
        b.setOrient( ImpulseMath.random( -ImpulseMath.PI, ImpulseMath.PI ) );
        b.invInertia = 5;
        b.restitution =5f;

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
    }
}
