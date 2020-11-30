import java.awt.*;
import java.awt.geom.*;
import java.io.Serializable;

public interface Drawable extends Serializable {
    void drawOnGraphics2D(Graphics2D g);//实现往g上作画
    void setAlpha(float f);//设置整个图形透明度，from 0 to 1.0f
    void setColor(Color c);//同时设置fill和border的颜色
    void setBorder(Color c,MyStroke s); //设置边缘颜色
    Color getBorderColor(); //获得边缘颜色
    MyStroke getMyStroke();

    boolean isFilled();  //是否开启填充
    void setFill();        //开启默认填充
    void setFill(Color c); //开启填充，设置填充的颜色
    Color getFillColor();  //获得填充的颜色
    void disableFill();    //关闭填充

    void scale(float times); //设置缩放倍数
    void moveStartTo(Point2D.Float p); //设置起始点
    void moveStartTo(float x,float y); //设置起始点
    Point2D.Float getStart();  //得到起始点
    void putEndPoint(Point2D.Float p); //设置结束点
    void putEndPoint(float x,float y); //设置结束点
    Point2D.Float getEndPoint(); //获得结束点

    boolean pointOn(Point2D.Float p);  //返回点是否在可绘制对象上
    boolean pointOn(float x,float y);  //返回点是否在可绘制对象上
    boolean pointOnFill(Point2D.Float p);  //返回点是否在可绘制对象的填充上
    boolean pointOnFill(float x,float y);  //返回点是否在可绘制对象的填充上
}