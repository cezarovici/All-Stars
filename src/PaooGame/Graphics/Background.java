package PaooGame.Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background
{

    private final BufferedImage       background;
    private static final int    backgroundWidth   = 1920;
    private static final int    backgroundHeight  = 450;


    public Background(BufferedImage buffImg)
    {
        background = buffImg;
    }

    public BufferedImage getBackground()
    {
        return background.getSubimage(0,0, backgroundWidth, backgroundHeight);
    }

    public void Draw(Graphics graphics){
        graphics.drawImage(background,0,(int)(1.45*backgroundHeight),null);
    }
}

