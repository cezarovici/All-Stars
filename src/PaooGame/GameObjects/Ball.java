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
    public Ball(BufferedImage sprite, int x, int y, float radius) {
        super(sprite,x,y,Shape.Type.Circle);
        shape.radius = radius;
    }

    public static Ball getInstance(BufferedImage sprite, int x, int y, float radius) {
        if (instance == null) {
            instance = new Ball(sprite, x, y,radius);
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

    @Override
    public void move(int x, int y) {

    }

    @Override
    public Helpers.Vector2 getCenter() {
        return null;
    }
}


