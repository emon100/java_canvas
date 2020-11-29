import java.awt.*;
import java.util.ArrayList;

public class States implements StatesModel {

    //当前选择颜色
    protected Color currentColor;

    public void setCurrentColor(Color cur) {
        this.currentColor = cur;
        System.out.println(cur);
    }

    @Override
    public void execute(Command command) {

    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

    @Override
    public ArrayList<Drawable> getAllDrawable() {
        return null;
    }

    @Override
    public TYPE getType() {
        return null;
    }

    @Override
    public Color getColor() {
        return currentColor;
    }

    @Override
    public float getAlpha() {
        return 0;
    }

    @Override
    public String getStringInput() {
        return null;
    }

    @Override
    public void setDrawable(ArrayList<Drawable> d) {

    }
}
