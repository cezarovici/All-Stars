package PaooGame.GameObjects;

import PaooGame.ImpulseEngine.Body;
import PaooGame.ImpulseEngine.Shape;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class GameObject {
    protected final BufferedImage sprite;
    protected Shape shape;
    public static ArrayList<GameObject> gameObjects = new ArrayList<>(); // array of game objects

    protected GameObject(BufferedImage sprite,int x , int y,Shape.Type type){
        this.shape = new shape.ShapeBuilder(type);

        // initialize the sprite
        this.sprite = sprite;

        // save any instance of an object
        // in game objects list
        gameObjects.add(this);
    }
    protected void Draw(Graphics graphics){
        graphics.drawImage(sprite, (int) shape.body.position.x, (int) shape.body.position.y,null);
    }

    protected abstract boolean collides(GameObject object);

    public ArrayList<GameObject> getGameObjects(){
        return gameObjects;
    }
     protected void update(){

     }
}
