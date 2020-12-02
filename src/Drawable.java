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
    void moveToInStart(Point2D.Float p); //以起始点为基准移动整个图形
    void moveToInStart(float x,float y); //以起始点为基准移动整个图形
    Point2D.Float getStartPoint();       //得到创建图形时拖动起始点
    void putEndPoint(Point2D.Float p);   //设置创建图形时拖动结束点
    void putEndPoint(float x,float y);   //设置创建图形时拖动结束点
    Point2D.Float getEndPoint();         //获得创建图形时拖动结束点

    Rectangle2D getOutBound(); //获得一个能够将图形包围的矩形
    Point2D.Float getTopLeft();       //得到矩形外边框左上角
    Point2D.Float getBottomRight();   //得到矩形外边框右下角




    boolean pointOn(Point2D.Float p);  //返回点是否在可绘制对象上的边框上
    boolean pointOn(float x,float y);  //返回点是否在可绘制对象上的边框上
    boolean pointOnFill(Point2D.Float p);  //返回点是否在可绘制对象的填充上
    boolean pointOnFill(float x,float y);  //返回点是否在可绘制对象的填充上
}