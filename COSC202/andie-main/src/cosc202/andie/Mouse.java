package cosc202.andie;

import java.awt.event.*;
import java.awt.*;

public class Mouse implements MouseListener {
    private int INITIAL_X_POSITION=-1;
    private int INITIAL_Y_POSITION;
    private boolean isMouseReleased;
    private int width=0;
    private int height=0;

    public Mouse () {

    }

    public void mouseClicked (MouseEvent e) {
        INITIAL_X_POSITION=0;
        INITIAL_Y_POSITION=0;
        width=0;
        height=0;
        ImageAction.target.repaint();
    }

    public void mouseEntered (MouseEvent e) {
        //ImageAction.target.paintComponent(ImageAction.target.getGraphics());
    }

    public void mouseExited (MouseEvent e) {

    }

    public void mousePressed (MouseEvent e) {
            isMouseReleased=false;
            INITIAL_X_POSITION=e.getX();
            INITIAL_Y_POSITION=e.getY();
    }

    public void mouseReleased (MouseEvent e) {
        ImageAction.target.paintComponent(ImageAction.target.getGraphics());
        if (e.getX()!=INITIAL_X_POSITION && e.getY()!=INITIAL_Y_POSITION) {
            width=e.getX()-INITIAL_X_POSITION;
            height=e.getY()-INITIAL_Y_POSITION;
            if (width<0) {
                width=width*-1;
                INITIAL_X_POSITION=e.getX();
            }
            if (height<0) {
                height=height*-1;
                INITIAL_Y_POSITION=e.getY();
            }
        Graphics g=ImageAction.target.getGraphics();
        g.setColor(Color.BLACK);
        g.drawRect(INITIAL_X_POSITION, INITIAL_Y_POSITION, width, height);
        }
        isMouseReleased=true;
    }

    public int getWidth () {
        return width;
    }

    public int getHeight () {
        return height;
    }

    public int getX() {
        return INITIAL_X_POSITION;
    }

    public int getY() {
        return INITIAL_Y_POSITION;
    }
 }
