package PaooGame.GameObjects;

import PaooGame.GameWindow.GameWindow;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Ball extends GameObject{
    private double vy; // vertical velocity
    private boolean isBouncing; // flag to track if the ball is currently bouncing

    public Ball(BufferedImage sprite, int x, int y) {
        super(sprite, x, y);
        this.vy = 0; // initialize velocity to 0
        this.isBouncing = false; // initialize bouncing flag to false
    }

    public void update() {
        if (isBouncing) {
            // apply gravity to vertical velocity
            vy += 0.1; // adjust gravity strength as needed

            // update ball's position based on velocity
            setY((int)(getY() + vy));

            // check for collision with the floor
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
                    obj.move((int) (dx / dist * 10), (int) (dy / dist * 10));
                }
            }
        }
    }

    public void Draw(Graphics g) {
        super.Draw(g);
    }

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
