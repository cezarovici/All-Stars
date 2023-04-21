package PaooGame.Graphics;

import PaooGame.GameWindow.GameWindow;

import java.awt.*;

public class RunningAd {
    private final String text;
    private int x, y;
    private final int speed;

    public RunningAd(String text, int x, int y, int speed) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void update() {
        // Move the text to the left

        x -= speed;

        // If the text is off the screen, reset its position to the right
        if (x + text.length() * 10 < 0) {
            x = GameWindow.GetWndWidth();
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, text.length() * 40, 80);

        // Draw the text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.drawString(text, x+20, y+50);
    }
}
