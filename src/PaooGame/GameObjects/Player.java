package PaooGame.GameObjects;

import PaooGame.GameWindow.GameWindow;
import PaooGame.UserInterface.Keyboard;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    public static int STEP = 6;
    public static final double JUMP_VELOCITY = -30;
    public static final double GRAVITY = 1;
    private double yVelocity = 0;
    private boolean isJumping = false;
    public Rectangle hitBox;

    private PlayerControlTemplate playerControls;
    public Player(BufferedImage sprite, int x, int y,int hitBoxX,int hitBoxY) {
        super(sprite, x, y);
        hitBox = new Rectangle(x,y,hitBoxX,hitBoxY);
    }

    public void setKeys(int []keys){
        playerControls = new PlayerControlTemplate(keys);
    }

    @Override
    public void Draw(Graphics graphics) {
        super.Draw(graphics);
        graphics.setColor(Color.blue);

        int tempX = x + sprite.getWidth() / 2 -  hitBox.width / 2;
        int tempY  = y + sprite.getHeight() /2 - hitBox.height/ 2;

        graphics.drawRect(tempX, tempY, hitBox.width, hitBox.height);
    }

    @Override
    public void move(int x, int y) {
        // Get the current position of the player
        int currentX = getX();
        int currentY = getY();

        hitBox.y = this.y;
        hitBox.x = this.x;

        // Calculate the new position of the player
        int newX = currentX + x;
        int newY = currentY + y;

        // Check if the new position is inside the game window
        if (newX < 0) {
            newX = 0;
        } else if (newX + sprite.getWidth() > GameWindow.GetWndWidth()) {
            newX = GameWindow.GetWndWidth() - sprite.getWidth();
        }

        if (newY < 0) {
            newY = 0;
        } else if (newY + sprite.getHeight() > GameWindow.GetWndHeight()) {
            newY = GameWindow.GetWndHeight() - sprite.getHeight();
        }

        // Set the new position of the player
        setX(newX);
        setY(newY);

        for(GameObject obj: getGameObjects()){
            if (this != obj && !(obj instanceof Ball) && this.collides(obj)){
                setX(currentX);
                setY(currentY);
            }
        }
    }
    @Override
    public void setX(int x){
        super.setX(x);
        hitBox.x = x;
    }


    @Override
    public void setY(int y){
        super.setY(y);
        hitBox.y = y;
    }
    @Override
    public void update() {

        int deltaY = 0, deltaX = 0;

        if (Keyboard.isKeyPressed(playerControls.getDownKey())) {
            deltaY += STEP;
        }

        if (Keyboard.isKeyPressed(playerControls.getLeftKey())) {
            deltaX -= STEP;
        }

        if (Keyboard.isKeyPressed(playerControls.getRightKey())) {
            deltaX += STEP;
        }

        boolean collidingWithPlayer = false;
        if (Keyboard.isKeyPressed(playerControls.getUpKey()) && !isJumping) {
            yVelocity = JUMP_VELOCITY;
            isJumping = true;
        }

        // Update player's position and velocity based on time
        double time = 0.5; // adjust this to control the time step
        double newYVelocity = yVelocity + GRAVITY * time;
        double avgYVelocity = (yVelocity + newYVelocity) / 2;
        double newY = getY() + avgYVelocity * time;

        // Apply air resistance to the jump
        newYVelocity = Math.max(newYVelocity, -30);

        // Check if the player is colliding with another player
        for (GameObject obj : getGameObjects()) {
            if (obj != this && this.collides(obj)) {
                collidingWithPlayer = true;
                break;
            }
        }

        // Adjust player's position and velocity based on collisions
        if (collidingWithPlayer) {
            // Cancel the jump
            newYVelocity = 0;
            isJumping = false;
        } else if (newY > GameWindow.GetWndHeight() - sprite.getHeight()) {
            // Player has landed
            isJumping = false;
            newY = GameWindow.GetWndHeight() - sprite.getHeight();
        }

        setY((int) newY);
        yVelocity = newYVelocity;

        move(deltaX, deltaY);
    }
    @Override
    protected boolean collides(GameObject other) {
        if (other instanceof Player){
            Rectangle rect1 = this.hitBox;
            Rectangle rect2 = ((Player) other).hitBox;

            System.out.println(rect1.intersects(rect2));
            return rect1.intersects(rect2);
        }

        return false;
    }
}
