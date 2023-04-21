package PaooGame.Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fan {
    private final BufferedImage fan;
    private int x;
    private int y;
    private static final int    fanWidth   = 32;
    private static final int    fanHeight  = 32;

    private static final int seatWidth = 10;
    private static final int seatHeight = 10;
    private static final int seatPadding = 5;

    public Fan(BufferedImage fan, int x, int y) {
        this.fan = fan;
        this.x = x;
        this.y = y;
    }

    public void Draw(Graphics graphics){
        graphics.drawImage(fan,x,y,null);
        // Draw two seats for each fan

        int seatX1 = x - seatWidth - seatPadding;
        int seatX2 = x + fanWidth + seatPadding;
        int seatY = y - seatHeight - seatPadding;

        // Draw first seat
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRoundRect(seatX1, seatY, seatWidth, seatHeight, 10, 10);
        graphics.setColor(Color.DARK_GRAY);
        graphics.drawRoundRect(seatX1, seatY, seatWidth, seatHeight, 10, 10);

        // Draw second seat
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRoundRect(seatX2, seatY, seatWidth, seatHeight, 10, 10);
        graphics.setColor(Color.DARK_GRAY);
        graphics.drawRoundRect(seatX2, seatY, seatWidth, seatHeight, 10, 10);
    }
}