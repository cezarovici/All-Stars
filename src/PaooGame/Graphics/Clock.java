package PaooGame.Graphics;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Rectangle {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 50;

    private int timeLeft;

    public Clock(int x, int y, int time) {
        super(x, y, WIDTH, HEIGHT);
        timeLeft = time;

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (timeLeft > 0) {
                    timeLeft--;
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask,1000,1000);
    }


    public void draw(Graphics graphics) {
        graphics.setColor(Color.orange);
        graphics.fillRect(x, y, WIDTH, HEIGHT);

        graphics.setColor(Color.BLACK);
        graphics.drawRect(x, y, WIDTH, HEIGHT);

        graphics.setFont(new Font("Arial", Font.PLAIN, 28));
        graphics.drawString(formatTime(timeLeft), x + 20, y + 30);
    }

    private String formatTime(int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    public boolean isTimeUp() {
        return timeLeft == 0;
    }
}
