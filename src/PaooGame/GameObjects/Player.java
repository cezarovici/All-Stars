package PaooGame.GameObjects;

import PaooGame.GameWindow.GameWindow;
import PaooGame.UserInterface.Keyboard;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    public static final int STEP = 10;
    public static final double JUMP_VELOCITY = -20;
    public static final double GRAVITY = 1;
    private double yVelocity = 0;
    private boolean isJumping = false;

    public Player(BufferedImage sprite, int x, int y) {
        super(sprite, x, y);
    }

    @Override
    public void Draw(Graphics graphics) {
        super.Draw(graphics);
    }

    @Override
    public void move(int x, int y){
        super.move(x,y);
    }

    @Override
    public void update() {
        int deltaY = 0, deltaX = 0;

        if (Keyboard.isKeyPressed(KeyEvent.VK_S)) {
            deltaY += STEP;
        }

        if (Keyboard.isKeyPressed(KeyEvent.VK_A)) {
            deltaX -= STEP;
        }

        if (Keyboard.isKeyPressed(KeyEvent.VK_D)) {
            deltaX += STEP;
        }

        if (Keyboard.isKeyPressed(KeyEvent.VK_W) && !isJumping) {
            yVelocity = JUMP_VELOCITY;
            isJumping = true;
        }

        // Update player's position and velocity based on time
        double time = 0.1; // adjust this to control the time step
        double newYVelocity = yVelocity + GRAVITY * time;
        double avgYVelocity = (yVelocity + newYVelocity) / 2;
        double newY = getY() + avgYVelocity * time;

        if (newY > GameWindow.GetWndHeight() - sprite.getHeight()) { // Player has landed
            isJumping = false;
            newY = GameWindow.GetWndHeight() - sprite.getHeight();
        }

        setY((int) newY);
        yVelocity = newYVelocity;

        move(deltaX, deltaY);
    }
}
