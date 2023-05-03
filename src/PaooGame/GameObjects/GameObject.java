package PaooGame.GameObjects;

import PaooGame.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class GameObject {
    protected final BufferedImage sprite;
    public static ArrayList<GameObject> gameObjects = new ArrayList<>(); // array of game objects
    public Rectangle hitBox;
    private int x , y;

    protected GameObject(BufferedImage sprite,int x , int y,int hitBoxX,int hitBoxY){
        this.sprite = sprite;
        this.x = x;
        this.y = y;

        hitBox = new Rectangle(x,y,hitBoxX,hitBoxY);

        gameObjects.add(this); // save any instance of an object
    }
    protected void Draw(Graphics graphics){
        graphics.setColor(Color.blue);

        int tempX = x + sprite.getWidth() / 2 -  hitBox.width / 2;
        int tempY  = y + sprite.getHeight() /2 - hitBox.height/ 2;

        graphics.drawRect(tempX, tempY, hitBox.width, hitBox.height);
        graphics.drawImage(sprite,x,y,null);
    }

    protected void moveY(int y){
        this.y += y;
        hitBox.y = this.y;
    }

    protected void moveX(int x){
        this.x += x;
        hitBox.x = this.x;
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
        hitBox.y = y;
    }
    protected void setX(int x){
        this.x = x;
        hitBox.x = x;
    }
    protected boolean collides(GameObject other) {
        Rectangle rect1 = this.hitBox;
        Rectangle rect2 = other.hitBox;

        return rect1.intersects(rect2);
    }


    public ArrayList<GameObject> getGameObjects(){
        return gameObjects;
    }
     protected void update(){

     }
}
