package PaooGame.Graphics;

import PaooGame.DataBase.DBMatches;
import PaooGame.DataBase.DBPlayer;
import PaooGame.DataBase.DataBaseManager;
import PaooGame.Game;
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
    public static Player playerLeft,playerLEFTL1,playerLEFTL2,playerLEFTL3;
    public static Player playerRight,playerRightL1,playerRightL2,playerRightL3;

    private static final int[] PLAYER1_KEYS = {KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D};
    private static final int[] PLAYER2_KEYS = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};
    public static Background menuBackground;
    public static Basket basketLeft;
    public static Basket basketRight;
    public static Background field1,field2,field3;
    public static ArrayList<RunningAd> runningAdsL1,runningAdsL2,runningAdsL3 = new ArrayList<>();;
    public static Fan []fans = new Fan[100];
    public  static List<Player> arrayPlayers;
    public static Clock clock;
    public static ImpulseScene impulseScene;
    public static Ball ball;
    public static Menu menu;
    public static Menu levels;
    public static DBPlayer dbPlayers;
    public static DBMatches dbMatches;
    public static Vec2 player2Start = new Vec2(1800,1080/2);
    public static Vec2 player1Start = new Vec2(0,1080/2);
    public static  final int hitBoxBall = 75;

    public static final int hitBoxXPlayer = 170;
    public static final int hitBoxYPlayer = 170;

    public static final int hitBoxXBasket = 150;
    public static final int hitBoxYBasket = 30;
    public static Match match,level1,level2,level3;
    public static String dataBasePath = "data_base.db";
    public static BufferedImage nykFanImage = ImageLoader.LoadImage("/textures/Fan.png");
    public static BufferedImage lakersFanImage = ImageLoader.LoadImage("/textures/LakersFan.png");
    public static BufferedImage miamiFanImage = ImageLoader.LoadImage("/textures/MiamiFan.png");
    public static void Init()
    {

        field1 = new Background(ImageLoader.LoadImage("/textures/NewYorkKnicksField.jpg"));
        field2 = new Background(ImageLoader.LoadImage("/textures/LosAngelesLakersField.jpg"));
        field3 = new Background(ImageLoader.LoadImage("/textures/MiamiHeaFieldt.jpg"));

        menuBackground = new Background(ImageLoader.LoadImage("/textures/MenuBackground.png"));

        ball = Ball.getInstance(ImageLoader.LoadImage("/textures/ball.png"),GameWindow.GetWndWidth()/2-1000,GameWindow.GetWndHeight()/2,hitBoxBall);

        arrayPlayers = new ArrayList<Player>();

        for (int column = 0 ; column < 4 ; column++){
            for(int line = 0 ; line < 4; line++){
                arrayPlayers.add(new Player(column+line, (int) player1Start.x, (int) player1Start.y,hitBoxXPlayer,hitBoxYPlayer));
            }
        }

        arrayPlayers.get(0).setName("Cezar");
        arrayPlayers.get(1).setName("Simi");
        arrayPlayers.get(2).setName("Dumitru");
        arrayPlayers.get(3).setName("LeBron");
        arrayPlayers.get(4).setName("Curry");
        arrayPlayers.get(5).setName("Vieru");
        arrayPlayers.get(6).setName("Florin");
        arrayPlayers.get(7).setName("Sebi");
        arrayPlayers.get(8).setName("Fanaragiu");
        arrayPlayers.get(9).setName("Cristi");
        arrayPlayers.get(10).setName("Radu");
        arrayPlayers.get(11).setName("Misu");
        arrayPlayers.get(12).setName("Silviu");
        arrayPlayers.get(13).setName("Erich");
        arrayPlayers.get(14).setName("Spiri");
        arrayPlayers.get(15).setName("Panciuc");

        playerLeft = arrayPlayers.get(0);
        playerRight = arrayPlayers.get(1);

        dbPlayers = new DBPlayer(dataBasePath);

        // Todo Uncomment this if you do not have players in DB
        //        dbPlayers.createTable();
        //        dbPlayers.savePlayers("player","name,x_position,y_position",arrayPlayers);

        dbMatches = new DBMatches(dataBasePath);
        dbMatches.createTable();

        match = new Match();
        clock = new Clock(GameWindow.GetWndWidth()/2,50,60);

        playerLeft.setKeys(PLAYER1_KEYS);
        playerRight.setKeys(PLAYER2_KEYS);

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

        //dataBaseManager = new DataBaseManager("player.db");

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

                fans[i * fansPerRow + j] = new Fan(nykFanImage, x, y + 200); // Add the fan to the array
            }
        }

       // RunningAd wizardGame2Add = new RunningAd("Play WizardGame2 FREE", GameWindow.GetWndWidth() / 8, 500, 5);
        //RunningAd yourAddHere = new RunningAd("Casa lui Nea Cimpoi FREE", GameWindow.GetWndWidth() / 2, 500, 5);

        RunningAd nykAD = new RunningAd("New York Knicks", GameWindow.GetWndWidth() / 2, 500, 5);
        RunningAd miamiAd = new RunningAd("Miami !!!", GameWindow.GetWndWidth() / 2, 500, 5);
        RunningAd lakersAdd = new RunningAd("Los Angeles Lakers", GameWindow.GetWndWidth() / 2, 500, 5);


        runningAdsL1 = new ArrayList<>();
        runningAdsL2 = new ArrayList<>();
        runningAdsL3 = new ArrayList<>();

        runningAdsL1.add(nykAD);
        runningAdsL2.add(miamiAd);
        runningAdsL3.add(lakersAdd);

        match.setMatch(basketRight, basketLeft, playerLeft, playerRight, ball, clock, field1, runningAdsL1, nykFanImage, dbMatches);


        playerLEFTL1 = new Player(2,(int)player1Start.x,(int)player1Start.y,hitBoxXPlayer,hitBoxYPlayer);
        playerRightL1 = new Player(3,(int)player2Start.x,(int)player2Start.y,hitBoxXPlayer,hitBoxYPlayer);

        playerLEFTL2 = new Player(4,(int)player1Start.x,(int)player1Start.y,hitBoxXPlayer,hitBoxYPlayer);
        playerRightL2 = new Player(5,(int)player2Start.x,(int)player2Start.y,hitBoxXPlayer,hitBoxYPlayer);

        playerLEFTL3 = new Player(6,(int)player1Start.x,(int)player1Start.y,hitBoxXPlayer,hitBoxYPlayer);
        playerRightL3 = new Player(7,(int)player2Start.x,(int)player2Start.y,hitBoxXPlayer,hitBoxYPlayer);

        playerLEFTL1.setKeys(PLAYER1_KEYS);
        playerLEFTL2.setKeys(PLAYER1_KEYS);
        playerLEFTL3.setKeys(PLAYER1_KEYS);

        playerRightL1.setKeys(PLAYER2_KEYS);
        playerRightL2.setKeys(PLAYER2_KEYS);
        playerRightL3.setKeys(PLAYER2_KEYS);


        level1 = new Match();
        level2 = new Match();
        level3 = new Match();

       level1.setMatch(basketRight,basketLeft,playerLEFTL1,playerRightL1,ball,clock,field1,runningAdsL1,nykFanImage,dbMatches);


       level2.setMatch(basketRight,basketLeft,playerLEFTL2,playerRightL2,ball,clock,field2,runningAdsL2,lakersFanImage,dbMatches);

       level3.setMatch(basketRight,basketLeft,playerLEFTL3,playerRightL3,ball,clock,field3,runningAdsL3,miamiFanImage,dbMatches);

    }
}
