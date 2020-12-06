import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.AffineTransform;

/**
 *这个类是任意线类，用于所有任意线的相关操作
 * @author 郑晨升
 *
 */
public class Path implements Drawable {
    Point.Float startPoint;             //该任意线的起始点
    Point2D.Float endPoint;             //该任意线的终点
    GeneralPath line;            //封装好的任意线段对象

    MyStroke borderStroke = new MyStroke(); // 画笔轮廓
    float alpha = 1f; // 透明度
    Color color = Color.BLACK;// 画笔颜色，默认黑色

    int widthTimes = 5;//边界扩大的倍数，用于对画笔宽度进行扩大
    float borderStrokeWidth = this.widthTimes * this.borderStroke.getBasicStroke().getLineWidth();   //设置的扩大好的画笔宽度，用于扩大矩形轮廓边界

    /**
     * 该任意线类的构造函数，包括设置起始点，构造分装好的任意线类
     * @param p 构造函数传进去的点
     *
     */
    public Path(Point.Float p) {
        startPoint = p;     //将该任意线类的起始点设置为传进去的点
        line = new GeneralPath();
        line.moveTo(p.x, p.y);  //将该任意线起点移动到起始点
    }

    /**
     * 将该任意线的对象绘制到Graphic2D对象上
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
        g.draw(line);      //绘制该任意线
    }

    /**
     * 设置透明度方法，用于将透明度值传到该任意线类透明度属性上
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
     * 设置颜色方法，将颜色值传到该任意线类颜色属性上
     * @param c 传进去的颜色值
     *
     */
    @Override
    public void setColor(Color c) {
        color = c;
    }

    /**
     * 设置画笔方法，用于将颜色和画笔值传到该任意线类上
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
     * @return  该任意线的画笔的颜色值
     *
     */
    @Override
    public Color getBorderColor() {
        return color;
    }

    /**
     * 获得画笔值方法
     * @return  该任意线的画笔值
     *
     */
    @Override
    public MyStroke getMyStroke() {
        return borderStroke;
    }

    /**
     * 获得该任意线类填充颜色的方法
     * @return 该任意线的填充颜色值
     *
     */
    @Override
    public Color getFillColor() {
        return color;
    }

    /**
     * 缩放扩大该任意线的方法，传进去倍数值
     * @param times 传进去的用于任意线缩放扩大的倍数值
     *
     */
    @Override
    public void scale(float times) {
        line.transform(AffineTransform.getScaleInstance(times, times));
    }


    /**
     * 设置终点位置的方法，将传进去的p作为该任意线的终点位置，并重新设置该任意线
     * @param p 传进去的新终点位置
     *
     */
    @Override
    public void putEndPoint(Point2D.Float p) {
        endPoint = p;       //将传进去的新终点位置传给该任意线类的终点坐标属性
        line.lineTo(p.x, p.y);
    }

    /**
     * 获得终点坐标的方法
     * @return 获得该任意线类终点坐标属性
     *
     */
    @Override
    public Point2D.Float getEndPoint() {
        return  endPoint;
    }

    /**
     * 获得该任意线的矩形边框的方法
     * @return 获得的该任意线的矩形边框
     *
     */
    @Override
    public Rectangle2D getOutBound() {
        return line.getBounds2D();
    }

    /**
     * 获得该任意线的矩形边框左上角点的方法
     * @return 该任意线的矩形边框的左上角点
     *
     */
    @Override
    public Point2D.Float getTopLeft() {
        return new Point2D.Float(line.getBounds().x, line.getBounds().y);
    }

    /**
     * 获得该任意线的矩形边框右下角点的方法
     * @return 该任意线的矩形边框的右下角点
     *
     */
    @Override
    public Point2D.Float getBottomRight() {
        return new Point2D.Float(
                line.getBounds().x + line.getBounds().width,
                line.getBounds().y + line.getBounds().height
        );
    }

    /**
     * 判断传进去的点是否在该任意线上的方法
     * @param p 传进去的需要判断是否在任意线上的点
     * @return 是否在该任意线上
     * 该方法通过建立以p点为中心、以2*borderStrokeWidth为长宽的矩形，并判断该矩形是否与该任意线相交来判断是否在该任意线上
     *
     */
    @Override
    public boolean pointOn(Point2D.Float p) {
//        扩大点至矩形，判断是否相交
        Rectangle2D.Float r = new Rectangle2D.Float(p.x - borderStrokeWidth,
                p.y - borderStrokeWidth,
                2 * borderStrokeWidth,
                2 * borderStrokeWidth);
        return line.intersects(r);      //判断是否相交
    }

    /**
     * 按起始点移动该任意线的方法，传进去新的位置点 p
     * @param p 传进去的用于给定新的任意线位置的位置点
     *
     */
    @Override
    public void moveToInStart(Point2D.Float p) {
        line.transform(AffineTransform.getTranslateInstance(p.getX() - startPoint.getX(),
                p.getY() - startPoint.getY()));

        startPoint = p;
    }

    /**
     * 按起始点移动该任意线的方法，传进去新的位置点 p
     * @param x 传进去的用于给定新的任意线位置的位置点（横坐标位置）
     * @param y 传进去的用于给定新的任意线位置的位置点（纵坐标位置）
     *
     */
    @Override
    public void moveToInStart(float x, float y) {
        line.transform(AffineTransform.getTranslateInstance(x - startPoint.getX(),
                y - startPoint.getY()));
        startPoint.setLocation(x, y);
    }

    /**
     * 获得该任意线初始点位置的方法
     * @return 该任意线初始点
     *
     */
    @Override
    public Point2D.Float getStartPoint() {
        return startPoint;
    }

    /**
     * 设置终点位置的方法，将传进去的p作为该任意线的终点位置，并重新设置该任意线
     * @param x 传进去的新终点位置的横坐标
     * @param y 传进去的新终点位置的纵坐标
     *
     */
    @Override
    public void putEndPoint(float x, float y) {
        endPoint = new Point2D.Float(x, y);
        line.lineTo(x, y);
    }

    /**
     * 判断传进去的点是否在该任意线上的方法
     * @param x 传进去的需要判断是否在任意线上的点的横坐标
     * @param y 传进去的需要判断是否在任意线上的点的纵坐标
     * @return 是否在该任意线上
     * 该方法通过建立以p点为中心、以2*borderStrokeWidth为长宽的矩形，并判断该矩形是否与该任意线相交来判断是否在该任意线上
     *
     */
    @Override
    public boolean pointOn(float x, float y) {
        //        扩大点至矩形，判断是否相交
        Rectangle2D.Float r = new Rectangle2D.Float(x - borderStrokeWidth,
                y - borderStrokeWidth,
                2 * borderStrokeWidth,
                2 * borderStrokeWidth);
        return line.intersects(r);      //判断是否相交
    }

    /**
     * 判断是否被填充方法
     * @return  是否被填充
     * 因为是任意线无法填充，故返回否
     *
     */
    @Override
    public boolean isFilled() {
        return false;
    }

    /**
     * 设置填充的方法
     * 任意线无法填充，故该方法为空
     *
     */
    @Override
    public void setFill() { }

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
    public void disableFill() { }

    /**
     * 判断传进去的点是否在该任意线内部的方法
     * @param p 传进去的点用于是否在该任意线区域内
     * @return 因为任意线无法被填充，故返回否
     *
     */
    @Override
    public boolean pointOnFill(Point2D.Float p) {
        return false;
    }

    /**
     * 判断传进去的点是否在该任意线内部的方法
     * @param x 传进去的点的横坐标，用于是否在该任意线区域内
     * @param y 传进去的点的横坐标，用于是否在该任意线区域内
     * @return 因为任意线无法被填充，故返回否
     *
     */
    @Override
    public boolean pointOnFill(float x, float y) {
        return false;
    }


}
