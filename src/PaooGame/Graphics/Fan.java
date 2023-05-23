package PaooGame.Graphics;

import PaooGame.GameWindow.GameWindow;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fan {
    private BufferedImage fan;
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

    public static void setFansImage(BufferedImage image,Fan[] fans){
        // Set up the game bounds
        int gameWidth = GameWindow.GetWndWidth();
        int gameHeight = GameWindow.GetWndHeight();
        int numFans = 100; // The total number of fans
        int numRows = 4; // The number of rows
        int fansPerRow = numFans / numRows; // The number of fans per row

        int rowHeight = gameHeight / (numRows * 5); // The height of each row
        int fanSpacing = gameWidth / fansPerRow; // The spacing between fans in each row

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < fansPerRow; j++) {
                int x = j * fanSpacing + fanSpacing / 2; // The x-coordinate of the fan
                int y = i * rowHeight + rowHeight / 2; // The y-coordinate of the fan

                fans[i * fansPerRow + j] = new Fan(image, x, y + 200); // Add the fan to the array
            }
        }
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