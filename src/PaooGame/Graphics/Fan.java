package PaooGame.Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fan {
    private final BufferedImage fan;
    private int x;
    private int y;
    private static final int    fanWidth   = 32;
    private static final int    fanHeight  = 32;

    public Fan(BufferedImage fan,int x,int y) {
        this.fan = fan;
        this.x = x;
        this.y = y;
    }

    public void Draw(Graphics graphics){
        graphics.drawImage(fan,x,y,null);
    }
}
