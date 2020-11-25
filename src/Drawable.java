import java.awt.*;

public interface Drawable{
    public static enum COLOR{
        BLACK,WHITE,RED,GREEN,BLUE
    }  
    public static enum TYPE{
        TRIANGLE,RECTANGLE,ELLIPSE,TEXTBOX
    }

    public void drawOnGraphics2D(Graphics2D g);//实现往g上作画
    public void setAlpha(float f);//设置透明度，from 0 to 1.0f
    public void getAlpha();
    public void setColor(COLOR c);//同时设置fill和border的颜色
    public void setBorder(COLOR c,BasicStroke s);
    public COLOR getBorderColor();
    public BasicStroke getBasicStroke();
    public void setFillColor(COLOR c);
    public COLOR getFillColor();

    public void scale(float times); //设置缩放倍数
    public void MovestartTo(Point p); //设置起始点
    public Point getStart();  //得到起始点
    public void putEndPoint(Point p); //设置结束点
    public Point getEndPoint(); //获得结束点

    public boolean pointOn(Point p);  //返回一个点是否在可绘制对象上

}