import java.awt.*;
import java.awt.geom.*;

public interface Drawable{
    public static enum TYPE{
        LINE,PATH,TRIANGLE,RECTANGLE,ELLIPSE,TEXTBOX
    }

    public void drawOnGraphics2D(Graphics2D g);//实现往g上作画
    public void setAlpha(float f);//设置透明度，from 0 to 1.0f
    public float getAlpha();
    public void setColor(Color c);//同时设置fill和border的颜色
    public void setBorder(Color c,BasicStroke s);
    public Color getBorderColor();
    public BasicStroke getBasicStroke();
    public void setFillColor(Color c);
    public Color getFillColor();

    public void scale(float times); //设置缩放倍数
    public void MovestartTo(Point2D.Float p); //设置起始点
    public Point2D.Float getStart();  //得到起始点
    public void putEndPoint(Point2D.Float p); //设置结束点
    public Point2D.Float getEndPoint(); //获得结束点

    public boolean pointOn(Point2D.Float p);  //返回一个点是否在可绘制对象上

}