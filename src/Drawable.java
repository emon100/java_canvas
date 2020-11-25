import java.awt.*;

public interface Drawable{
    public static enum COLOR{
        BLACK,WHITE,RED,GREEN,BLUE
    }  
    public static enum TYPE{
        TRIANGLE,RECTANGLE,ELLIPSE,TEXTBOX
    }

    public void drawOnGraphics2D(Graphics2D g);
    public void setAlpha(float f);//from 0 to 1.0f
    public void setColor(COLOR c);
    public void setBorder(COLOR c,BasicStroke s);
    public void setFill(COLOR c);
    
    public void scale();
    public void MovestartTo(Point p);
    public Point getStart(); 
    public void putEndPoint(Point p);
    public Point getEndPoint();

    public boolean pointOn(Point p);

}