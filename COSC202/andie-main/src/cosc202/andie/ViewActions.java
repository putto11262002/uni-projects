package cosc202.andie;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the View menu.
 * </p>
 * 
 * <p>
 * The View menu contains actions that affect how the image is displayed in the application.
 * These actions do not affect the contents of the image itself, just the way it is displayed.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ViewActions {
    
    /**
     * A list of actions for the View menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of View menu actions.
     * </p>
     */
    public ViewActions() {
        actions = new ArrayList<Action>();
        actions.add(new ZoomInAction("Zoom In", null, "Zoom In", Integer.valueOf(KeyEvent.VK_EQUALS)+ Integer.valueOf(Event.SHIFT_MASK), KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, Event.SHIFT_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx())));
        actions.add(new ZoomOutAction("Zoom Out", null, "Zoom Out", Integer.valueOf(KeyEvent.VK_MINUS)));
        actions.add(new ZoomFullAction("Zoom Full", null, "Zoom Full", Integer.valueOf(KeyEvent.VK_1)));
        actions.add(new ResizeAction("Resize", null, "Resize the image",
                Integer.valueOf(KeyEvent.VK_R) + Integer.valueOf(KeyEvent.SHIFT_MASK), KeyStroke.getKeyStroke(
                        KeyEvent.VK_R, Event.SHIFT_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx())));
        actions.add(new CropAction("Crop image", null, "Crops down the image to a selected area", Integer.valueOf(KeyEvent.VK_0)));
    }

    /**
     * <p>
     * Create a menu containing the list of View actions.
     * </p>
     * 
     * @return The view menu UI element.
     */
    public JMenu createMenu() {
        JMenu viewMenu = new JMenu("View");

        for (Action action: actions) {
            viewMenu.add(new JMenuItem(action));
        }

        return viewMenu;
    }

    /**
     * <p>
     * Action to zoom in on an image.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomInAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-in action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomInAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Create a new zoom-in action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         * @param keyStroke A keyStroke for keyboard shortcut
         */
        ZoomInAction(String name, ImageIcon icon, String desc, Integer mnemonic, KeyStroke keyStroke) {
            super(name, icon, desc, mnemonic, keyStroke);
        }

        /**
         * <p>
         * Callback for when the zoom-in action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomInAction is triggered.
         * It increases the zoom level by 10%, to a maximum of 200%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                JOptionPane.showMessageDialog(null, "There is no image open!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                target.setZoom(target.getZoom()+10);
                target.repaint();
                target.getParent().revalidate();
            }
        }

    }

    /**
     * <p>
     * Action to zoom out of an image.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomOutAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-out action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomOutAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-iout action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomOutAction is triggered.
         * It decreases the zoom level by 10%, to a minimum of 50%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                JOptionPane.showMessageDialog(null, "There is no image open!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                target.repaint();
                target.getParent().revalidate();
                target.setZoom(target.getZoom()-10);
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Action to reset the zoom level to actual size.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomFullAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-full action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomFullAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-full action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomFullAction is triggered.
         * It resets the Zoom level to 100%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                JOptionPane.showMessageDialog(null, "There is no image open!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            //this is to fix it not working when fully zoomed out
            else if(target.getZoom() == 50.0){
                target.revalidate();
                target.getParent().revalidate();
                target.setZoom(target.getZoom() + 1.0);
                target.revalidate();
                target.getParent().revalidate();
        
            }
            //this is for every other situation
            else {
                target.setZoom(100);
                target.revalidate();
                target.getParent().revalidate();
        
            }
        
            /* this is probably the stupidest fix in my shortlived coding career: 
            fullZoom wont work if you're fully zoomed out, so I just add 1 to the current zoom and double it. 
            This method below checks for a zoom of 102 then applies the fullZoom effect. 
            Works perfectly but it's really annoying to think about */
            if(target.getZoom() == 51){
                target.setZoom(100);
                target.repaint();
                target.getParent().revalidate();   
            }
        }
    }

    /**
     * Action to rotate image tothe right by theta
     */
    // public class RotateActions extends ImageAction {
    // /**
    // *
    // * @param name
    // * @param icon
    // * @param desc
    // * @param mnemonic
    // */
    // RotateActions(String name, ImageIcon icon, String desc, Integer mnemonic){
    // super(name,icon, desc,mnemonic);
    // }

    // public void actionPerformed(ActionEvent e){
    // // Determine the radius - ask the user.
    // double theta = 90;

    // // Pop-up dialog box to ask for the radius value.
    // SpinnerNumberModel thetaModel = new SpinnerNumberModel(90.0, 0.0, 360.0,
    // 90.0);
    // JSpinner radiusSpinner = new JSpinner(thetaModel);
    // int option = JOptionPane.showOptionDialog(null, radiusSpinner, "Enter
    // rotation angle",
    // JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,
    // null);

    // // Check the return value from the dialog box.
    // if (option == JOptionPane.CANCEL_OPTION) {
    // return;
    // } else if (option == JOptionPane.OK_OPTION) {
    // theta = thetaModel.getNumber().intValue();
    // }

    // // Create and apply the filter
    // target.getImage().apply(new Rotate(theta));
    // target.repaint();
    // target.getParent().revalidate();

    // }
    // }
    public class ResizeAction extends ImageAction {

        /**
         * <p>
         * Create a new resize action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ResizeAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Create a new resize action.
         * </p>
         *
         * @param name      The name of the action (ignored if null).
         * @param icon      An icon to use to represent the action (ignored if null).
         * @param desc      A brief description of the action (ignored if null).
         * @param mnemonic  A mnemonic key to use as a shortcut (ignored if null).
         * @param keyStroke A keystroke for keyboard shortcut
         */
        ResizeAction(String name, ImageIcon icon, String desc, Integer mnemonic, KeyStroke keyStroke) {
            super(name, icon, desc, mnemonic, keyStroke);
        }

        /**
         * 
         * <p>
         * This method is called whenever the ResizeAction is triggered.
         * It prompts the user for a resize proportion, then uses this to apply the
         * {@link Resize}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                JOptionPane.showMessageDialog(null, "There is no image open!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Determine the proportion - ask the user.
                double proportion = 1;

                // Pop-up dialog box to ask for the resize proportion.
                SpinnerNumberModel resizeModel = new SpinnerNumberModel(1, 0.25, 10, 0.25);
                JSpinner resizeSpinner = new JSpinner(resizeModel);
                int option = JOptionPane.showOptionDialog(null, resizeSpinner, "Select value to multiply image size by",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    proportion = resizeModel.getNumber().doubleValue();
                }

                // Create and apply the filter
                target.getImage().apply(new Resize(proportion));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    public class CropAction extends ImageAction {
        public CropAction (String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed (ActionEvent e) {
            if (!target.getImage().hasImage()) {
                JOptionPane.showMessageDialog(null, "There is no image open!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (ImagePanel.mouse.getWidth()<1 || ImagePanel.mouse.getHeight()<1) {
                    return;
                }
                target.getImage().apply(new Crop(ImagePanel.mouse.getX(), ImagePanel.mouse.getY(), ImagePanel.mouse.getWidth(), ImagePanel.mouse.getHeight()));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

}
