package PaooGame.GameObjects;

import PaooGame.GameWindow.GameWindow;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Ball extends GameObject{
    double vx = 0; // horizontal velocity of the ball
    final double MAX_VX = 5; // maximum horizontal velocity of the ball
    private double vy; // vertical velocity
    private boolean isBouncing; // flag to track if the ball is currently bouncing
    private Ellipse2D circleHitBox;
    private static Ball instance = null;


    public Ball(BufferedImage sprite, int x, int y,int hitBoxX,int hitBoxY) {
        super(sprite, x, y,hitBoxX,hitBoxY);
        this.vy = 0; // initialize velocity to 0
        this.isBouncing = false; // initialize bouncing flag to false
    }


    public static Ball getInstance(BufferedImage sprite, int x, int y, int hitBoxX, int hitBoxY) {
        if (instance == null) {
            instance = new Ball(sprite, x, y, hitBoxX, hitBoxY);
        }
        return instance;
    }


    public void update() {
        super.update();

        if (isBouncing) {
            // apply gravity to vertical velocity
            vy += 0.1; // adjust gravity strength as needed

            // update ball's position based on velocity
            setY((int)(getY() + vy));

            if (getY() + sprite.getHeight() > GameWindow.GetWndHeight()) {
                setY(GameWindow.GetWndHeight() - sprite.getHeight()); // reset y-coordinate to the floor
                vy = -vy * 0.8; // invert velocity and reduce it to simulate bouncing
            }

            // check for collision with players
            for (GameObject obj : getGameObjects()) {
                if (obj instanceof Player && this.collides(obj)) {
                    // calculate the direction and magnitude of the collision
                    double dx = getX() + sprite.getWidth() / 2 - (obj.getX() + obj.sprite.getWidth() / 2);
                    double dy = getY() + sprite.getHeight() / 2 - (obj.getY() + obj.sprite.getHeight() / 2);
                    double dist = Math.sqrt(dx * dx + dy * dy);

                    // adjust the ball's velocity based on the collision
                    vy = -vy * 0.8 + dy / dist * 2;
                    vx = dx / dist * 5; // adjust horizontal velocity based on direction of collision
                    obj.move((int) (dx / dist * 10), (int) (dy / dist * 10));

                    // update ball position
                    setX(getX() + (int) vx);
                    setY(getY() + (int) vy);

                    // cap horizontal velocity at maximum value
                    vx = Math.min(Math.max(vx, -MAX_VX), MAX_VX);
                }
            }
        }
    }

    public void Draw(Graphics g) {
        super.Draw(g);
    }

  //  @Override
//    public boolean collides(GameObject obj){
//        Ellipse2D ballHitBox = hitBox;
//
//
//
//
//        return false;
//    }

    public void startBounce() {
        if (!isBouncing) {
            isBouncing = true;
            vy = -5; // set initial upward velocity for bouncing
        }
    }

    public boolean isBouncing() {
        return isBouncing;
    }
}
