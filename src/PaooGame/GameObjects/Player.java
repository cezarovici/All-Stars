package PaooGame.GameObjects;

import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.ImageLoader;
import PaooGame.Graphics.SpriteSheet;
import PaooGame.ImpulseEngine.Polygon;
import PaooGame.ImpulseEngine.Shape;
import PaooGame.ImpulseEngine.Vec2;
import PaooGame.UserInterface.Keyboard;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    public static int STEP = 6;
    public static final double JUMP_VELOCITY = -30;
    public static final double GRAVITY = 1;
    private double yVelocity = 0;
    private boolean isJumping = false;
    public Rectangle hitBox;
    public String name;
    final SpriteSheet players = new SpriteSheet(ImageLoader.LoadImage("/textures/Players.png"));


    public Helpers.Bounds bounds;
    private PlayerControlTemplate playerControls;
    public Player(int id,int x, int y, int hitBoxX, int hitBoxY) {
        super(x, y, Shape.Type.Poly);

        int i = id / 4 ;
        int j = id % 4 ;
        this.sprite = players.crop( i , j);

        int tempX = x + sprite.getWidth() / 2 -  hitBoxX / 2;
        int tempY  = y + sprite.getHeight() /2 - hitBoxY/ 2;

        hitBox = new Rectangle(tempX,tempY,hitBoxX,hitBoxY);
        bounds = new Helpers.Bounds(tempX,tempY,tempX+hitBoxX,tempY+hitBoxY);

        this.id = id;

        Vec2 v1 = new Vec2(hitBox.x,hitBox.y);
        Vec2 v2 = new Vec2(hitBox.x+hitBoxX,hitBox.y);
        Vec2 v3 = new Vec2(hitBox.x+hitBoxX,hitBox.y+hitBoxY);
        Vec2 v4 = new Vec2(hitBox.x,hitBox.y+hitBoxY);

        ((Polygon) shape).set(v1,v2,v3,v4);
        //((Polygon) shape).print();
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setKeys(int []keys){
        playerControls = new PlayerControlTemplate(keys);
    }

    public Rectangle getHitBox(){
        return new Rectangle(hitBox.x,hitBox.y,hitBox.width,hitBox.height);
    }
    @Override
    public void Draw(Graphics graphics) {
        super.Draw(graphics);
        graphics.setColor(Color.BLACK);
        //graphics.drawString(name, (int) getCenter().x, (int) getCenter().y);
    }

    public boolean isJumping(){
        return isJumping;
    }
    @Override
    public void move(int x, int y) {
        // Get the current position of the player
        int currentX = getX();
        int currentY = getY();

        // Calculate the new position of the player
        int newX = currentX + x;
        int newY = currentY + y;

        // Check if the new position is inside the game window
        if (newX < 0) {
            newX = 0;
        } else if (newX + sprite.getWidth() > GameWindow.GetWndWidth()) {
            newX = GameWindow.GetWndWidth() - sprite.getWidth();
        }

        if (newY < 0) {
            newY = 0;
        } else if (newY + sprite.getHeight() > GameWindow.GetWndHeight()) {
            newY = GameWindow.GetWndHeight() - sprite.getHeight();
        }

        // Set the new position of the player
        setX(newX);
        setY(newY);

        for(GameObject obj: getGameObjects()){
            if (this != obj && !(obj instanceof Ball) && this.collides(obj)){
                setX(currentX);
                setY(currentY);
            }
        }
    }
    @Override
    public void setX(int x){
        super.setX(x);
        hitBox.x = x + sprite.getWidth() / 2 -  hitBox.width / 2;
        bounds.left = hitBox.x;
        bounds.right = hitBox.x + hitBox.width;
    }


    @Override
    public Helpers.Vector2 getCenter(){
        return new Helpers.Vector2(hitBox.x+hitBox.width/2,hitBox.y+hitBox.height/2);
    }

    @Override
    public void setY(int y){
        super.setY(y);
        hitBox.y = y + sprite.getHeight() /2 - hitBox.height/2;
        bounds.top = hitBox.y;
        bounds.bottom = hitBox.y + hitBox.height;
    }
    @Override
    public void update() {
     // super.update();

        int deltaY = 0, deltaX = 0;

        if (Keyboard.isKeyPressed(playerControls.getDownKey())) {
            deltaY += STEP;
        }

        if (Keyboard.isKeyPressed(playerControls.getLeftKey())) {
            deltaX -= STEP;
        }

        if (Keyboard.isKeyPressed(playerControls.getRightKey())) {
            deltaX += STEP;
        }

        boolean collidingWithPlayer = false;
        if (Keyboard.isKeyPressed(playerControls.getUpKey()) && !isJumping) {
            yVelocity = JUMP_VELOCITY;
            isJumping = true;
        }

        // Update player's position and velocity based on time
        double time = 0.5; // adjust this to control the time step
        double newYVelocity = yVelocity + GRAVITY * time;
        double avgYVelocity = (yVelocity + newYVelocity) / 2;
        double newY = getY() + avgYVelocity * time;

        // Apply air resistance to the jump
        newYVelocity = Math.max(newYVelocity, -30);

        // Check if the player is colliding with another player
        for (GameObject obj : getGameObjects()) {
            if (obj != this && this.collides(obj)) {
                collidingWithPlayer = true;
                break;
            }
        }

        // Adjust player's position and velocity based on collisions
        if (collidingWithPlayer) {
            // Cancel the jump
            newYVelocity = 0;
            isJumping = false;
        } else if (newY > GameWindow.GetWndHeight() - sprite.getHeight()) {
            // Player has landed
            isJumping = false;
            newY = GameWindow.GetWndHeight() - sprite.getHeight();
        }

        setY((int) newY);
        yVelocity = newYVelocity;

        bounds.update(hitBox);
        move(deltaX, deltaY);
    }

    @Override
    protected boolean collides(GameObject other) {
        if (other instanceof Player){
            Rectangle rect1 = this.hitBox;
            Rectangle rect2 = ((Player) other).hitBox;

            return rect1.intersects(rect2);
        }

        return false;
    }
}
