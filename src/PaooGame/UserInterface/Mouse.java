package PaooGame.UserInterface;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Mouse implements MouseInputListener {
    public enum GameMouseType
    {
        Click, Press, Release, Enter, Exit
    }
    public static class GameMouseEvent
    {

        public GameMouseType type;
        public MouseEvent e;

        public GameMouseEvent( GameMouseType type, MouseEvent e )
        {
            this.type = type;
            this.e = e;
        }
    }

    public int mouseX, mouseY, mouseDownCount, mouseUpCount;
    public boolean[] mouseDown = new boolean[MouseInfo.getNumberOfButtons()];
    public boolean[] mouseUp = new boolean[MouseInfo.getNumberOfButtons()];
    public boolean mouseInside = true;
    public boolean mouseDragging = false;
    public boolean mouseMoving = false;
    public boolean mouseEventsQueue = false;
    public Queue<GameMouseEvent> mouseEvents = new ConcurrentLinkedQueue<>();

    public void clear()
    {
        mouseDownCount = 0;
        mouseUpCount = 0;
        mouseDragging = false;
        mouseMoving = false;
        mouseEvents.clear();

        Arrays.fill(mouseUp, false);
    }
    @Override
    public void mouseDragged( MouseEvent e )
    {
        mouseDragging = true;
        mouseMoving = true;
    }

    @Override
    public void mouseMoved( MouseEvent e )
    {
        mouseDragging = false;
        mouseMoving = true;
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked( MouseEvent e )
    {
        if (mouseEventsQueue)
        {
            mouseEvents.offer(new GameMouseEvent(GameMouseType.Click, e));
        }
    }

    @Override
    public void mousePressed( MouseEvent e )
    {
        mouseDown[e.getButton()] = true;
        mouseDownCount++;

        if (mouseEventsQueue)
        {
            mouseEvents.offer(new GameMouseEvent(GameMouseType.Press, e));
        }
    }

    @Override
    public void mouseReleased( MouseEvent e )
    {
        mouseDown[e.getButton()] = false;
        mouseUp[e.getButton()] = true;
        mouseUpCount++;

        if (mouseEventsQueue)
        {
            mouseEvents.offer(new GameMouseEvent(GameMouseType.Release, e));
        }
    }

    @Override
    public void mouseEntered( MouseEvent e )
    {
        mouseInside = true;

        if (mouseEventsQueue)
        {
            mouseEvents.offer(new GameMouseEvent(GameMouseType.Enter, e));
        }
    }

    @Override
    public void mouseExited( MouseEvent e )
    {
        mouseInside = false;

        if (mouseEventsQueue)
        {
            mouseEvents.offer(new GameMouseEvent(GameMouseType.Exit, e));
        }
    }
}
