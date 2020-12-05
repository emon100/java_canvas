import java.util.*;
import java.awt.*;

public interface StatesModel {
    enum TYPE{
        SELECT,LINE,PATH,TRIANGLE,RECTANGLE,ELLIPSE,TEXTBOX,ERASER,FILL
    }
    void execute(Command command);
    void undo();
    void redo();

    ArrayList<Drawable> getAllDrawable();
    void setDrawable(ArrayList<Drawable> d);
    StatesModel.TYPE getType();
    void setType(StatesModel.TYPE t);
    Color getColor();
    void setColor(Color cur);
    float getAlpha();
    void setAlpha(float f);
    MyStroke getMyStroke();
    void setMyStroke(MyStroke my);
}
