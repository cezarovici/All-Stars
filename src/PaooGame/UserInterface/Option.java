package PaooGame.UserInterface;

import java.awt.*;

public class Option {
    private String text;
    private Color color;
    private Font font;
    private Rectangle option;
    private Graphics2D graphics;
    private int x;
    private int y;

    public Option(String text, Color color, Font font,int width,int height, int x, int y) {
        this.text = text;
        setGraphics(font,color);
        option = new Rectangle(x,y, width,height);
    }

    public void setGraphics(Font font, Color color) {
        graphics.setFont(font);
        graphics.setColor(color);
    }

    public void Draw(Graphics graphics){
        Graphics2D graphics2d = (Graphics2D) graphics;

        graphics.drawString(text,x+x/2,y+y/2);
        graphics2d.draw(option);
    }
}
