import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *这个类是椭圆类，用于所有举行的相关操作
 * @author 郑晨升
 *
 */
public class Ellipse implements Drawable {

    private static final long serialVersionUID = -4871999594383122024L;
    Point2D.Float startPoint;           //该椭圆的起始点
    Point2D.Float endPoint = null;      //该椭圆的终点
    Ellipse2D.Float ellipse;            //封装好的椭圆对象

    MyStroke borderStroke = new MyStroke();// 画笔轮廓
    float alpha = 1f;// 透明度
    Color color = Color.BLACK;// 画笔颜色，默认黑色
    Color fillColor = Color.BLUE;// 填充颜色，默认蓝色

    int widthTimes = 5; //边界扩大的倍数，用于对画笔宽度进行扩大
    float basicBorderStrokeWidth = this.borderStroke.getBasicStroke().getLineWidth();//basic的画笔宽度
    float borderStrokeWidth = this.widthTimes * this.borderStroke.getBasicStroke().getLineWidth();//设置的扩大好的画笔宽度，用于扩大矩形轮廓边界

    boolean iffFillEll = false;// 是否填充，默认未被填充

    /**
     * 该椭圆类的构造函数，包括设置起始点，构造分装好的椭圆类
     * @param point 构造函数传进去的点
     *
     */
    Ellipse(Point2D.Float point) {
        startPoint = point;
        ellipse = new Ellipse2D.Float(point.x, point.y, 0, 0);
    }

    /**
     * 将文椭圆对象绘制到Graphic2D对象上
     * @param g 绘制到的Graphic2D对象
     *
     */
    @Override
    public void drawOnGraphics2D(Graphics2D g) {
        g.setPaint(color);
        g.setStroke(borderStroke.getBasicStroke());
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
        g.draw(ellipse);
        if (isFilled()) {
            g.setColor(fillColor);
            g.fill(ellipse);
        }
    }

    /**
     * 设置透明度方法，用于将透明度值传到该椭圆类透明度属性上
     * @param f 传进去的透明度值
     *
     */
    @Override
    public void setAlpha(float f) {
        if (f <= 1 && f >= 0) {
            alpha = f;
        }
    }

    /**
     * 设置颜色方法，将颜色值传到该椭圆类颜色属性上
     * @param c 传进去的颜色值
     *
     */
    @Override
    public void setColor(Color c) {
        color = c;
    }

    /**
     * 设置画笔方法，用于将颜色和画笔值传到该椭圆类上
     * @param c 传进去的颜色值
     * @param s 传进去的画笔值
     *
     */
    @Override
    public void setBorder(Color c, MyStroke s) {
        color = c;
        borderStroke = s;
        basicBorderStrokeWidth = this.borderStroke.getBasicStroke().getLineWidth();
    }

    /**
     * 获得画笔颜色方法
     * @return  该椭圆形的画笔的颜色值
     *
     */
    @Override
    public Color getBorderColor() {
        return color;
    }

    /**
     * 获得画笔值方法
     * @return  该椭圆的画笔值
     *
     */
    @Override
    public MyStroke getMyStroke() {
        return borderStroke;
    }

    /**
     * 判断是否被填充方法
     * @return  是否被填充
     *
     */
    @Override
    public boolean isFilled() {
        return iffFillEll;
    }

    /**
     * 设置填充的方法
     * 将是否被填充的值设置为是
     * 将填充颜色设置为默认颜色蓝色
     *
     */
    @Override
    public void setFill() {
        iffFillEll = true;
    }

    /**
     * 设置填充的方法，传入填充颜色的值
     * @param c 传进去的用于设置填充颜色的颜色值
     *
     */
    @Override
    public void setFill(Color c) {
        iffFillEll = true;
        fillColor = c;
    }

    /**
     * 获得该椭圆类填充颜色的方法
     * @return 该矩形的填充颜色值
     *
     */
    @Override
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * 取消填充的方法
     * 将是否填充的值设置为否
     *
     */
    @Override
    public void disableFill() {
        iffFillEll = false;
    }

    /**
     * 缩放扩大该椭圆的方法，传进去倍数值
     * @param times 传进去的用于椭圆缩放扩大的倍数值
     *
     */
    @Override
    public void scale(float times) {
        ellipse.setFrame(
                ellipse.x, ellipse.y,
                ellipse.width * times,
                ellipse.height * times
        );
    }

    /**
     * 按起始点移动该椭圆的方法，传进去新的位置点 p
     * @param p 传进去的用于给定新的椭圆的矩形边框位置的位置点
     *
     */
    @Override
    public void moveToInStart(Point2D.Float p) {
        float deltaX = p.x - startPoint.x;  //起始点的横坐标值与传进去的横坐标的值的差
        float deltaY = p.y - startPoint.y;  //起始点的纵坐标值与传进去的纵坐标的值的差

        ellipse.setFrame(   //设置新的椭圆形的矩形框，因为平移故该矩形的长宽不变
                ellipse.x + deltaX, ellipse.y + deltaY,
                ellipse.width, ellipse.height
        );
    }

    /**
     * 按起始点移动该椭圆的方法，传进去新的位置点（坐标位置）
     * @param x 传进去的用于给定新的椭圆的矩形边框位置的位置点（横坐标位置）
     * @param y 传进去的用于给定新的椭圆的矩形边框位置的位置点（纵坐标位置）
     *
     */
    @Override
    public void moveToInStart(float x, float y) {
        float deltaX = x - startPoint.x;    //起始点的横坐标值与传进去的横坐标的值的差
        float deltaY = y - startPoint.y;    //起始点的纵坐标值与传进去的纵坐标的值的差

        ellipse.setFrame(       //设置新的椭圆形的矩形框，因为平移故该矩形的长宽不变
                ellipse.x + deltaX, ellipse.y + deltaY,
                ellipse.width, ellipse.height
        );
    }

    /**
     * 获得该椭圆的矩形边框初始点位置的方法
     * @return 该椭圆的矩形边框初始点属性的克隆点
     *
     */
    @Override
    public Point2D.Float getStartPoint() {
        return (Point2D.Float) startPoint.clone();
    }

    /**
     * 设置终点位置的方法，将传进去的p作为该椭圆的矩形边框的终点位置，并重新设置该椭圆
     * @param p 传进去的新终点位置
     *
     */
    @Override
    public void putEndPoint(Point2D.Float p) {
        endPoint = p;   //将传进去的新终点位置传给该椭圆类的矩形边框终点坐标属性

        if (p.getX() >= startPoint.x) {
            if (p.getY() >= startPoint.y) {     //第四象限
                ellipse.setFrame(
                        startPoint.x, startPoint.y,
                        p.getX() - startPoint.x,
                        p.getY() - startPoint.y
                );
            }
            else {//第一象限
                ellipse.setFrame(
                        startPoint.x, p.getY(),
                        p.getX() - startPoint.x,
                        startPoint.y - p.getY()
                );
            }
        }
        else {      //第三象限
            if (p.getY() >= startPoint.y) {
                ellipse.setFrame(
                        p.getX(), startPoint.y,
                        startPoint.x - p.getX(),
                        p.getY() - startPoint.y
                );
            }
            else {      //第二象限
                ellipse.setFrame(
                        p.getX(), p.getY(),
                        startPoint.x - p.getX(),
                        startPoint.y - p.getY()
                );
            }
        }

    }

    /**
     * 设置终点位置的方法，将传进去的p作为该椭圆的矩形边框的终点位置，并重新设置该椭圆
     * @param x 传进去的新终点位置的横坐标
     * @param y 传进去的新终点位置的纵坐标
     *
     */
    @Override
    public void putEndPoint(float x, float y) {
        endPoint = new Point2D.Float(x, y);//将传进去的新终点位置传给该椭圆类的矩形边框终点坐标属性

        if (x >= startPoint.x) {
            if (y >= startPoint.y) { //第四象限
                ellipse.setFrame(
                        startPoint.x, startPoint.y,
                        x - startPoint.x,
                        y - startPoint.y
                );
            }
            else { //第一象限
                ellipse.setFrame(
                        startPoint.x, y,
                        x - startPoint.x,
                        startPoint.y - y
                );
            }
        }
        else {      //第三象限
            if (y >= startPoint.y) {
                ellipse.setFrame(
                        x, startPoint.y,
                        startPoint.x - x,
                        y - startPoint.y
                );
            }
            else {  //第二象限
                ellipse.setFrame(
                        x, y,
                        startPoint.x - x,
                        startPoint.y - y
                );
            }
        }

    }

    /**
     * 获得终点坐标的方法
     * @return 获得该椭圆类的矩形边框终点坐标属性的克隆
     *
     */
    @Override
    public Point2D.Float getEndPoint() {
        return (Point2D.Float) endPoint.clone();
    }

    /**
     * 获得该椭圆的矩形边框的方法
     * @return 获得的该椭圆的矩形边框
     *
     */
    @Override
    public Rectangle2D getOutBound() {
        return ellipse.getBounds2D();
    }

    /**
     * 获得该椭圆的矩形边框左上角点的方法
     * @return 该椭圆的矩形边框的左上角点
     *
     */
    @Override
    public Point2D.Float getTopLeft() {
        return new Point2D.Float(ellipse.getBounds().x, ellipse.getBounds().y);

    }

    /**
     * 获得该椭圆的矩形边框右下角点的方法
     * @return 该椭圆的矩形边框的右下角点
     *
     */
    @Override
    public Point2D.Float getBottomRight() {
        return new Point2D.Float(
                ellipse.getBounds().x + ellipse.getBounds().width,
                ellipse.getBounds().y + ellipse.getBounds().height
        );
    }

    /**
     * 判断传进去的点是否在该椭圆的边框上的方法，为了方便获得边框将边框的宽度扩大了widthTimes倍
     * @param p 传进去的需要判断是否在椭圆上的点
     * @return 是否在该椭圆边框上
     *
     */
    @Override
    public boolean pointOn(Point2D.Float p) {
        Ellipse2D.Float e1 = getSmallerEll();   //调用了getSmallerRec方法用于设置一个较小的椭圆边框
        Ellipse2D.Float e2 = getBiggerEll();    //调用了getBiggerRec方法用于设置一个较大椭圆形边框

        return (e2.contains(p) && (!e1.contains(p)));   //判断该点是否在较小和较大的椭圆边框区间内
    }

    /**
     * 判断传进去的点是否在该椭圆的边框上的方法，为了方便获得边框将边框的宽度扩大了widthTimes倍
     * @param x 传进去的需要判断是否在椭圆上的点的横坐标
     * @param y 传进去的需要判断是否在椭圆上的点的纵坐标
     * @return 是否在该椭圆边框上
     *
     */
    @Override
    public boolean pointOn(float x, float y) {
        Ellipse2D.Float e1 = getSmallerEll();   //调用了getSmallerRec方法用于设置一个较小的椭圆边框
        Ellipse2D.Float e2 = getBiggerEll();    //调用了getBiggerRec方法用于设置一个较大椭圆形边框

        return (e2.contains(x, y) && (!e1.contains(x, y)));   //判断该点是否在较小和较大的椭圆边框区间内
    }

    /**
     * 判断传进去的点是否在该椭圆内部的方法
     * @param p 传进去的点用于是否在该椭圆内部区域内
     * @return 该点是否在该椭圆内部
     *
     */
    @Override
    public boolean pointOnFill(Point2D.Float p) {
        Ellipse2D.Float e1 = getSmallerEll();
        return (e1.contains(p));
    }

    /**
     * 判断传进去的点是否在该椭圆内部的方法
     * @param x 传进去的点的横坐标，用于是否在该椭圆区域内
     * @param y 传进去的点的横坐标，用于是否在该椭圆区域内
     * @return 该点是否在该椭圆内部
     *
     */
    @Override
    public boolean pointOnFill(float x, float y) {
        Ellipse2D.Float e1 = getSmallerEll();
        return (e1.contains(x, y));
    }

    /**
     * 获得一个在该椭圆内部较小的矩形的方法，缩小了widthTimes
     * @return 较小的椭圆
     *
     */
    private Ellipse2D.Float getSmallerEll() {
        return new Ellipse2D.Float(
                ellipse.x + basicBorderStrokeWidth,
                ellipse.y + basicBorderStrokeWidth,
                ellipse.width - 2 * basicBorderStrokeWidth,
                ellipse.height - 2 * basicBorderStrokeWidth
        );
    }

    /**
     * 获得一个在该矩形内部较大的椭圆的方法，扩大了widthTimes
     * @return 较大的椭圆
     *
     */
    private Ellipse2D.Float getBiggerEll() {
        return new Ellipse2D.Float(
                ellipse.x - borderStrokeWidth,
                ellipse.y - borderStrokeWidth,
                ellipse.width + 2 * borderStrokeWidth,
                ellipse.height + 2 * borderStrokeWidth
        );
    }
}
