package PaooGame.GameObjects;

import PaooGame.GameWindow.GameWindow;
import PaooGame.ImpulseEngine.Circle;
import PaooGame.ImpulseEngine.Shape;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Ball extends GameObject {
    private double vx = 0; // horizontal velocity of the ball
    final double MAX_VX = 5; // maximum horizontal velocity of the ball
    private double vy; // vertical velocity
    private boolean isBouncing; // flag to track if the ball is currently bouncing
    private final double radius;
    private static Ball instance = null;
    private final Ellipse2D.Double hitboxCircle;

    public Ball(BufferedImage sprite, int x, int y,float radius) {
        super(sprite,x,y,Shape.Type.Circle);
        shape.radius = radius;
        this.radius = radius;

        this.vy = 0; // initialize velocity to 0
        this.isBouncing = false; // initialize bouncing flag to false
        hitboxCircle = new Ellipse2D.Double(x+sprite.getWidth()/2,y+sprite.getHeight()/2,radius*2,radius*2);
    }


    public static Ball getInstance(BufferedImage sprite, int x, int y, float radius) {
        if (instance == null) {
            instance = new Ball(sprite, x, y,radius);
        }

        return instance;
    }

    @Override
    public void Draw(Graphics graphics){
        super.Draw(graphics);
    }

    @Override
    protected boolean collides(GameObject object) {
        return false;
    }

    @Override
    public void move(int x, int y) {
    }

    @Override
    public void update() {
        super.update();
        hitboxCircle.setFrame(getX()+sprite.getWidth()/2-radius,getY()+sprite.getHeight()/2-radius,radius*2,radius*2);

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

    public void startBounce() {
        if (!isBouncing()){
            isBouncing = true;
            vy = -5; // set initial upward velocity for bouncing
        }
    }

    public boolean isBouncing() {
        return isBouncing;
    }

    @Override
    public Helpers.Vector2 getCenter() {
        return null;
    }
}


