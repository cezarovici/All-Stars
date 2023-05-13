package PaooGame.GameObjects;

import PaooGame.GameWindow.GameWindow;
import PaooGame.ImpulseEngine.Circle;
import PaooGame.ImpulseEngine.Shape;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Ball extends GameObject {

    private static Ball instance;
    public Ball(BufferedImage sprite, int x, int y,float radius,Shape shape) {
        super(sprite,x,y,shape);
        shape.radius = radius;
    }

    public static Ball getInstance(BufferedImage sprite, int x, int y, float radius,Shape shape) {
        if (instance == null) {
            instance = new Ball(sprite, x, y,radius,shape);
        }

        return instance;
    }

    @Override
    public void Draw(Graphics graphics){
        super.Draw(graphics);
    }
    @Override
    protected boolean collides(GameObject object) {
        return false;
    }
}
