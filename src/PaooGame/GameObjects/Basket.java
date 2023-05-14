package PaooGame.GameObjects;

import PaooGame.ImpulseEngine.Shape;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Basket extends GameObject {
    public Rectangle hitBox;


    public Basket(BufferedImage sprite, int x, int y,int hitBoxX,int hitBoxY) {
        super(sprite, x, y, Shape.Type.Poly);
        hitBox = new Rectangle(x,y,hitBoxX,hitBoxY);
    }

    public void Draw(Graphics g){
        super.Draw(g);
    }

    @Override
    protected boolean collides(GameObject object) {
        return false;
    }

    @Override
    public void move(int x, int y) {

    }

    @Override
    public Helpers.Vector2 getCenter() {
        return null;
    }
}
