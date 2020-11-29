import java.util.*;
import java.awt.*;

public interface StatesModel {
    public static enum TYPE{
        SELECT,LINE,PATH,TRIANGLE,RECTANGLE,ELLIPSE,TEXTBOX,ERASER,FILL
    }
    public void execute(Command command);
    public void undo();
    public void redo();

    public ArrayList<Drawable> getAllDrawable();
    public void setDrawable(ArrayList<Drawable> d);
    public StatesModel.TYPE getType();
    public void setType(StatesModel.TYPE t);
    public Color getColor();
    public void setColor(Color cur);
    public float getAlpha();
    public void setAlpha(float f);
}
