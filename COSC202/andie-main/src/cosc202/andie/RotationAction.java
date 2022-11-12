package cosc202.andie;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class RotationAction {

    /** A list of actions for the Rotation menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Rotation menu actions.
     * </p>
     */
    public RotationAction() {
        actions = new ArrayList<Action>();
        actions.add(new flipHorizontalAction("Flip horizontally", null, "flips image horizontally",
                Integer.valueOf(KeyEvent.VK_H), KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.SHIFT_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx())));
        actions.add(new flipVerticalAction("Flip Vertically", null, "flips image Vertically",
                Integer.valueOf(KeyEvent.VK_V)));
        actions.add(
                new RotateRightActions("Rotate right", null, "Rotate image to the right", Integer.valueOf(KeyEvent.VK_R)));
                actions.add(
                    new RotateLeftActions("Rotate left", null, "Rotate image to the left", Integer.valueOf(KeyEvent.VK_L)));

    }

    /**
     * <p>
     * Create a menu contianing the list of Rotation actions.
     * </p>
     * 
     * @return The Rotation menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Rotations");

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Image action to apply the flipHorizontal method.
     * </p>
     */ 
    public class flipHorizontalAction extends ImageAction {

        flipHorizontalAction(String name, ImageIcon icon, String desc, Integer mnemonic, KeyStroke keyStroke) {
            super(name, icon, desc, mnemonic, keyStroke);
        }

        flipHorizontalAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                JOptionPane.showMessageDialog(null, "There is no image open!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                target.getImage().apply(new flipHorizontal());
                target.repaint();
                target.getParent().revalidate();
            }
        }

    }

    /**
     * <p>
     * Image action to apply the flipVertical method.
     * </p>
     */
    public class flipVerticalAction extends ImageAction {

        flipVerticalAction(String name, ImageIcon icon, String desc, Integer mnemonic, KeyStroke keyStroke) {
            super(name, icon, desc, mnemonic, keyStroke);
        }

        flipVerticalAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                JOptionPane.showMessageDialog(null, "There is no image open!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                target.getImage().apply(new flipVertical());
                target.repaint();
                target.getParent().revalidate();
            }
        }

    }

    /**
     * <p>
     * Action to rotate image to the right by theta
     * </p>
     */
    public class RotateRightActions extends ImageAction {
        /**
         * <p>
         * Create a new rotate right action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         * @param keyStroke A keyStroke for keyboard shortcut
         */
        RotateRightActions(String name, ImageIcon icon, String desc, Integer mnemonic, KeyStroke keyStroke) {
            super(name, icon, desc, mnemonic, keyStroke);
        }

        /**
         * <p>
         * Create a new rotate right action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        RotateRightActions(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                JOptionPane.showMessageDialog(null, "There is no image open!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                // Determined the radius
                double theta = 90;

                // Create and apply the filter
                target.getImage().apply(new Rotate(theta));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

     /**
      * <p>
     * Action to rotate image to the left by theta
     * </p>
     */
    public class RotateLeftActions extends ImageAction {
         /**
          * <p>
          * Create a new rotate left action.
          * </p>
          *
          * @param name The name of the action (ignored if null).
          * @param icon An icon to use to represent the action (ignored if null).
          * @param desc A brief description of the action  (ignored if null).
          * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
          */
        RotateLeftActions(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }


         /**
          * <p>
          * Create a new rotate left action
          * </p>
          *
          * @param name The name of the action (ignored if null).
          * @param icon An icon to use to represent the action (ignored if null).
          * @param desc A brief description of the action  (ignored if null).
          * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
          * @param keyStroke A keyStroke for keyboard shortcut
          */
         RotateLeftActions(String name, ImageIcon icon, String desc, Integer mnemonic, KeyStroke keyStroke) {
             super(name, icon, desc, mnemonic, keyStroke);
         }

        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                JOptionPane.showMessageDialog(null, "There is no image open!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                // Determined the radius
                double theta = 90;

                // Create and apply the filter
                target.getImage().apply(new Rotate(-theta));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }



}

   