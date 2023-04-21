package PaooGame.Graphics;

import PaooGame.GameObjects.Player;
import PaooGame.GameWindow.GameWindow;

import java.awt.image.BufferedImage;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets
{
        /// Referinte catre elementele grafice (dale) utilizate in joc.
    public static Player playerLeft;
    public static Player playerRight;
    public static Background field1;
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
        playerLeft = new Player(players.crop(0,0),0,0);

        // Set up the game bounds
        int gameWidth = GameWindow.GetWndWidth();
        int gameHeight = GameWindow.GetWndHeight();
        int numRows = 5; // number of rows of fans
        int rowHeight = gameHeight / numRows;

        // Create the fans and add them to the array
        // Calculate the starting y-coordinate for the first row
        int startY = (gameHeight - numRows * rowHeight) / 2;

        // Iterate over each fan and place them on the desired rows
        for (int i = 0; i < fans.length; i++) {
            // Calculate the row index based on the fan index
            int rowIndex = i % numRows;

            // Calculate the x-coordinate for the fan
            int x = (int)(i* 50 * gameWidth);

            // Calculate the y-coordinate based on the row index and row height
            int y = startY + rowIndex * rowHeight;

            fans[i] = new Fan(fanImage, x, y);
        }
    }
}
