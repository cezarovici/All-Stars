package PaooGame.GameObjects;

import PaooGame.UserInterface.Keyboard;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    public static int STEP = 2;

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

    public void jump(){
        int jumpLength = 100;
        for(int i = 0 ; i < jumpLength ; i++){
            move(0,-1);
        }

//        for(int i = 0 ; i < jumpLength ; i++){
//            move(0,-1);
//        }
    }
    @Override
    public void update() {
        int deltaY = 0, deltaX = 0;

        if (Keyboard.isKeyPressed(KeyEvent.VK_S)) {
            deltaY += STEP;
        }

        if (Keyboard.isKeyPressed(KeyEvent.VK_W)) {
            jump();
        }

        if (Keyboard.isKeyPressed(KeyEvent.VK_A)) {
            deltaX -= STEP;
        }

        if (Keyboard.isKeyPressed(KeyEvent.VK_D)) {
            deltaX += STEP;
        }

        move(deltaX, deltaY);
    }
}
