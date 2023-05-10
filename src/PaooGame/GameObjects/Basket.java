package PaooGame.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Basket extends GameObject {
    public Rectangle hitBox;


    public Basket(BufferedImage sprite, int x, int y,int hitBoxX,int hitBoxY) {
        super(sprite, x, y);
        hitBox = new Rectangle(x,y,hitBoxX,hitBoxY);
    }

    public void Draw(Graphics g){
        super.Draw(g);
    }

    @Override
    protected boolean collides(GameObject object) {
        return false;
    }
}
