package cosc202.andie;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * <p>
 * The actions of the 'macro' function as they apply to ANDIE.
 * </p>
 * @author Put Suthisrisinlpa
 */
public class MacroActions {
    private final ArrayList<Action> actions = new ArrayList<>();
    private final FileFilter fileFilter = new FileFilter() {

        /**
         * <p>
         * Specifying that the macro will only accept .ops files
         * </p>
         * @param f the file that is loaded
         * @return the file with the suffix '.ops'
         */
        @Override
        public boolean accept(File f) {
            return f.getName().endsWith(".ops");
        }

        /**
         * <p>
         * Provides a description for the file.
         * </p>
         */
        @Override
        public String getDescription() {
            return ".ops";
        }
    };
    /**
     * <p>
     * The actions performed by the macro.
     * </p>
     */
    public MacroActions(){
        actions.add(new Replay("Replay Macro", null, "Replay Macro", Integer.valueOf(KeyEvent.VK_R)));
        actions.add(new StartRecord("Start Recording", null, "Start Recording", Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new StopRecord("Stop Recording", null, "Stop Recording", Integer.valueOf(KeyEvent.VK_P)));
    }

    /**
     * <p>
     * Creates a macro menu
     * </p>
     * @return macro menu for user
     */
    public JMenu createMenu(){
        JMenu macroMenu = new JMenu("Macro");

        for(Action action: actions){
            macroMenu.add(new JMenuItem(action));
        }
        return macroMenu;
    }

    /**
     * <p>
     * Starts the macro recording.
     * </p>
     */
    public class StartRecord extends ImageAction {
        public StartRecord(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);

        }

        public  void actionPerformed(ActionEvent e){
            if (!target.getImage().hasImage()) {
                JOptionPane.showMessageDialog(null, "There is no image open!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                EditableImage.macro.startRecording();


            }
        }

    }

    /**
     * <p>
     * Replays the macro once it is loaded from the .ops file.
     * </p>
     */
    public class Replay extends  ImageAction {

        public Replay(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e){
            if (!target.getImage().hasImage()) {
                JOptionPane.showMessageDialog(null, "There is no file open!", "Error", JOptionPane.ERROR_MESSAGE);
            }else {

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.addChoosableFileFilter(fileFilter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setDialogTitle("Select ops file");
                int res = fileChooser.showOpenDialog(target);
                if (res == JFileChooser.APPROVE_OPTION){
                    try {
                        EditableImage.macro.replay(fileChooser.getSelectedFile().getCanonicalPath());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Could not open this file!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }



            }


        }
    }

    /**
     * <p>
     * Stops the macro recording, allowing the user to save or discard the recording.
     * </p>
     */
    public class StopRecord extends  ImageAction{

        StopRecord(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e){
            if (!target.getImage().hasImage()) {
                JOptionPane.showMessageDialog(null, "There is no file open!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!EditableImage.macro.isRecording()){
                JOptionPane.showMessageDialog(null, "No recording in progress.");
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(fileFilter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setDialogTitle("Save Recording");
            int res = fileChooser.showSaveDialog(target);


            if(res == JFileChooser.APPROVE_OPTION){
                try {

                    EditableImage.macro.stopSaveRecording(fileChooser.getSelectedFile().getCanonicalPath());
                    return;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            EditableImage.macro.stopRecording();


        }
    }
}


