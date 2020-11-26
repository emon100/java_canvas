import java.util.*;
import java.awt.*;

public interface StatesModel {
    public void execute(Command command);
    public void undo();
    public void redo();

    public ArrayList<Drawable> getAllDrawable();
    public Drawable.TYPE getType();
    public Color getColor();
    public float getAlpha();
    public String getStringInput();
    public void setDrawable(ArrayList<Drawable> d);
}
