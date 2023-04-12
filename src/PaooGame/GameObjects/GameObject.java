package PaooGame.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    private final BufferedImage sprite;
    private int x , y;

    protected GameObject(BufferedImage sprite,int x , int y){
        this.sprite = sprite;
        this.x = x;
        this.y = y;
    }
    protected void Draw(Graphics graphics){
        graphics.drawImage(sprite,x,y,null);
    }

    protected void moveY(int y){
        this.y += y;
    }

    protected void moveX(int x){
        this.x += x;
    }

    protected void move(int x, int y){
        moveY(y);
        moveX(x);
    }

    abstract void update();
}
