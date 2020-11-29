import java.util.*;
import java.awt.*;

public interface StatesModel {
    public static enum TYPE{
        SELECT,LINE,PATH,TRIANGLE,RECTANGLE,ELLIPSE,TEXTBOX
    }
    public void execute(Command command);
    public void undo();
    public void redo();

    public ArrayList<Drawable> getAllDrawable();
    public StatesModel.TYPE getType();
    public Color getColor();
    public float getAlpha();
    public String getStringInput();
    public void setDrawable(ArrayList<Drawable> d);
}
