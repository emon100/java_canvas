import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Rectangle implements Drawable {
    
    Point2D.Float startPoint;
    Point2D.Float endPoint = null;
    Rectangle2D.Float rectangle;
 
    MyStroke borderStroke = new MyStroke(); // 画笔轮廓
    float alpha = 1f; // 透明度
    Color color = Color.BLACK; // 画笔颜色
    Color fillColor = Color.BLUE; // 填充颜色
    boolean ifFillRec = false; // 是否填充

    int widthTimes = 5;
    float basicBorderStrokeWidth = this.borderStroke.getLineWidth();
    float borderStrokeWidth = this.widthTimes * this.borderStroke.getLineWidth();

    Rectangle(Point2D.Float point) {
        startPoint = point;
        rectangle = new Rectangle2D.Float(point.x, point.y, 0, 0);
    }

    @Override
    public void drawOnGraphics2D(Graphics2D g) {
        g.setPaint(color);
        g.setStroke(borderStroke);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.draw(rectangle);
        if (isFilled()) { // 填充
            g.setColor(fillColor);
            g.fill(rectangle);
        }

    }

    @Override
    public void setAlpha(float f) {
        if (f <= 1 && f >= 0) {
            alpha = f;
        }
    }

    @Override
    public void setColor(Color c) {
        color = c;
    }

    @Override
    public void setBorder(Color c, MyStroke s) {
        color = c;
        borderStroke = s;
    }

    @Override
    public Color getBorderColor() {
        return color;
    }

    @Override
    public MyStroke getMyStroke() {
        return borderStroke;
    }

    @Override
    public boolean isFilled() {
        return ifFillRec;
    }

    @Override
    public void setFill() {
        ifFillRec = true;
        fillColor = Color.BLUE;
    }

    @Override
    public void setFill(Color c) {
        ifFillRec = true;
        fillColor = c;
    }

    @Override
    public Color getFillColor() {
        return fillColor;
    }

    @Override
    public void disableFill() {
        ifFillRec = false;
    }

    @Override
    public void scale(float times) {
        rectangle.setRect(
                rectangle.x, rectangle.y,
                rectangle.width * times,
                rectangle.height * times
        );
    }

    @Override
    public void moveToInStart(Point2D.Float p) {
        float deltaX = p.x - startPoint.x;
        float deltaY = p.y - startPoint.y;

        rectangle.setRect(
                rectangle.x + deltaX, rectangle.y + deltaY,
                rectangle.width, rectangle.height
        );
    }

    @Override
    public void moveToInStart(float x, float y) {
        float deltaX = x - startPoint.x;
        float deltaY = y - startPoint.y;

        rectangle.setRect(
                rectangle.x + deltaX,rectangle.y + deltaY,
                rectangle.width,rectangle.height
        );

    }


    @Override
    public Point2D.Float getStartPoint() {
        return (Point2D.Float) startPoint.clone();
    }

    @Override
    /*需要交换起始和终点坐标*/
    public void putEndPoint(Point2D.Float p) {
        endPoint = p;

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
        else {
            if (p.getY() >= startPoint.y) {
                rectangle.setRect(
                        p.getX(), startPoint.y,
                        startPoint.x - p.getX(),
                        p.getY() - startPoint.y
                );
            }
            else {
                rectangle.setRect(
                        p.getX(), p.getY(),
                        startPoint.x - p.getX(),
                        startPoint.y - p.getY()
                );
            }
        }
    }

    @Override
    public void putEndPoint(float x, float y) {
        endPoint = new Point2D.Float(x, y);

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
        else {
            if (y >= startPoint.y) {
                rectangle.setRect(
                        x, startPoint.y,
                        startPoint.x - x,
                        y - startPoint.y
                );
            }
            else {
                rectangle.setRect(
                        x, y,
                        startPoint.x - x,
                        startPoint.y - y
                );
            }
        }

    }

    @Override
    public Point2D.Float getEndPoint() {
        return (Point2D.Float) endPoint.clone();
    }

    @Override
    public boolean pointOn(Point2D.Float p) {
        Rectangle2D.Float r1 = getSmallerRec();
        Rectangle2D.Float r2 = getBiggerRec();

        return (r2.contains(p) && (!r1.contains(p)));
    }

    @Override
    public boolean pointOn(float x, float y) {
        Rectangle2D.Float r1 = getSmallerRec();
        Rectangle2D.Float r2 = getBiggerRec();

        return (r2.contains(x, y) && (!r1.contains(x, y)));
    }

    @Override
    public boolean pointOnFill(Point2D.Float p) {
        Rectangle2D.Float r1 = getSmallerRec();
        return (r1.contains(p));

    }

    @Override
    public boolean pointOnFill(float x, float y) {
        Rectangle2D.Float r1 = getSmallerRec();
        return (r1.contains(x, y));
    }

    private Rectangle2D.Float getSmallerRec() {
        return new Rectangle2D.Float(
                rectangle.x + basicBorderStrokeWidth,
                rectangle.y + basicBorderStrokeWidth,
                rectangle.width - 2 * basicBorderStrokeWidth,
                rectangle.height - 2 * basicBorderStrokeWidth
        );
    }

    private Rectangle2D.Float getBiggerRec() {
        return new Rectangle2D.Float(
                rectangle.x - borderStrokeWidth,
                rectangle.y - borderStrokeWidth,
                rectangle.width + 2 * borderStrokeWidth,
                rectangle.height + 2 * borderStrokeWidth
        );
    }


    @Override
    public Rectangle2D getOutBound() {
        return rectangle.getBounds2D();
    }

    @Override
    public Point2D.Float getTopLeft() {
        return new Point2D.Float(rectangle.getBounds().x, rectangle.getBounds().y);
    }

    @Override
    public Point2D.Float getBottomRight() {
        return new Point2D.Float(
                rectangle.getBounds().x + rectangle.getBounds().width,
                rectangle.getBounds().y + rectangle.getBounds().height
        );
    }
}