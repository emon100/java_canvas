import java.util.*;

public interface StatesModel {
    public void execute(Command command);
    public void undo();
    public void redo();

    public ArrayList<Drawable> getAllDrawable();
    public Drawable.TYPE getType();
    public Drawable.COLOR getColor();
    public String getStringInput();
    public void setDrawable(ArrayList<Drawable> d);
}
