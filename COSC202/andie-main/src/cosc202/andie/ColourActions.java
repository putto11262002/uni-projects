package cosc202.andie;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel
 * directly
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations
 * will need to be added.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ColourActions {

    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<Action>();
        
        actions.add(new ConvertToGreyAction("Greyscale", null, "Convert to greyscale",
                Integer.valueOf(KeyEvent.VK_G) + Integer.valueOf(KeyEvent.SHIFT_MASK), KeyStroke.getKeyStroke(
                        KeyEvent.VK_G, Event.SHIFT_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx())));
        actions.add(
                new brightnessContrastAction("Brightness", null, "Modify brightness",
                        Integer.valueOf(KeyEvent.VK_B) + Integer.valueOf(KeyEvent.SHIFT_MASK),
                        KeyStroke.getKeyStroke(KeyEvent.VK_B,
                                Event.SHIFT_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx())));
        actions.add(
                new PosterisationAction("Posterisation", null, "Posterise the photo",
                        Integer.valueOf(KeyEvent.VK_B) + Integer.valueOf(KeyEvent.SHIFT_MASK),
                        KeyStroke.getKeyStroke(KeyEvent.VK_5,
                                Event.SHIFT_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx())));
        actions.add(new InversionAction("Inversion", null, "Invert colours",
                    Integer.valueOf(KeyEvent.VK_I) + Integer.valueOf(KeyEvent.SHIFT_MASK), KeyStroke.getKeyStroke(
                        KeyEvent.VK_I, Event.SHIFT_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx())));                        
    }

    /**
     * <p>
     * Create a menu contianing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Colour");

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         *
         * @param name      The name of the action (ignored if null).
         * @param icon      An icon to use to represent the action (ignored if null).
         * @param desc      A brief description of the action (ignored if null).
         * @param mnemonic  A mnemonic key to use as a shortcut (ignored if null).
         * @param keyStroke A keystroke for keyboard shortcut
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic, KeyStroke keyStroke) {
            super(name, icon, desc, mnemonic, keyStroke);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new ConvertToGrey());
            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class brightnessContrastAction extends ImageAction {
        // does nothing yet
        brightnessContrastAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         *
         * @param name      The name of the action (ignored if null).
         * @param icon      An icon to use to represent the action (ignored if null).
         * @param desc      A brief description of the action (ignored if null).
         * @param mnemonic  A mnemonic key to use as a shortcut (ignored if null).
         * @param keyStroke A keystroke for keyboard shortcut
         */
        brightnessContrastAction(String name, ImageIcon icon, String desc, Integer mnemonic, KeyStroke keyStroke) {
            super(name, icon, desc, mnemonic, keyStroke);
        }

        public void actionPerformed(ActionEvent e) {
            int brightness = 1;
            int contrast = 1;

            SpinnerNumberModel perc = new SpinnerNumberModel(1, -100, 100, 1);
            JSpinner percSpinner = new JSpinner(perc);
            int option = JOptionPane.showOptionDialog(null, percSpinner, "Enter Brightness Factor",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                brightness = perc.getNumber().intValue();
            }

            SpinnerNumberModel contr = new SpinnerNumberModel(1, -100, 100, 1);
            JSpinner contSpinner = new JSpinner(contr);
            int option2 = JOptionPane.showOptionDialog(null, contSpinner, "Enter Contrast Factor",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (option2 == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option2 == JOptionPane.OK_OPTION) {
                contrast = contr.getNumber().intValue();
            }
            // Create and apply the filter
            target.getImage().apply(new BrightnessFilter(brightness, contrast));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class PosterisationAction extends ImageAction {

        /**
         * <p>
         * Create a new Posterisation action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        PosterisationAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Create a new Posterisation action.
         * </p>
         *
         * @param name      The name of the action (ignored if null).
         * @param icon      An icon to use to represent the action (ignored if null).
         * @param desc      A brief description of the action (ignored if null).
         * @param mnemonic  A mnemonic key to use as a shortcut (ignored if null).
         * @param keyStroke A keystroke for keyboard shortcut
         */
        PosterisationAction(String name, ImageIcon icon, String desc, Integer mnemonic, KeyStroke keyStroke) {
            super(name, icon, desc, mnemonic, keyStroke);
        }

        /**
         * <p>
         * Callback for when the Posterisation action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the PosterisationAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new PosterisationFilter());
            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class InversionAction extends ImageAction {

        /**
         * <p>
         * Create a new Inversion action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        InversionAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Create a new Inversion action.
         * </p>
         *
         * @param name      The name of the action (ignored if null).
         * @param icon      An icon to use to represent the action (ignored if null).
         * @param desc      A brief description of the action (ignored if null).
         * @param mnemonic  A mnemonic key to use as a shortcut (ignored if null).
         * @param keyStroke A keystroke for keyboard shortcut
         */
        InversionAction(String name, ImageIcon icon, String desc, Integer mnemonic, KeyStroke keyStroke) {
            super(name, icon, desc, mnemonic, keyStroke);
        }

        /**
         * <p>
         * Callback for when the Posterisation action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the PosterisationAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new InversionFilter());
            target.repaint();
            target.getParent().revalidate();
        }

    }

}
