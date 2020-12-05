import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *这个类是举行类，用于所有举行的相关操作
 * @author 郑晨升
 *
 */

public class Rectangle implements Drawable {
    
    Point2D.Float startPoint;           //该矩形的起始点
    Point2D.Float endPoint = null;      //该举行的终点
    Rectangle2D.Float rectangle;        //封装好的矩形类
 
    MyStroke borderStroke = new MyStroke(); // 画笔轮廓
    float alpha = 1f; // 透明度
    Color color = Color.BLACK; // 画笔颜色，默认黑色
    Color fillColor = Color.BLUE; // 填充颜色，默认蓝色
    boolean ifFillRec = false; // 是否填充，默认未被填充

    int widthTimes = 5;                 //边界扩大的倍数，用于对画笔宽度进行扩大
    float basicBorderStrokeWidth = this.borderStroke.getBasicStroke().getLineWidth();    //basic的画笔宽度
    float borderStrokeWidth = this.widthTimes * this.borderStroke.getBasicStroke().getLineWidth();   //设置的扩大好的画笔宽度，用于扩大矩形轮廓边界

    /**
     * 该矩形类的构造函数，包括设置起始点，构造分装好的矩形类
     * @param point 构造函数传进去的点
     *
     */
    Rectangle(Point2D.Float point) {
        startPoint = point;     //将该矩形类的起始点设置为传进去的点

        rectangle = new Rectangle2D.Float(point.x, point.y, 0, 0);
    }

    /**
     * 将文矩形对象绘制到Graphic2D对象上
     * @param g 绘制到的Graphic2D对象
     *
     */
    @Override
    public void drawOnGraphics2D(Graphics2D g) {
        g.setPaint(color);              //设置颜色
        g.setStroke(borderStroke.getBasicStroke());      //设置画笔
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); //设置透明度
        g.draw(rectangle);              //绘制该矩形
        if (isFilled()) {               //判断是否填充
            g.setColor(fillColor);      //设置填充颜色
            g.fill(rectangle);          //填充该矩形
        }
    }

    /**
     * 设置透明度方法，用于将透明度值传到该矩形类透明度属性上
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
     * 设置颜色方法，将颜色值传到该矩形类颜色属性上
     * @param c 传进去的颜色值
     *
     */
    @Override
    public void setColor(Color c) {
        color = c;
    }

    /**
     * 设置画笔方法，用于将颜色和画笔值传到该矩形类撒上
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
     * @return  该矩形的画笔的颜色值
     *
     */
    @Override
    public Color getBorderColor() {
        return color;
    }

    /**
     * 获得画笔值方法
     * @return  该矩形的画笔值
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
        return ifFillRec;
    }

    /**
     * 设置填充的方法
     * 将是否被填充的值设置为是
     * 将填充颜色设置为默认颜色蓝色
     *
     */
    @Override
    public void setFill() {
        ifFillRec = true;
        fillColor = Color.BLUE;
    }

    /**
     * 设置填充的方法，传入填充颜色的值
     * @param c 传进去的用于设置填充颜色的颜色值
     *
     */
    @Override
    public void setFill(Color c) {
        ifFillRec = true;
        fillColor = c;
    }

    /**
     * 获得该矩形类填充颜色的方法
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
        ifFillRec = false;
    }

    /**
     * 缩放扩大该矩形的方法，传进去倍数值
     * @param times 传进去的用于矩形缩放扩大的倍数值
     *
     */
    @Override
    public void scale(float times) {
        rectangle.setRect(
                rectangle.x, rectangle.y,
                rectangle.width * times,
                rectangle.height * times
        );
    }

    /**
     * 按起始点移动该矩形的方法，传进去新的位置点 p
     * @param p 传进去的用于给定新的矩形位置的位置点
     *
     */
    @Override
    public void moveToInStart(Point2D.Float p) {
        float deltaX = p.x - startPoint.x;  //起始点的横坐标值与传进去的横坐标的值的差
        float deltaY = p.y - startPoint.y;  //起始点的纵坐标值与传进去的纵坐标的值的差

        rectangle.setRect(                  //设置新的矩形，因为平移故该矩形的长宽不变
                rectangle.x + deltaX, rectangle.y + deltaY,
                rectangle.width, rectangle.height
        );
    }

    /**
     * 按起始点移动该矩形的方法，传进去新的位置点（坐标位置）
     * @param x 传进去的用于给定新的矩形位置的位置点（横坐标位置）
     * @param y 传进去的用于给定新的矩形位置的位置点（纵坐标位置）
     *
     */
    @Override
    public void moveToInStart(float x, float y) {
        float deltaX = x - startPoint.x;    //起始点的横坐标值与传进去的横坐标的值的差
        float deltaY = y - startPoint.y;    //起始点的纵坐标值与传进去的纵坐标的值的差

        rectangle.setRect(                  //设置新的矩形，因为平移故该矩形的长宽不变
                rectangle.x + deltaX,rectangle.y + deltaY,
                rectangle.width,rectangle.height
        );

    }


    /**
     * 获得该矩形初始点位置的方法
     * @return 该矩形初始点属性的克隆点
     *
     */
    @Override
    public Point2D.Float getStartPoint() {
        return (Point2D.Float) startPoint.clone();
    }

    /**
     * 设置终点位置的方法，将传进去的p作为该矩形的终点位置，并重新设置该矩形
     * @param p 传进去的新终点位置
     *
     */
    @Override
    public void putEndPoint(Point2D.Float p) {
        endPoint = p;       //将传进去的新终点位置传给该矩形类的终点坐标属性
        /**
         * 需要判断四种情况，因为Rectangle2D类的设置只能是左上角的坐标到右下角的坐标，
         * 新终点位置相对于起始点的位置，并重新设置rectangle对象
         *
         */
        if (p.getX() >= startPoint.x) {
            if (p.getY() >= startPoint.y) { //第四象限
                rectangle.setRect(
                        startPoint.x, startPoint.y,
                        p.getX() - startPoint.x,
                        p.getY() - rectangle.y
                );
            }else { //第一象限
                rectangle.setRect(
                        startPoint.x, p.getY(),
                        p.getX() - startPoint.x,
                        startPoint.y - p.getY()
                );
            }
        }
        else {      //第三象限
            if (p.getY() >= startPoint.y) {
                rectangle.setRect(
                        p.getX(), startPoint.y,
                        startPoint.x - p.getX(),
                        p.getY() - startPoint.y
                );
            }
            else {      //第二象限
                rectangle.setRect(
                        p.getX(), p.getY(),
                        startPoint.x - p.getX(),
                        startPoint.y - p.getY()
                );
            }
        }
    }

    /**
     * 设置终点位置的方法，将传进去的坐标作为该矩形的终点位置，并重新设置该矩形
     * @param x 传进去的新终点位置的横坐标
     * @param y 传进去的新终点位置的纵坐标
     *
     */
    @Override
    public void putEndPoint(float x, float y) {
        endPoint = new Point2D.Float(x, y); //将传进去的新终点位置传给该矩形类的终点坐标属性

        /**
         * 需要判断四种情况，因为Rectangle2D类的设置只能是左上角的坐标到右下角的坐标，
         * 新终点位置相对于起始点的位置，并重新设置rectangle对象
         *
         */
        if (x >= startPoint.x) {
            if (y >= startPoint.y) { //第四象限
                rectangle.setRect(
                        startPoint.x, startPoint.y,
                        x - startPoint.x,
                        y - rectangle.y
                );
            }else { //第一象限
                rectangle.setRect(
                        startPoint.x, y,
                        x - startPoint.x,
                        startPoint.y - y
                );
            }
        }
        else {      //第三象限
            if (y >= startPoint.y) {
                rectangle.setRect(
                        x, startPoint.y,
                        startPoint.x - x,
                        y - startPoint.y
                );
            }
            else {  //第二象限
                rectangle.setRect(
                        x, y,
                        startPoint.x - x,
                        startPoint.y - y
                );
            }
        }

    }

    /**
     * 获得终点坐标的方法
     * @return 获得该矩形类终点坐标属性的克隆
     *
     */
    @Override
    public Point2D.Float getEndPoint() {
        return (Point2D.Float) endPoint.clone();
    }

    /**
     * 判断传进去的点是否在该矩形边框上的方法，为了方便获得边框将边框的宽度扩大了widthTimes倍
     * @param p 传进去的需要判断是否在矩形上的点
     * @return 是否在该矩形边框上
     *
     */
    @Override
    public boolean pointOn(Point2D.Float p) {
        Rectangle2D.Float r1 = getSmallerRec(); //调用了getSmallerRec方法用于设置一个较小的矩形边框
        Rectangle2D.Float r2 = getBiggerRec();  //调用了getBiggerRec方法用于设置一个较大的矩形边框

        return (r2.contains(p) && (!r1.contains(p)));   //判断该点是否在较小和较大的矩形边框区间内
    }

    /**
     * 判断传进去的点是否在该矩形边框上的方法，为了方便获得边框将边框的宽度扩大了widthTimes倍
     * @param x 传进去的需要判断是否在矩形上的点的横坐标
     * @param y 传进去的需要判断是否在矩形上的点的纵坐标
     * @return 是否在该矩形边框上
     *
     */
    @Override
    public boolean pointOn(float x, float y) {
        Rectangle2D.Float r1 = getSmallerRec(); //调用了getSmallerRec方法用于设置一个较小的矩形边框
        Rectangle2D.Float r2 = getBiggerRec();  //调用了getBiggerRec方法用于设置一个较大的矩形边框

        return (r2.contains(x, y) && (!r1.contains(x, y))); //判断该点是否在较小和较大的矩形边框区间内
    }

    /**
     * 判断传进去的点是否在该矩形内部的方法
     * @param p 传进去的点用于是否在该矩形区域内
     * @return 该点是否在该矩形内部
     *
     */
    @Override
    public boolean pointOnFill(Point2D.Float p) {
        Rectangle2D.Float r1 = getSmallerRec(); //调用了getSmallerRec方法用于设置一个较小的矩形边框
        return (r1.contains(p));                //判断是否在该较小的矩形边框内

    }

    /**
     * 判断传进去的点是否在该矩形内部的方法
     * @param x 传进去的点的横坐标，用于是否在该矩形区域内
     * @param y 传进去的点的横坐标，用于是否在该矩形区域内
     * @return 该点是否在该矩形内部
     *
     */
    @Override
    public boolean pointOnFill(float x, float y) {
        Rectangle2D.Float r1 = getSmallerRec(); //调用了getSmallerRec方法用于设置一个较小的矩形边框
        return (r1.contains(x, y));             //判断是否在该较小的矩形边框内
    }

    /**
     * 获得一个在该矩形内部较小的矩形的方法，缩小了widthTimes
     * @return 较小的矩形
     *
     */
    private Rectangle2D.Float getSmallerRec() {
        return new Rectangle2D.Float(       //需要将左上角点向右下移动basicBorderStrokeWidth，并减小相应的宽和高
                rectangle.x + basicBorderStrokeWidth,
                rectangle.y + basicBorderStrokeWidth,
                rectangle.width - 2 * basicBorderStrokeWidth,
                rectangle.height - 2 * basicBorderStrokeWidth
        );
    }

    /**
     * 获得一个在该矩形内部较大的矩形的方法，扩大了widthTimes
     * @return 较大的矩形
     *
     */
    private Rectangle2D.Float getBiggerRec() {
        return new Rectangle2D.Float(       //需要将左上角点向左上移动basicBorderStrokeWidth，并扩大相应的宽和高
                rectangle.x - borderStrokeWidth,
                rectangle.y - borderStrokeWidth,
                rectangle.width + 2 * borderStrokeWidth,
                rectangle.height + 2 * borderStrokeWidth
        );
    }


    /**
     * 获得该矩形边框的方法
     * @return 获得的该矩形边框
     *
     */
    @Override
    public Rectangle2D getOutBound() {
        return rectangle.getBounds2D();     //调用了已经封装好的getBound2D方法
    }

    /**
     * 获得该矩形左上角点的方法
     * @return 该矩形的左上角点
     *
     */
    @Override
    public Point2D.Float getTopLeft() {
        return new Point2D.Float(rectangle.getBounds().x, rectangle.getBounds().y);
    }

    /**
     * 获得该矩形右下角的方法
     * @return 该矩形的右下角点
     */
    @Override
    public Point2D.Float getBottomRight() {
        return new Point2D.Float(
                rectangle.getBounds().x + rectangle.getBounds().width,
                rectangle.getBounds().y + rectangle.getBounds().height
        );
    }
}