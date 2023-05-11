package PaooGame.GameObjects;

import PaooGame.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class GameObject {
    protected final BufferedImage sprite;
    public static ArrayList<GameObject> gameObjects = new ArrayList<>(); // array of game objects
    protected int x , y;

    protected GameObject(BufferedImage sprite,int x , int y){
        this.sprite = sprite;
        this.x = x;
        this.y = y;

        gameObjects.add(this); // save any instance of an object
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

    protected int getX(){
        return x;
    }
    protected int getY(){
        return y;
    }

    protected void setY(int y){
        this.y = y;
    }
    protected void setX(int x){
        this.x = x;
    }

    protected abstract boolean collides(GameObject object);


    public ArrayList<GameObject> getGameObjects(){
        return gameObjects;
    }
     protected void update(){

     }
}
