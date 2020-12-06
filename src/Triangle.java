import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 *这个类是三角形类，用于所有三角形的相关操作
 * @author 郑晨升
 *
 */
public class Triangle implements Drawable{

    Triangle2D triangle;                        //封装好的三角形对象
    MyStroke borderStroke = new MyStroke(); // 画笔轮廓
    float alpha = 1f; // 透明度
    Color color = Color.BLACK; // 画笔颜色，默认黑色
    Color fillColor = Color.BLUE; // 填充颜色，默认蓝色
    boolean ifFillTri = false; // 是否填充，默认未被填充

    int widthTimes = 5;       //边界扩大的倍数，用于对画笔宽度进行扩大
    float basicBorderStrokeWidth = this.borderStroke.getBasicStroke().getLineWidth();    //basic的画笔宽度
    float borderStrokeWidth = this.widthTimes * this.borderStroke.getBasicStroke().getLineWidth();   //设置的扩大好的画笔宽度，用于扩大矩形轮廓边界

    /**
     * 该三角形类的构造函数，包括设置起始点，构造分装好的三角形类
     * @param point 构造函数传进去的点
     *
     */
    Triangle(Point2D.Float point) {
        triangle = new Triangle2D(point.x, point.y ,point.x ,point.y);
    }

    /**
     * 将该三角形对象绘制到Graphic2D对象上
     * @param g 绘制到的Graphic2D对象
     *
     */
    @Override
    public void drawOnGraphics2D(Graphics2D g) {
        g.setPaint(color);              //设置颜色
        g.setStroke(borderStroke.getBasicStroke());      //设置画笔
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha)); //设置透明度
        g.draw(triangle.tri);           //绘制该矩形
        if (isFilled()) {               //判断是否填充
            g.setColor(fillColor);      //设置填充颜色
            g.fill(triangle.tri);       //填充该矩形
        }
    }

    /**
     * 设置透明度方法，用于将透明度值传到该三角形类透明度属性上
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
     * 设置颜色方法，将颜色值传到该三角形类颜色属性上
     * @param c 传进去的颜色值
     *
     */
    @Override
    public void setColor(Color c) {
        color = c;
    }

    /**
     * 设置画笔方法，用于将颜色和画笔值传到该三角形类上
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
     * @return  该三角形的画笔的颜色值
     *
     */
    @Override
    public Color getBorderColor() {
        return color;
    }

    /**
     * 获得画笔值方法
     * @return  该三角形的画笔值
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
        return ifFillTri;
    }

    /**
     * 设置填充的方法
     * 将是否被填充的值设置为是
     * 将填充颜色设置为默认颜色蓝色
     *
     */
    @Override
    public void setFill() {
        ifFillTri = true;
        fillColor = Color.BLUE;
    }

    /**
     * 设置填充的方法，传入填充颜色的值
     * @param c 传进去的用于设置填充颜色的颜色值
     *
     */
    @Override
    public void setFill(Color c) {
        ifFillTri = true;
        fillColor = c;
    }

    /**
     * 获得该三角形类填充颜色的方法
     * @return 该三角形的填充颜色值
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
        ifFillTri = false;
    }

    /**
     * 缩放扩大该三角形的方法，传进去倍数值
     * @param times 传进去的用于三角形缩放扩大的倍数值
     *
     */
    @Override
    public void scale(float times) {
        triangle.setTri(        //调用了.setTri方法，用于将三角形重新设置
                triangle.x1, triangle.y1,
                triangle.x1 + triangle.halfWidth * times,
                triangle.y1 + triangle.height * times
        );
    }

    /**
     * 按起始点移动该三角形的方法，传进去新的位置点 p
     * @param p 传进去的用于给定新的三角形位置的位置点
     *          
     */
    @Override
    public void moveToInStart(Point2D.Float p) {
        triangle.setTri(
                p.x, p.y,
                p.x + triangle.halfWidth,
                p.y + triangle.height
        );
    }

    /**
     * 按起始点移动该三角形的方法，传进去新的位置点 p
     * @param x 传进去的用于给定新的三角形位置的位置点（横坐标位置）
     * @param y 传进去的用于给定新的三角形位置的位置点（纵坐标位置）
     *
     */
    @Override
    public void moveToInStart(float x, float y) {
        triangle.setTri(
                x, y,
                x + triangle.halfWidth,
                y + triangle.height
        );
    }

    /**
     * 获得该三角形初始点位置的方法
     * @return 该三角形初始点属性的克隆点
     * 对于三角形来说，起始点永远是x1，y1也就是顶点
     *
     */
    @Override
    public Point2D.Float getStartPoint() {
        return new Point2D.Float(triangle.x1, triangle.y1);
    }

    /**
     * 设置终点位置的方法，将传进去的p作为该三角形的终点位置，并重新设置该三角形
     * @param p 传进去的新终点位置
     *
     */
    @Override
    public void putEndPoint(Point2D.Float p) {
        triangle.setTri(triangle.x1, triangle.y1, p.x, p.y);
    }

    /**
     * 设置终点位置的方法，将传进去的p作为该三角形的终点位置，并重新设置该三角形
     * @param x 传进去的新终点位置的横坐标
     * @param y 传进去的新终点位置的纵坐标
     *
     */
    @Override
    public void putEndPoint(float x, float y) {
        triangle.setTri(triangle.x1, triangle.y1, x, y);
    }

    /**
     * 获得终点坐标的方法
     * @return 获得该三角形类终点坐标
     * 对于三角形来说，终点点永远是x2，y2
     */
    @Override
    public Point2D.Float getEndPoint() {
        return new Point2D.Float(triangle.x2, triangle.y2);
    }

    /**
     * 获得该三角形的矩形边框的方法
     * @return 获得的该三角形的矩形边框
     *
     */
    @Override
    public Rectangle2D getOutBound() {
        float x, y;             //该三角形矩形边框的左上角点
        float width,height;     //该三角形矩形边框的的宽和高

        if (triangle.y1 >= triangle.y2) {
            height = triangle.y1 - triangle.y2;
            if (triangle.x1 >= triangle.x2) {       //该三角形的终点在顶点的第二象限
                x = triangle.x2;
                y = triangle.y2;
                width = triangle.x3 - triangle.x2;
            }
            else {                                  //该三角形的终点在顶点的第一象限
                x = triangle.x3;
                y = triangle.y3;
                width = triangle.x2 - triangle.x3;
            }
        }
        else {
            y = triangle.y1;
            height = triangle.y2 - triangle.y1;
            if (triangle.x1 >= triangle.x2) {       //该三角形的终点在顶点的第三象限
                x = triangle.x2;
                width = triangle.x3 - triangle.x2;
            }
            else {                                  //该三角形的终点在顶点的第四象限
                x = triangle.x3;
                width = triangle.x2 - triangle.x3;
            }
        }

        return new Rectangle2D.Float(x, y, width, height);  //按照找到的x,y,width,height构造该三角形的矩形边框
    }

    /**
     * 获得该三角形矩形边框左上角点的方法
     * @return 该三角形矩形边框的左上角点
     *
     */
    @Override
    public Point2D.Float getTopLeft() {
        return new Point2D.Float(triangle.tri.getBounds().x,
                triangle.tri.getBounds().y);
    }

    /**
     * 获得该三角形矩形边框右下角点的方法
     * @return 该三角形矩形边框的右下角点
     *
     */
    @Override
    public Point2D.Float getBottomRight() {
        return new Point2D.Float(
                triangle.tri.getBounds().x + triangle.tri.getBounds().width,
                triangle.tri.getBounds().y + triangle.tri.getBounds().height
        );
    }

    /**
     * 判断传进去的点是否在该三角形边框上的方法，为了方便获得边框将边框的宽度扩大了widthTimes倍
     * @param p 传进去的需要判断是否在三角形上的点
     * @return 是否在该三角形边框上
     * 通过构造以p点为中心的小正方形，判断该正方形是否与该三角形边框相交来判断是否在该三角形边框上
     *
     */
    @Override
    public boolean pointOn(Point2D.Float p) {
        Rectangle2D.Float r = new Rectangle2D.Float(    //构造以p点为中心的矩形
                p.x - borderStrokeWidth,
                p.y - borderStrokeWidth,
                2 * borderStrokeWidth,
                2 * borderStrokeWidth
        );
        return triangle.tri.intersects(r);      //判断是否相交
    }

    /**
     * 获得该三角形较小的边框的方法
     * @return 该三角形较小的三角形边框
     *
     */
    private Triangle2D getBound() {
        Triangle2D t;       //要返回的该三角形较小的边框
        if (triangle.y1 >= triangle.y2) {
            if (triangle.x1 >= triangle.x2) {       //该三角形的终点在顶点的第二象限
                t = new Triangle2D(
                        triangle.x1,
                        triangle.y1 - borderStrokeWidth,
                        triangle.x2 + borderStrokeWidth,
                        triangle.y2 + borderStrokeWidth
                );
            }
            else {                                  //该三角形的终点在顶点的第一象限
                t = new Triangle2D(
                        triangle.x1,
                        triangle.y1 - borderStrokeWidth,
                        triangle.x2 - borderStrokeWidth,
                        triangle.y2 + borderStrokeWidth
                );
            }
        }
        else {
            if (triangle.x1 >= triangle.x2) {       //该三角形的终点在顶点的第三象限
                t = new Triangle2D(
                        triangle.x1,
                        triangle.y1 + borderStrokeWidth,
                        triangle.x2 + borderStrokeWidth,
                        triangle.y2 - borderStrokeWidth
                );
            }
            else {                                  //该三角形的终点在顶点的第四象限
                t = new Triangle2D(
                        triangle.x1,
                        triangle.y1 + borderStrokeWidth,
                        triangle.x2 - borderStrokeWidth,
                        triangle.y2 - borderStrokeWidth
                );
            }
        }
        return t;
    }

    /**
     * 判断传进去的点是否在该三角形边框上的方法，为了方便获得边框将边框的宽度扩大了widthTimes倍
     * @param x 传进去的需要判断是否在三角形上的点的横坐标
     * @param y 传进去的需要判断是否在三角形上的点的纵坐标
     * @return 是否在该三角形边框上
     * 通过构造以p点为中心的小正方形，判断该正方形是否与该三角形边框相交来判断是否在该三角形边框上
     *
     */
    @Override
    public boolean pointOn(float x, float y) {
        Rectangle2D.Float r = new Rectangle2D.Float(    //构造以p点为中心的矩形
                x - borderStrokeWidth,
                y - borderStrokeWidth,
                2 * borderStrokeWidth,
                2 * borderStrokeWidth
        );
        return triangle.tri.intersects(r);      //判断是否相交
    }

    /**
     * 判断传进去的点是否在该三角形内部的方法
     * @param p 传进去的点用于是否在该三角形区域内
     * @return 该点是否在该三角形内部
     * 通过调用getBound方法获得比该三角形稍小的三角形，若该点与之有交点则为在该三角形内部
     *
     */
    @Override
    public boolean pointOnFill(Point2D.Float p) {
        Triangle2D t1 = getBound();
        return (t1.contains(p));        //判断是否与该较小三角形相交
    }

    /**
     * 判断传进去的点是否在该三角形内部的方法
     * @param x 传进去的点的横坐标，用于是否在该三角形区域内
     * @param y 传进去的点的横坐标，用于是否在该三角形区域内
     * @return 该点是否在该三角形内部
     * 通过调用getBound方法获得比该三角形稍小的三角形，若该点与之有交点则为在该三角形内部
     *
     */
    @Override
    public boolean pointOnFill(float x, float y) {
        Triangle2D t1 = getBound();
        return (t1.contains(x, y));        //判断是否与该较小三角形相交

    }

    /**
     * 继承自接口Serializable的自建的Triangle2D类，用于存放三角形的基本属性和构造
     *
     */
    private static class Triangle2D implements Serializable{
        Path2D.Float tri = new Path2D.Float();  //用Path2D.Float 来绘制三角形的三条边
        float x1,y1,x2,y2,x3,y3;                //三角形的起始点、终点和第三个点

        float height, halfWidth;                //三角形的高和一般的底长
        //Point2D.Float p1,p2,p3;

        /**
         * 该类的构造方法，使用了setTri方法
         * @param x1 传入的该三角形的起始点的横坐标
         * @param y1 传入的该三角形的起始点的纵坐标
         * @param x2 传入的该三角形的终点的横坐标
         * @param y2 传入的该三角形的终点的纵坐标
         *
         */
        Triangle2D(float x1, float y1, float x2, float y2) {
            setTri(x1, y1, x2, y2);
        }

        public boolean contains(float x,float y){
            return tri.contains(x,y);
        }

        public boolean contains(Point2D.Float p) {
            return tri.contains(p);
        }

        /**
         * 设置该三角形基本属性的方法
         * @param x1 传入的该三角形的起始点的横坐标
         * @param y1 传入的该三角形的起始点的纵坐标
         * @param x2 传入的该三角形的终点的横坐标
         * @param y2 传入的该三角形的终点的纵坐标
         *
         */
        private void setTri(float x1, float y1, float x2, float y2) {
            tri.reset();
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;

            x3 = 2 * this.x1 - this.x2;     //该三角形第三个点的横坐标
            y3 = this.y2;                   //该三角形第三个点的纵坐标

            height = y2 - y1;
            halfWidth = x2 - x1;

            tri.moveTo(x1, y1);             //绘制该三角形
            tri.lineTo(x2, y2);
            tri.lineTo(x3, y3);
            tri.closePath();
        }

    }
}
