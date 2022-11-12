package cosc202.andie;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class DrawActions {
    protected ArrayList<Action> actions;

    public DrawActions () {
        actions=new ArrayList<Action>();
        actions.add(new drawRectangleAction ("Draw rectangle", null, "draws a rectangle", Integer.valueOf(KeyEvent.VK_T)));
        actions.add(new drawLineAction ("Draw line", null, "draws a line", Integer.valueOf(KeyEvent.VK_L)));
        actions.add(new drawEllipseAction ("Draw ellipse", null, "draws an ellipse", Integer.valueOf(KeyEvent.VK_C)));
    }

    public JMenu createMenu() {
        JMenu drawMenu = new JMenu("Draw");

        for (Action action: actions) {
            drawMenu.add(new JMenuItem(action));
        }

        return drawMenu;
    }
    
    public class drawRectangleAction extends ImageAction {
        
        public drawRectangleAction (String name, ImageIcon icon, String desc, int mnemonic) {
            super (name, icon, desc, mnemonic);
        }

        public void actionPerformed (ActionEvent e) {
            if (!target.getImage().hasImage()) {
                JOptionPane.showMessageDialog(null, "There is no image open!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
           Color selectedColor =  showColorChooser(null, "Choose colour", null);
           if(selectedColor == null) return;
            target.getImage().apply(new drawRectangle(selectedColor, ImagePanel.mouse.getX(), ImagePanel.mouse.getY(), ImagePanel.mouse.getWidth(), ImagePanel.mouse.getHeight()));
            target.repaint();
            target.getParent().revalidate();
            }
        }
    }

        
    public class drawLineAction extends ImageAction {
        
        public drawLineAction (String name, ImageIcon icon, String desc, int mnemonic) {
            super (name, icon, desc, mnemonic);
        }

        public void actionPerformed (ActionEvent e) {
            if (!target.getImage().hasImage()) {
                JOptionPane.showMessageDialog(null, "There is no image open!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            Color selectedColor =  showColorChooser(null, "Choose colour", null);
                if(selectedColor == null) return;
            target.getImage().apply(new drawLine(selectedColor, ImagePanel.mouse.getX(), ImagePanel.mouse.getY(), ImagePanel.mouse.getWidth(), ImagePanel.mouse.getHeight()));
            target.repaint();
            target.getParent().revalidate();
            }
        }
    }
    

    public class drawEllipseAction extends ImageAction {
        public drawEllipseAction (String name, ImageIcon icon, String desc, int mnemonic) {
            super (name, icon, desc, mnemonic);
        }

        public void actionPerformed (ActionEvent e) {
            if (!target.getImage().hasImage()) {
                JOptionPane.showMessageDialog(null, "There is no image open!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            Color selectedColor =  showColorChooser(null, "Choose colour", null);
                if(selectedColor == null) return;
            target.getImage().apply(new drawEllipse(selectedColor, ImagePanel.mouse.getX(), ImagePanel.mouse.getY(), ImagePanel.mouse.getWidth(), ImagePanel.mouse.getHeight()));
            target.repaint();
            target.getParent().revalidate();
            }
        }
    }



    private Color showColorChooser(Component component, String title, Color initialColor){
        Color selectedColor;
        JColorChooser chooser = new JColorChooser(initialColor);
        chooser.setPreviewPanel(new JPanel());
        for(int i = 0; i < chooser.getChooserPanels().length; i++){
            if(chooser.getChooserPanels()[i].getDisplayName() != "RGB"){
                chooser.removeChooserPanel(chooser.getChooserPanels()[i]);
            }
        }
        selectedColor = JColorChooser.showDialog(component, title, initialColor);
        return selectedColor;
    }
}
