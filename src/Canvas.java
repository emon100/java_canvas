import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Canvas extends JFrame {

    /**
     *测试用主窗口
     */
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        new Canvas();
    }

    public Canvas() {
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new PaintSurface(
            new StatesModel(){
                public void execute(Command command){}
                public void undo(){}
                public void redo(){}
            
                public ArrayList<Drawable> getAllDrawable(){
                    var a = new ArrayList<Drawable>();
                    a.add(BasicDrawableFactory.makeLine(1, 18,50,50));
                    return a;
                }
                public Drawable.TYPE getType(){
                    return Drawable.TYPE.LINE;
                }
                public Drawable.COLOR getColor(){
                    return Drawable.COLOR.BLACK;
                }
                public String getStringInput(){
                    return "TryString";
                }
                public void setDrawable(ArrayList<Drawable> d){}
            }
        ), BorderLayout.CENTER);
        this.setVisible(true);
    }
}