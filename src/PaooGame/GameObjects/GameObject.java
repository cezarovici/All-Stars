package PaooGame.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObject {
    private BufferedImage sprite;
    private int x , y;

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
}
