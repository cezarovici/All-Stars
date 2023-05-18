package PaooGame.UserInterface;

import java.awt.*;

public class Option {
    private String text;
    private Color color;
    private Font font;
    private Rectangle bounds;

    public boolean isActive;

    public Option(String text, Color color, Font font, int width, int height) {
        this.text = text;
        this.color = color;
        this.font = font;

        this.bounds = new Rectangle();
        this.bounds.width = width;
        this.bounds.height = height;
    }


    public String getText() {
        return text;
    }

    public void setColor(Color color) {
        this.color = color;
    }



    public void setActive(){
        isActive = true;
    }

    public void unActive(){
        isActive = false;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void setPosition(int x, int y) {
        bounds.setLocation(x, y);
    }

    public void draw(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;

        g2d.setColor(color);
        g2d.setFont(font);
        g2d.drawString(text, bounds.x+ bounds.width/2-text.length(), bounds.y+bounds.height/2-text.length());
    }
}
