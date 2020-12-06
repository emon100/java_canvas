import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Float;

/**
 *这个类是直线段类，用于所有直线段的相关操作
 * @author 郑晨升
 *
 */
public class Line implements Drawable {
    Point2D.Float startPoint;           //该直线段的起始点
    Point2D.Float endPoint = null;      //该直线段的终点

    Line2D.Float line;                  //封装好的直线段对象
    MyStroke borderStroke = new MyStroke(); // 画笔轮廓
    float alpha = 1f; // 透明度
    Color color = Color.BLACK; // 画笔颜色，默认黑色

    int widthTimes = 5;//边界扩大的倍数，用于对画笔宽度进行扩大
    float basicBorderStrokeWidth = this.borderStroke.getBasicStroke().getLineWidth();    //basic的画笔宽度
    float borderStrokeWidth = widthTimes * basicBorderStrokeWidth;   //设置的扩大好的画笔宽度，用于扩大矩形轮廓边界

    /**
     * 该直线段类的构造函数，包括设置起始点，构造分装好的直线段类
     * @param point 构造函数传进去的点
     *
     */
    public Line(Point2D.Float point) {
        startPoint = point;     //将该直线段类的起始点设置为传进去的点
        line = new Line2D.Float(point.x, point.y, point.x, point.y);
    }

    /**
     * 将该直线段的对象绘制到Graphic2D对象上
     * @param g 绘制到的Graphic2D对象
     *
     */
    @Override
    public void drawOnGraphics2D(Graphics2D g) {
        // initial g settings
        g.setPaint(color); // 设置画笔颜色
        g.setStroke(borderStroke.getBasicStroke()); // 设置画笔
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); // 设置透明度
        //
        g.draw(line);              //绘制该直线段

    }

    /**
     * 设置透明度方法，用于将透明度值传到该直线段类透明度属性上
     * @param f 传进去的透明度值
     *
     */
    @Override
    public void setAlpha(float f) {
        if (f <= 1 && f >= 0) { // validate alpha value
            alpha = f;
        }
    }

    /**
     * 设置颜色方法，将颜色值传到该直线段类颜色属性上
     * @param c 传进去的颜色值
     *
     */
    @Override
    public void setColor(Color c) {
        color = c;
    }

    /**
     * 设置画笔方法，用于将颜色和画笔值传到该直线段类上
     * @param c 传进去的颜色值
     * @param s 传进去的画笔值
     *
     */
    @Override
    public void setBorder(Color c, MyStroke s) {
        color = c;
        borderStroke = s;
    }

    /**
     * 获得画笔颜色方法
     * @return  该直线段的画笔的颜色值
     *
     */
    @Override
    public Color getBorderColor() {
        return color;
    }

    /**
     * 获得画笔值方法
     * @return  该直线段的画笔值
     *
     */
    @Override
    public MyStroke getMyStroke() {
        return borderStroke;
    }

    /**
     * 获得该直线段类填充颜色的方法
     * @return 该直线段的填充颜色值
     *
     */
    @Override
    public Color getFillColor() {
        return color;
    }

    /**
     * 缩放扩大该直线段的方法，传进去倍数值
     * @param times 传进去的用于直线段缩放扩大的倍数值
     *
     */
    @Override
    public void scale(float times) {

        line.setLine(line.x1, line.y1,
                line.x2 * times - line.x1,
                line.y2 * times - line.y1);
    }

    /**
     * 获得该直线段初始点位置的方法
     * @return 该直线段初始点属性的克隆点
     *
     */
    @Override
    public Point2D.Float getStartPoint() {
        return (Point2D.Float) startPoint.clone();
    }

    /**
     * 设置终点位置的方法，将传进去的p作为该直线段的终点位置，并重新设置该直线段
     * @param p 传进去的新终点位置
     *
     */
    @Override
    public void putEndPoint(Point2D.Float p) {
        endPoint = p;       //将传进去的新终点位置传给该直线段类的终点坐标属性
        line.setLine(startPoint.x, startPoint.y, p.x, p.y);
    }

    /**
     * 获得终点坐标的方法
     * @return 获得该直线段类终点坐标属性的克隆
     *
     */
    @Override
    public Point2D.Float getEndPoint() {
        return (Point2D.Float) endPoint.clone();
    }

    /**
     * 获得该直线段的矩形边框的方法
     * @return 获得的该直线段的矩形边框
     *
     */
    @Override
    public Rectangle2D getOutBound() {
        return line.getBounds2D();
    }

    /**
     * 获得该直线段的矩形边框左上角点的方法
     * @return 该直线段的矩形边框的左上角点
     *
     */
    @Override
    public Float getTopLeft() {
        return new Point2D.Float(line.getBounds().x, line.getBounds().y);
    }

    /**
     * 获得该直线段的矩形边框右下角点的方法
     * @return 该直线段的矩形边框的右下角点
     *
     */
    @Override
    public Float getBottomRight() {
        return new Point2D.Float(
                line.getBounds().x + line.getBounds().width,
                line.getBounds().y + line.getBounds().height
        );
    }

    /**
     * 判断传进去的点是否在该直线段上的方法，为了方便获得直线段将直线段的宽度扩大了widthTimes倍
     * @param p 传进去的需要判断是否在直线段上的点
     * @return 是否在该直线段上
     *
     */
    @Override
    public boolean pointOn(Point2D.Float p) {
        return (line.ptSegDist(p) <= borderStrokeWidth);    //通过判断该点与该直线段的距离判断是否在该直线段上
    }

    /**
     * 按起始点移动该直线段的方法，传进去新的位置点 p
     * @param p 传进去的用于给定新的直线段位置的位置点
     *
     */
    @Override
    public void moveToInStart(Point2D.Float p) {
        float deltaX = p.x - startPoint.x;  //起始点的横坐标值与传进去的横坐标的值的差
        float deltaY = p.y - startPoint.y;  //起始点的纵坐标值与传进去的纵坐标的值的差

        line.setLine(       //设置新的直线段，因为平移故该直线段的长度不变
                line.x1 + deltaX, line.y1 + deltaY,
                line.x2 + deltaX, line.y2 + deltaY
        );
    }

    /**
     * 按起始点移动该直线段的方法，传进去新的位置点 p
     * @param x 传进去的用于给定新的直线段位置的位置点（横坐标位置）
     * @param y 传进去的用于给定新的直线段位置的位置点（纵坐标位置）
     *
     */
    @Override
    public void moveToInStart(float x, float y) {
        float deltaX = x - startPoint.x;  //起始点的横坐标值与传进去的横坐标的值的差
        float deltaY = y - startPoint.y;  //起始点的纵坐标值与传进去的纵坐标的值的差

        line.setLine(       //设置新的直线段，因为平移故该直线段的长度不变
                line.x1 + deltaX, line.y1 + deltaY,
                line.x2 + deltaX, line.y2 + deltaY
        );
    }

    /**
     * 设置终点位置的方法，将传进去的p作为该直线段的终点位置，并重新设置该直线段
     * @param x 传进去的新终点位置的横坐标
     * @param y 传进去的新终点位置的纵坐标
     *
     */
    @Override
    public void putEndPoint(float x, float y) {
        endPoint = new Point2D.Float(x, y);     //将传进去的新终点位置传给该直线段类的终点坐标属性
        line.setLine(line.x1, line.y1, x, y);
    }

    /**
     * 判断传进去的点是否在该直线段上的方法，为了方便获得直线段将直线段的宽度扩大了widthTimes倍
     * @param x 传进去的需要判断是否在直线段上的点的横坐标
     * @param y 传进去的需要判断是否在直线段上的点的纵坐标
     * @return 是否在该直线段上
     *
     */
    @Override
    public boolean pointOn(float x, float y) {
        return (line.ptSegDist(x, y) <= borderStrokeWidth);    //通过判断该点与该直线段的距离判断是否在该直线段上
    }

    /**
     * 判断是否被填充方法
     * @return  是否被填充
     * 因为是直线段无法填充，故返回否
     *
     */
    @Override
    public boolean isFilled() {
        return false;
    }

    /**
     * 设置填充的方法
     * 直线段无法填充，故该方法为空
     *
     */
    @Override
    public void setFill() {
    }

    /**
     * 设置填充的方法，传入填充颜色的值
     * @param c 传进去的用于设置填充颜色的颜色值
     *
     */
    @Override
    public void setFill(Color c) {
        color = c;
    }

    /**
     * 取消填充的方法
     * 将是否填充的值设置为否
     *
     */
    @Override
    public void disableFill() {
    }

    /**
     * 判断传进去的点是否在该直线段内部的方法
     * @param p 传进去的点用于是否在该直线段区域内
     * @return 因为直线段无法被填充，故返回否
     *
     */
    @Override
    public boolean pointOnFill(Point.Float p) {
        return false;
    }

    /**
     * 判断传进去的点是否在该直线段内部的方法
     * @param x 传进去的点的横坐标，用于是否在该直线段区域内
     * @param y 传进去的点的横坐标，用于是否在该直线段区域内
     * @return 因为直线段无法被填充，故返回否
     *
     */
    @Override
    public boolean pointOnFill(float x, float y) {
        return false;
    }


}