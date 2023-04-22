package PaooGame.GameObjects;

import PaooGame.GameWindow.GameWindow;
import PaooGame.UserInterface.Keyboard;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    public static int STEP = 6;
    public static final double JUMP_VELOCITY = -20;
    public static final double GRAVITY = 1;
    private double yVelocity = 0;
    private boolean isJumping = false;

    private PlayerControlTemplate playerControls;
    public Player(BufferedImage sprite, int x, int y) {
        super(sprite, x, y);
    }

    public void setKeys(int []keys){
        playerControls = new PlayerControlTemplate(keys);
    }

    private void checkCollisions(){
        for(GameObject obj: getGameObjects()){
            if (this != obj && this.collides(obj)){
                STEP = -6;
                System.out.println("COLLIDEEESS !!!!");
            }
        }
    }

    @Override
    public void Draw(Graphics graphics) {
        super.Draw(graphics);
    }

    @Override
    public void move(int x, int y) {
        STEP = 6;
        // Get the current position of the player
        int currentX = getX();
        int currentY = getY();

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

        checkCollisions();
        // Set the new position of the player
        setX(newX);
        setY(newY);
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

        // Adjust this value to control air resistance
        if (newY > GameWindow.GetWndHeight() - sprite.getHeight()) {
            // Player has landed
            isJumping = false;
            newY = GameWindow.GetWndHeight() - sprite.getHeight();
        }

        setY((int) newY);
        yVelocity = newYVelocity;

        move(deltaX, deltaY);
    }
}
