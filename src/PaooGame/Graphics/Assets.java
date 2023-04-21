package PaooGame.Graphics;

import PaooGame.GameObjects.Basket;
import PaooGame.GameObjects.Player;
import PaooGame.GameWindow.GameWindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
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
    public static ArrayList<RunningAd> runningAds = new ArrayList<RunningAd>();;
    public static Fan []fans = new Fan[100];
    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init()
    {
        /// Se creazcca temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet players = new SpriteSheet(ImageLoader.LoadImage("/textures/Players.png"));
        BufferedImage fanImage = ImageLoader.LoadImage("/textures/Fan.png");

        field1 = new Background(ImageLoader.LoadImage("/textures/NewYorkKnicksField.jpg"));



        playerLeft = new Player(players.crop(0,0),0,1080/2);
        playerLeft.setKeys(PLAYER1_KEYS);
        playerRight = new Player(players.crop(1,0),1920,1080/2);
        playerRight.setKeys(PLAYER2_KEYS);

        RunningAd wizardGame2Add = new RunningAd("Play WizardGame2 FREE", GameWindow.GetWndWidth() / 8, 500, 5);
        RunningAd yourAddHere = new RunningAd("Your add here", GameWindow.GetWndWidth() / 2, 500, 5);

        basketLeft = new Basket(ImageLoader.LoadImage("/textures/basketSpriteLeft.png"),0,1080-450-234);            // todo make this constants
        basketRight = new Basket(ImageLoader.LoadImage("/textures/basketSpriteRight.png"),1920-163,1080-450-234);   // todo make this constants

        runningAds.add(wizardGame2Add);
        runningAds.add(yourAddHere);

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
