package PaooGame.GameObjects;

import PaooGame.Game;
import PaooGame.ImpulseEngine.Body;
import PaooGame.ImpulseEngine.Shape;
import PaooGame.ImpulseEngine.Vec2;
import PaooGame.UserInterface.Mouse;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class GameObject {
    protected BufferedImage sprite;
    public Shape shape;
    protected int id;
    public static ArrayList<GameObject> gameObjects = new ArrayList<>();
    // array of game objects

    protected GameObject(BufferedImage sprite,int x , int y,Shape.Type type){
        this.shape = Shape.ShapeBuilder(type);
        this.shape.setBody(new Body(shape,x,y));

        // initialize the sprite
        this.sprite = sprite;

        // save any instance of an object
        // in game objects list
        gameObjects.add(this);
    }

    protected GameObject(int x , int y,Shape.Type type){
        this.shape = Shape.ShapeBuilder(type);
        this.shape.setBody(new Body(shape,x,y));

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

    public abstract void move(int x, int y);

    public abstract Helpers.Vector2 getCenter();

    public  void setY(int y){
        shape.body.position.y = (float)y;
    }

    public void setX(int x){
        shape.body.position.x = (float)x;
    }

    public void update(){
        setY((int) shape.body.getPosition().y);
        setX((int)shape.body.getPosition().x);
    }

    public void moveByMouse(Mouse mouse){
        if (mouse.mouseUp[MouseEvent.BUTTON1]){
            setY(mouse.mouseY);
            setX(mouse.mouseX);
        }
    }

    public int getId() {
        return id;
    }

    public int getX(){
        return (int) shape.body.position.getX();
     }

     public int getY(){
        return (int) shape.body.position.getY();
    }
    public void print(){
        shape.body.print();
    }

    public void setPosition(int x, int y){
        this.shape.body.position.x = x;
        this.shape.body.position.y = y;
    }

    public Vec2 getPosition() {
        return shape.body.getPosition();
    }
}
