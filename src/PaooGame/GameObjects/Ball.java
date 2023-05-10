package PaooGame.GameObjects;

import PaooGame.GameWindow.GameWindow;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class Ball extends GameObject{
    double vx = 0; // horizontal velocity of the ball
    final double MAX_VX = 5; // maximum horizontal velocity of the ball
    private double vy; // vertical velocity
    private boolean isBouncing; // flag to track if the ball is currently bouncing
    private final double radius;
    private static Ball instance = null;


    public Ball(BufferedImage sprite, int x, int y,double radius) {
        super(sprite,x,y);
        this.radius = radius;

        this.vy = 0; // initialize velocity to 0
        this.isBouncing = false; // initialize bouncing flag to false
    }


    public static Ball getInstance(BufferedImage sprite, int x, int y, double radius) {
        if (instance == null) {
            instance = new Ball(sprite, x, y,radius);
        }

        return instance;
    }

    public void update() {
        if (isBouncing()) {
            // apply gravity to vertical velocity
            vy += 0.05; // adjust gravity strength as needed

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
        else{
            this.startBounce();
        }
    }

    public void Draw(Graphics g) {
        super.Draw(g);
        // Draw the hitbox
        int tempX = (int) (x + sprite.getWidth() / 2 - radius / 2);
        int tempY  = (int) (y + sprite.getHeight() /2 - radius / 2);

        g.setColor(Color.BLUE);
        g.drawOval(tempX, tempY, (int) (radius*2), (int) (radius*2));
    }

    protected boolean collides(GameObject object) {
        // Calculate the distance between the center of the ball and the player's hitbox
        if  (!(object instanceof Player player)) {
            return false;
        }

        double dx = getX() - player.hitBox.x;
        double dy = getY() - player.hitBox.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Check if the distance is less than the sum of the ball's radius and half of the player's width/height
        return distance < radius + (Math.max(player.hitBox.width, player.hitBox.height) / 2.0);
    }

    public void startBounce() {
        if (!isBouncing()){
            isBouncing = true;
            vy = -5; // set initial upward velocity for bouncing
        }
    }

    public boolean isBouncing() {
        return isBouncing;
    }
}
