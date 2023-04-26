package PaooGame.GameObjects;

import PaooGame.GameObjects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Basket extends GameObject {

    public Basket(BufferedImage sprite, int x, int y,int hitBoxX,int hitBoxY) {
        super(sprite, x, y,hitBoxX,hitBoxY);
    }

    public void Draw(Graphics g){
        super.Draw(g);
    }
}
