import java.awt.*;
import java.awt.geom.*;

public interface Drawable{
    public static enum TYPE{
        LINE,PATH,TRIANGLE,RECTANGLE,ELLIPSE,TEXTBOX
    }

    public void drawOnGraphics2D(Graphics2D g);//实现往g上作画
    public void setAlpha(float f);//设置整个图形透明度，from 0 to 1.0f
    public void setColor(Color c);//同时设置fill和border的颜色
    public void setBorder(Color c,BasicStroke s); //设置边缘颜色
    public Color getBorderColor(); //获得边缘颜色
    public BasicStroke getBasicStroke();

    public boolean ifFilled(); //是否开启填充
    public void setFill();        //开启默认填充
    public void setFill(Color c); //开启填充，设置填充的颜色
    public Color getFillColor();  //获得填充的颜色
    public void disableFill();    //关闭填充

    public void scale(float times); //设置缩放倍数
    public void moveStartTo(Point2D.Float p); //设置起始点
    public void moveStartTo(float x,float y); //设置起始点
    public Point2D.Float getStart();  //得到起始点
    public void putEndPoint(Point2D.Float p); //设置结束点
    public void putEndPoint(float x,float y); //设置结束点
    public Point2D.Float getEndPoint(); //获得结束点

    public boolean pointOn(Point2D.Float p);  //返回点是否在可绘制对象上
    public boolean pointOn(float x,float y);  //返回点是否在可绘制对象上
    public boolean pointOnFill(Point2D.Float p);  //返回点是否在可绘制对象的填充上
    public boolean pointOnFill(float x,float y);  //返回点是否在可绘制对象的填充上
}