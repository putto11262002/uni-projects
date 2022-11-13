package cosc202.andie;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class ToolBar<isRecording> extends JToolBar implements  ActionListener{
    private final String ICON_PATH = "src/icons/";
    private  ImageIcon redoIcon;
    private  ImageIcon undoIcon;
    private  ImageIcon rotateLeftIcon;
    private  ImageIcon rotateRightIcon;
    private  ImageIcon saveIcon;
    private  ImageIcon horizontalFlipIcon;
    private  ImageIcon verticalFlipIcon;
    private  ImageIcon zoomInIcon;
    private final ImageIcon zoomOutIcon;
    private final Action rotateLeftAction;
    private final Action rotateRightAction;
    private final Action horizontalFlipAction;
    private final Action verticalFlipAction;
    private Action saveAction;
    private Action redoAction;
    private  Action undoAction;
    private  Action zoomInAction;
    private  Action zoomOutAction;
    private static Action startRecordAction;
    private static Action stopRecordAction;
    private  JButton redoButton;
    private  JButton undoButton;
    private  JButton rotateRightButton;
    private  JButton rotateLeftButton;
    private  JButton horizontalFlipButton;
    private  JButton saveButton;
    private  JButton verticalFlipButton;
    private  JButton zoomInButton;
    private  JButton zoomOutButton;
    private static ImageIcon startRecordIcon;
    private ImageIcon stopRecordIcon;
    static JButton stopRecordingButton;
    static JButton startRecordingButton;
    static ImageIcon isRecordingIcon;
    static JButton isRecordingButton;
    private int SPACE = 15;
    private final int ICON_WIDTH = 15, ICON_HEIGHT = 15;
    static Timer recordingIconTimer;



    public ToolBar(){
        // Actions
        FileActions fileActions = new FileActions();
        RotationAction rotationAction = new RotationAction();
        EditActions editActions = new EditActions();
        ViewActions viewActions = new ViewActions();
        MacroActions macroActions = new MacroActions();

        // Icons
        redoIcon = resizeImageIcon(new ImageIcon(ICON_PATH + "redo-icon.png"));
        undoIcon = resizeImageIcon( new ImageIcon(ICON_PATH + "undo-icon.png"));
        rotateLeftIcon = resizeImageIcon(new ImageIcon(ICON_PATH + "rotate-left-icon.png"));
        rotateRightIcon = resizeImageIcon(new ImageIcon(ICON_PATH + "rotate-right-icon.png"));
        saveIcon = resizeImageIcon(new ImageIcon(ICON_PATH + "save-icon.png"));
        horizontalFlipIcon = resizeImageIcon(new ImageIcon(ICON_PATH + "horizontal-flip-icon.png"));
        verticalFlipIcon = resizeImageIcon(new ImageIcon(ICON_PATH + "vertical-flip-icon.png"));
        zoomInIcon = resizeImageIcon(new ImageIcon(ICON_PATH + "zoom-in-icon.png"));
        zoomOutIcon = resizeImageIcon(new ImageIcon(ICON_PATH + "zoom-out-icon.png"));
        startRecordIcon = resizeImageIcon(new ImageIcon(ICON_PATH + "start-record-icon.png"));
        stopRecordIcon = resizeImageIcon(new ImageIcon(ICON_PATH + "stop-record-icon.png"));
        isRecordingIcon = resizeImageIcon(new ImageIcon(ICON_PATH + "recording-icon.png"));


        // Extract individual actions
        rotateLeftAction = rotationAction.new RotateLeftActions("Rotate left", rotateLeftIcon ,null, null);
        rotateRightAction = rotationAction.new RotateRightActions("Rotate right", rotateRightIcon, null, null, null);
        horizontalFlipAction = rotationAction.new flipHorizontalAction("Horizontal flip", horizontalFlipIcon, null, null, null);
        verticalFlipAction = rotationAction.new flipVerticalAction("Vertical flip", verticalFlipIcon, null, null, null);
        saveAction = fileActions.new FileSaveAction("Save file", saveIcon, null, null);
        redoAction = editActions.new RedoAction("Redo", redoIcon, null, null);
        undoAction = editActions.new UndoAction("Undo", undoIcon, null, null);
        zoomInAction = viewActions.new ZoomInAction("Zoom in", zoomInIcon, null, null);
        zoomOutAction = viewActions.new ZoomOutAction("Zoom out", zoomOutIcon, null, null);
        startRecordAction = macroActions.new StartRecord("Start record", startRecordIcon, null, null);
        stopRecordAction = macroActions.new StopRecord("Stop record", stopRecordIcon, null, null);



        redoButton = new Button(redoAction);
        undoButton = new Button(undoAction);
        rotateRightButton = new Button(rotateRightAction);
        rotateLeftButton = new Button(rotateLeftAction);
        saveButton = new Button(saveAction);
        horizontalFlipButton = new Button(horizontalFlipAction);
        verticalFlipButton = new Button(verticalFlipAction);
        zoomInButton = new Button(zoomInAction);
        zoomOutButton = new Button(zoomOutAction);
        stopRecordingButton = new Button(stopRecordAction);
        startRecordingButton  = new Button(startRecordAction);
        isRecordingButton = new Button();





        this.setRollover(true);
        this.setFloatable(false);

        this.setMargin(new Insets(SPACE/4,SPACE/2, SPACE/4, SPACE/2));
        this.add(saveButton);
        this.add(Box.createHorizontalStrut(SPACE/2));
        this.addSeparator();
        this.add(Box.createHorizontalStrut(SPACE/2));

        this.add(undoButton);
        this.add(Box.createHorizontalStrut(SPACE));
        this.add(redoButton);
        this.add(Box.createHorizontalStrut(SPACE/2));
        this.addSeparator();
        this.add(Box.createHorizontalStrut(SPACE/2));
        this.add(zoomInButton);
        this.add(Box.createHorizontalStrut(SPACE));
        this.add(zoomOutButton);
        this.add(Box.createHorizontalStrut(SPACE/2));
        this.addSeparator();
        this.add(Box.createHorizontalStrut(SPACE/2));
        this.add(rotateLeftButton);
        this.add(Box.createHorizontalStrut(SPACE));
        this.add(rotateRightButton);
        this.add(Box.createHorizontalStrut(SPACE));
        this.add(verticalFlipButton);
        this.add(Box.createHorizontalStrut(SPACE));
        this.add(horizontalFlipButton);
        this.add(Box.createHorizontalStrut(SPACE/2));
        this.addSeparator();
        this.add(Box.createHorizontalStrut(SPACE/2));
        this.add(startRecordingButton);
        this.add(Box.createHorizontalStrut(SPACE));
        this.add(stopRecordingButton);
        this.add(Box.createHorizontalStrut(SPACE));
        this.add(isRecordingButton);
        recordingIconTimer = new Timer(200, this);
        recordingIconTimer.start();



    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == recordingIconTimer){
            if(EditableImage.macro.isRecording()){
                if(isRecordingButton.getIcon() == null){
                    isRecordingButton.setIcon(isRecordingIcon);
                }else{

                    isRecordingButton.setIcon(null);
                }

            }else{
                isRecordingButton.setIcon(null);
            }

            isRecordingButton.repaint();
        }



    }






    private class Button extends JButton{
        public Button(Action e){
            super(e);
            this.setBorder(BorderFactory.createEmptyBorder());
            this.setHideActionText(true);


        }

        public Button(){
            this.setBorder(BorderFactory.createEmptyBorder());
            this.setHideActionText(true);


        }
    }

    private class Separator extends JSeparator{
        public Separator(){
            this.setForeground(Color.lightGray);
            this.setBackground(Color.lightGray);
            this.setOrientation(VERTICAL);



        }
    }

    private ImageIcon resizeImageIcon(ImageIcon imageIcon){
        Image image =  imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(ICON_WIDTH, ICON_HEIGHT,  Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);

    }

    private class BlinkImageIcon extends ImageIcon implements ActionListener  {
        private Image primaryImage, secondaryImage;

        public BlinkImageIcon(String primaryImageImagePath, String secondaryImagePath) throws IOException {
            this.primaryImage = ImageIO.read(new File(primaryImageImagePath)).getScaledInstance(ICON_WIDTH, ICON_HEIGHT,  Image.SCALE_SMOOTH);
            this.secondaryImage = ImageIO.read(new File(secondaryImagePath)).getScaledInstance(ICON_WIDTH, ICON_HEIGHT,  Image.SCALE_SMOOTH);
            this.setImage(primaryImage);

            new Timer(500, this).start();
        }

        public void actionPerformed(ActionEvent ae){
            if(getImage().equals(primaryImage)){
                this.setImage(secondaryImage);
                repaint();
                return;
            }
            this.setImage(null);
            repaint();
            return;
        }

    }
}
