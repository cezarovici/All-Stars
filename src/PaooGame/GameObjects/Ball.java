package PaooGame.GameObjects;

import PaooGame.GameWindow.GameWindow;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Ball extends GameObject{

    private double vx = 0; // horizontal velocity of the ball
    final double MAX_VX = 5; // maximum horizontal velocity of the ball
    private double vy; // vertical velocity
    private boolean isBouncing; // flag to track if the ball is currently bouncing
    private final double radius;
    private static Ball instance = null;
    private final Ellipse2D.Double hitboxCircle;


    public Ball(BufferedImage sprite, int x, int y,double radius) {
        super(sprite,x,y);
        this.radius = radius;

        this.vy = 0; // initialize velocity to 0
        this.isBouncing = false; // initialize bouncing flag to false
        hitboxCircle = new Ellipse2D.Double(x+sprite.getWidth()/2,y+sprite.getHeight()/2,radius*2,radius*2);
    }


    public static Ball getInstance(BufferedImage sprite, int x, int y, double radius) {
        if (instance == null) {
            instance = new Ball(sprite, x, y,radius);
        }

        return instance;
    }

    public void update() {
        hitboxCircle.setFrame(x+sprite.getWidth()/2-radius,y+sprite.getHeight()/2-radius,radius*2,radius*2);

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
        Helpers.Vector2 ballStart = getCenter();  // Current position of the ball
        Graphics2D g2d = (Graphics2D)g;

        for (GameObject obj : getGameObjects()) {
            if (obj instanceof Player) {
                Helpers.Bounds playerBounds = new Helpers.Bounds(((Player) obj).bounds); /* player's bounding box coordinates */
                g2d.setColor(Color.CYAN);
                g2d.draw(new Rectangle2D.Double(playerBounds.left,playerBounds.top,playerBounds.getWidth(),playerBounds.getHeight()));
                System.out.println(String.valueOf(playerBounds.left+playerBounds.top+playerBounds.getWidth()+playerBounds.getHeight()));
                ;
                Helpers.Vector2 ballEnd = obj.getCenter();  // Desired position of the ball after the hit
                float ballRadius = (float) radius; /* radius of the ball */

                g2d.setColor(Color.BLACK);
                g2d.draw(new Line2D.Float(ballStart.x, ballStart.y, ballEnd.x, ballEnd.y));

                g2d.setColor(Color.YELLOW);
                g2d.draw(new Ellipse2D.Float((float) (ballStart.x - radius), (float) (ballStart.y - radius), (float) (radius * 2), (float) (radius * 2)));
                g2d.draw(new Ellipse2D.Float((float) (ballEnd.x - radius), (float) (ballEnd.y - radius), (float) (radius * 2), (float) (radius * 2)));

                Helpers.Intersection inter = Helpers.Intersection.handleIntersection(playerBounds, ballStart, ballEnd, ballRadius);
                if (inter != null) {

                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.drawString("time: " + inter.time, 10, 20);

                    g2d.setColor(Color.GRAY);
                    g2d.draw(new Ellipse2D.Float((float) (inter.cx - radius), (float) (inter.cy - radius), (float) (radius * 2), (float) (radius * 2)));
                    g2d.draw(new Line2D.Float(inter.cx, inter.cy, inter.cx + inter.nx * 20, inter.cy + inter.ny * 20));

                    g2d.setColor(Color.RED);
                    g2d.draw(new Ellipse2D.Float(inter.ix - 2, inter.iy - 2, 4, 4));

                    // Project Future Position
                    float remainingTime = 1.0f - inter.time;
                    float dx = ballEnd.x - ballStart.x;
                    float dy = ballEnd.y - ballStart.y;
                    float dot = dx * inter.nx + dy * inter.ny;
                    float ndx = dx - 2 * dot * inter.nx;
                    float ndy = dy - 2 * dot * inter.ny;
                    float newx = inter.cx + ndx * remainingTime;
                    float newy = inter.cy + ndy * remainingTime;

                    g2d.setColor(Color.darkGray);
                    g2d.draw(new Ellipse2D.Float((float) (newx - radius), (float) (newy - radius), (float) (radius * 2), (float) (radius * 2)));
                    g2d.draw(new Line2D.Float(inter.cx, inter.cy, newx, newy));
                }
            }
        }

    }

    protected boolean collides(GameObject object) {
        // Calculate the distance between the center of the ball and the player's hitbox
        if  (!(object instanceof Player player)) {
            return false;
        }

        return hitboxCircle.intersects(player.getHitBox());
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
