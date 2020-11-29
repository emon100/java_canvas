import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class States implements StatesModel {

    //当前选择颜色
    protected Color currentColor;

    protected float alpha = 1.0f;

    //当前选择类型
    protected TYPE cursorType = TYPE.RECTANGLE;

    //设置当前颜色
    public void setCurrentColor(Color cur) {
        this.currentColor = cur;
        System.out.println(cur);
    }

    ArrayList<Drawable> a = new ArrayList<>();

    {
        var r  = new Random();
        a.add(BasicDrawableFactory.makeLine(r.nextInt(200),r.nextInt(200),r.nextInt(200),r.nextInt(200)));
    }

    //设置当前光标类型
    public void setCursorType(TYPE cursorType) {
        this.cursorType = cursorType;
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
        return a;
    }
    
    @Override
    public void setAlpha(float f) {
        f = alpha;
    }

    @Override
    public float getAlpha() {
        return alpha;
    }
    
    @Override
    public TYPE getType() {
        return cursorType;
    }
    @Override
    public void setType(TYPE t) {
        cursorType=t;
    }
    
    @Override
    public Color getColor() {
        return currentColor;
    }
    
    @Override
    public void setDrawable(ArrayList<Drawable> d) {
        a=d;
    }

    @Override
    public void setColor(Color cur) {
        currentColor = cur;

    }
}
