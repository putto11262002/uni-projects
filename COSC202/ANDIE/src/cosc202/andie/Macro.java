package cosc202.andie;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;

/**
 * <p>
 * Recording actions taken on an image as a series of steps.
 * </p>
 * This function allows users to record, save, and load a series of actions taken on an image for use on other images.
 * @author Put Suthisrisinlpa
 * 
 */
public class Macro {
     private int startRecordIdx;
     private boolean isRecording = false;

    private EditableImage editableImage;

    /**
     * <p>
     * Create a macro instance
     * </p>
     * This default constructor will not record or save actions.
     */
    public Macro(){}

    /**
     * <p>
     * Create a macro instance.
     * </p>
     * @param editableImage the image that the editing actions are taking place on initially.
     */
    public Macro(EditableImage editableImage){
        this.editableImage = editableImage;
    }

    /**
     * <p>
     * Set the image being edited.
     * </p>
     * @param editableImage The image being edited in the macro.
     */
    public void setEditableImage(EditableImage editableImage){
        this.editableImage = editableImage;
    }

    /**
     * <p>
     * Begins the macro recording process. Every action taken between this point and ending the recording will be saved.
     * </p>
     */
    public void startRecording(){
        this.startRecordIdx = editableImage.getOps().size() == 0 ? 0 : editableImage.getOps().size() - 1;
        this.isRecording = true;
    }

    /**
     * <p>
     * Stops the macro recording and triggers the saving process.
     * </p>
     * @param opsFilename the name that the .ops file is saved under.
     */
    public void stopSaveRecording(String opsFilename){
        Stack<ImageOperation> ops = new Stack<>();
        for(int i = startRecordIdx; i < editableImage.getOps().size(); i++){
            ops.add(editableImage.getOps().get(i));
        }
        try{
            save(ops, opsFilename);
            this.isRecording = false;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * <p>
     * Stops the macro recording without saving.
     * </p>
     */
    public void stopRecording(){
            this.isRecording = false;
    }

    /**
     * <p>
     * Lets the recorder know if the macro is recording or not.
     * </p>
     * @return the recording status.
     */
    public boolean isRecording(){
        return this.isRecording;
    }

    /**
     * <p>
     * Replays the .ops file actions. Allows the user to repeat the actions on a new image.
     * </p>
     * @param opsFilename The name of the macro's .ops file
     */
    public void replay(String opsFilename){
        try{
            Stack<ImageOperation> ops =  open(opsFilename);
            for(ImageOperation op: ops){
                editableImage.apply(op);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * <p>
     * Saves the macro to a .ops file.
     * </p>
     * @param ops The file format, allowing operations to be saved.
     * @param opsFilename The name of the .ops file that contains the macro actions.
     * @throws Exception
     */
    private void save(Stack<ImageOperation> ops,String opsFilename) throws Exception{
        FileOutputStream fileOut = new FileOutputStream(opsFilename + ".ops");
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        objOut.writeObject(ops);
        objOut.close();
        fileOut.close();
    }

    /**
     * <p>
     * Allows the user to open a .ops file and load a previously saved macro.
     * </p>
     * @param opsFilename The name of the macro file.
     * @return The macro.
     * @throws Exception
     */
    private Stack<ImageOperation> open(String opsFilename) throws Exception {
        FileInputStream fileIn = new FileInputStream(opsFilename);
        ObjectInputStream objIn = new ObjectInputStream(fileIn);

        Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
        objIn.close();
        fileIn.close();
        return opsFromFile;
    }
}

