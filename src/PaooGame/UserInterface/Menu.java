package PaooGame.UserInterface;

import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Background;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.event.KeyEvent.*;

public class Menu {
    private final List<Option> options;
    private final int x;
    private final int y;
    private final int spacing;
    private final Background background;
    public int lastActive = -1;
    private boolean keyPressed;
    public boolean start;
    public boolean stop;
    public boolean levels;
    private boolean update;
    public  boolean save;

    public Menu(int x, int y, int spacing, Background background) {
        options = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.spacing = spacing;
        this.background = background;
    }
    public void addOption(Option option) {
        options.add(option);
        calculateOptionPositions();

        if (lastActive == -1){
            lastActive = 0;
        }
    }

    public void calculateOptionPositions() {
        int offsetY = y;

        for (Option option : options) {
            option.setPosition(x, offsetY);
            offsetY += spacing;
        }
    }

    public void handleInput() {
        if (Keyboard.isKeyPressed(VK_UP)) {
            // Move selection up
            moveSelectionUp();
            keyPressed = true;
        } else if (Keyboard.isKeyPressed(VK_DOWN)) {
            // Move selection down
            moveSelectionDown();
            keyPressed = true;
        } else if (Keyboard.isKeyPressed(VK_ENTER)) {
            keyPressed = true;
            switch (getSelectedOption().getText()) {
                case "Start Game" -> start = true;
                case "EXIT" -> stop = true;
                case "Levels" -> levels = true;
                case "Save Game" -> save = true;
            }
        }
        else{
            keyPressed = false;
        }
    }


    private void moveSelectionUp() {
        if(lastActive > 0 && update) {
            lastActive--;
        }
    }

    private void moveSelectionDown() {
        if(lastActive < options.size()-1 && update){
            lastActive++;
        }
    }

    private void handleSelection() {
        handleInput();

        if (!keyPressed){
            update = true;
        }else if (update) {

            resetActive();
            getSelectedOption().setActive();

            update = false;
        }
    }

    private Option getSelectedOption() {
        return options.get(lastActive);
    }

    private void resetActive(){
        for(Option option:  options){
            option.unActive();
        }
    }
    public void draw(Graphics graphics) {
       handleSelection();

        Graphics2D g2d = (Graphics2D) graphics;

        g2d.drawImage(background.background.getSubimage(0,0,1920,1080),0,0,null);
        g2d.setColor(Color.BLACK);

        for (Option option : options) {
            option.draw(graphics);

            if (option.isActive){
                option.setColor(Color.ORANGE);
                option.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,50));
            }
            else{
                option.setColor(Color.WHITE);
                option.setFont(new Font(Font.SERIF,Font.PLAIN,50));
            }
        }
    }

    public boolean StartGame(){
        return start;
    }
    public boolean StopGame(){
        return stop;
    }
    public boolean Levels(){
        return levels;
    }
    public boolean Save(){return save;}
}
