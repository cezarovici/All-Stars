package PaooGame;

import PaooGame.GameObjects.Ball;
import PaooGame.GameObjects.Basket;
import PaooGame.GameObjects.Player;
import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.*;
import PaooGame.ImpulseEngine.*;
import PaooGame.ImpulseEngine.Polygon;

import PaooGame.UserInterface.Keyboard;
import PaooGame.UserInterface.Menu;

import static java.awt.event.KeyEvent.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferStrategy;
import java.util.*;

public class Game implements Runnable
{
    private ArrayList<RunningAd> runningAds;

    private Background background;
    private GameWindow      wnd;        /*!< Fereastra in care se va desena tabla jocului*/
    private boolean         runState;   /*!< Flag ce starea firului de executie.*/
    private Thread          gameThread; /*!< Referinta catre thread-ul de update si draw al ferestrei*/
    private BufferStrategy  bs;         /*!< Referinta catre un mecanism cu care se organizeaza memoria complexa pentru un canvas.*/
    /// Sunt cateva tipuri de "c
    //complex buffer strategies", scopul fiind acela de a elimina fenomenul de
    /// flickering (palpaire) a ferestrei.
    /// Modul in care va fi implementata aceasta strategie in cadrul proiectului curent va fi triplu buffer-at

    ///                         |------------------------------------------------>|
    ///                         |                                                 |
    ///                 ****************          *****************        ***************
    ///                 *              *   Show   *               *        *             *
    /// [ Ecran ] <---- * Front Buffer *  <------ * Middle Buffer * <----- * Back Buffer * <---- Draw()
    ///                 *              *          *               *        *             *
    ///                 ****************          *****************        ***************

    private Graphics        g;          /*!< Referinta catre un context grafic.*/



    public enum GameState {
        MENU_Start,
        MENU_Levels,

        PLAYING
    }

    private GameState currentState = GameState.MENU_Start;

    Player player1,player2;
    Fan[] fans;
    Basket basketLeft,basketRight;
    Clock clock;
    Ball ball;
    Menu menu;
    Menu levels;
    ImpulseScene impulseScene;
    public Game(String title, int width, int height)
    {
            /// Obiectul GameWindow este creat insa fereastra nu este construita
            /// Acest lucru va fi realizat in metoda init() prin apelul
            /// functiei BuildGameWindow();
        wnd = new GameWindow(title, width, height);
            /// Resetarea flagului runState ce indica starea firului de executie (started/stoped)
        runState = false;
    }

    /*! \fn private void init()
        \brief  Metoda construieste fereastra jocului, initializeaza aseturile, listenerul de tastatura etc.

        Fereastra jocului va fi construita prin apelul functiei BuildGameWindow();
        Sunt construite elementele grafice (assets): dale, player, elemente active si pasive.

     */
    private void InitGame()
    {
        wnd = new GameWindow("Schelet Proiect PAOO", 1920, 1080);
            /// Este construita fereastra grafica.
        wnd.BuildGameWindow();
            /// Se incarca toate elementele grafice (dale)
        Assets.Init();

        impulseScene = Assets.impulseScene;
        background = Assets.field1;
        player1 = Assets.playerLeft;
        player2 = Assets.playerRight;


        fans = Assets.fans;

        runningAds = Assets.runningAds;

        basketLeft = Assets.basketLeft;
        basketRight = Assets.basketRight;

        clock = Assets.clock;

        ball = Assets.ball;

        menu = Assets.menu;
        levels = Assets.levels;
    }

    /*! \fn public void run()
        \brief Functia ce va rula in thread-ul creat.

        Aceasta functie va actualiza starea jocului si va redesena tabla de joc (va actualiza fereastra grafica)
     */
    public void run()
    {
        // Initialize game object
        InitGame();

        long oldTime = System.currentTimeMillis();
        long currentTime;

        final int framesPerSecond = 60;
        final long timeFrame = 1000 / framesPerSecond;

        int frames = 0;
        long frameStartTime = System.currentTimeMillis();

        while (runState)
        {
            // Get current time
            currentTime = System.currentTimeMillis();

            // Update game if enough time has passed
            if (currentTime - oldTime >= timeFrame)
            {
                Update();
                Draw();
                oldTime = currentTime;
                frames++;
            }

            // Print FPS and tick rate every second
            if (currentTime - frameStartTime >= 1000)
            {
                double tickTime = (double)(currentTime - frameStartTime) / frames;
                double fps = frames / ((double)(currentTime - frameStartTime) / 1000);
                System.out.printf("Tick: %.2f ms, FPS: %.2f\n", tickTime, fps);
                frames = 0;
                frameStartTime = currentTime;
            }
        }
    }

    /*! \fn public synchronized void start()
        \brief Creaza si starteaza firul separat de executie (thread).

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StartGame()
    {
        if(!runState)
        {
                /// Se actualizeaza flagul de stare a threadului
            runState = true;
                /// Se construieste threadul avand ca parametru obiectul Game. De retinut faptul ca Game class
                /// implementeaza interfata Runnable. Threadul creat va executa functia run() suprascrisa in clasa Game.
            gameThread = new Thread(this);
                /// Threadul creat este lansat in executie (va executa metoda run())
            gameThread.start();
        }
        else
        {
                /// Thread-ul este creat si pornit deja
            return;
        }
    }

    /*! \fn public synchronized void stop()
        \brief Opreste executie thread-ului.

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StopGame()
    {
        if(runState)
        {
                /// Actualizare stare thread
            runState = false;
                /// Metoda join() arunca exceptii motiv pentru care trebuie incadrata intr-un block try - catch.
            try
            {
                // Clean up and shutdown operations
                wnd.CloseWindow(); // Close the game window
                gameThread.join();
            }
            catch(InterruptedException ex)
            {
                    /// In situatia in care apare o exceptie pe ecran vor fi afisate informatii utile pentru depanare.
                ex.printStackTrace();
            }
        }
        else
        {
                /// Thread-ul este oprit deja.
            return;
        }
    }

    /*! \fn private void Update()
        \brief Actualizeaza starea elementelor din joc.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Update()
    {
        if (currentState == GameState.MENU_Start){
            if (menu.StartGame()){
                currentState = GameState.PLAYING;
                menu.start = false;
            }

            if(menu.Levels()){
                currentState = GameState.MENU_Levels;
                levels.start = false;
            }

            if (menu.StopGame()){
                StopGame();
            }
        }
        if (currentState == GameState.MENU_Levels){
            if (Keyboard.isKeyPressed(VK_ESCAPE)){
                currentState = GameState.MENU_Start;
                menu.start = true;
                menu.levels = false;
                levels.start = false;
            }
        }
        else {
            player1.update();
            player2.update();
            ball.update();
            impulseScene.update();

            for (RunningAd ad : runningAds) {
                ad.update();
            }

            if (Keyboard.isKeyPressed(VK_ESCAPE)){
                currentState = GameState.MENU_Start;
            }
        }
    }

    /*! \fn private void Draw()
        \brief Deseneaza elementele grafice in fereastra coresponzator starilor actualizate ale elementelor.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Draw()
    {
            /// Returnez bufferStrategy pentru canvasul existent
        bs = wnd.GetCanvas().getBufferStrategy();
            /// Verific daca buffer strategy a fost construit sau nu
        if(bs == null)
        {
                /// Se executa doar la primul apel al metodei Draw()
            try
            {
                    /// Se construieste tripul buffer
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e)
            {
                    /// Afisez informatii despre problema aparuta pentru depanare.
                e.printStackTrace();
            }
        }
            /// Se obtine contextul grafic curent in care se poate desena.
        g = bs.getDrawGraphics();
            /// Se sterge ce era
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());

      if (currentState==GameState.MENU_Start) {
          menu.draw(g);

          g.setColor(Color.BLACK);
          g.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,30));
          g.drawString("Navigate with ARROWS",GameWindow.GetWndWidth()/2-100,100);
      }else if(currentState==GameState.MENU_Levels){
          levels.draw(g);

          g.setColor(Color.BLACK);
          g.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,30));
          g.drawString("ESC - move back to Main menu",GameWindow.GetWndWidth()/2-100,100);
      }else {
          for (RunningAd ad : runningAds) {
              ad.draw(g);
          }

          for (Fan fan : fans) {
              fan.Draw(g);
          }

          background.Draw(g);

          player1.Draw(g);
          player2.Draw(g);

          basketRight.Draw(g);
          basketLeft.Draw(g);
          clock.draw(g);

          ball.Draw(g);

          impulseScene.Draw((Graphics2D) g);
      }

        bs.show();

        g.dispose();
    }

}

