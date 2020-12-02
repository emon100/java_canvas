import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Float;

/**
 * 直线类
 */
public class Line implements Drawable {
    Point2D.Float startPoint;
    Point2D.Float endPoint = null;

    Line2D.Float line;
    MyStroke borderStroke = new MyStroke();
    float alpha = 1f;
    Color color = Color.BLACK;
    int widthTimes = 5;
    float basicBorderStrokeWidth = this.borderStroke.getLineWidth();
    float borderStrokeWidth = widthTimes * basicBorderStrokeWidth;

    public Line(Point2D.Float point) {
        startPoint = point;
        line = new Line2D.Float(point.x, point.y, point.x, point.y);
    }


    @Override
    public void drawOnGraphics2D(Graphics2D g) {
        // initial g settings
        g.setPaint(color); // 设置画笔颜色
        g.setStroke(borderStroke); // 设置画笔
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); // 设置透明度
        //
        g.draw(line);

    }

    @Override
    public void setAlpha(float f) {
        if (f <= 1 && f >= 0) { // validate alpha value
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
    public Color getFillColor() {
        return color;
    }

    @Override
    public void scale(float times) {

        line.setLine(line.x1, line.y1,
                line.x2 * times - line.x1,
                line.y2 * times - line.y1);
    }

    @Override
    public Point2D.Float getStartPoint() {
        return (Point2D.Float) startPoint.clone();
    }

    @Override
    public void putEndPoint(Point2D.Float p) {
        endPoint = p;
        line.setLine(startPoint.x, startPoint.y, p.x, p.y);
    }

    @Override
    public Point2D.Float getEndPoint() {
        return (Point2D.Float) endPoint.clone();
    }

    @Override
    public Rectangle2D getOutBound() {
        return line.getBounds2D();
    }

    @Override
    public Float getTopLeft() {
        return new Point2D.Float(line.getBounds().x, line.getBounds().y);
    }

    @Override
    public Float getBottomRight() {
        return new Point2D.Float(
                line.getBounds().x + line.getBounds().width,
                line.getBounds().y + line.getBounds().height
        );
    }

    @Override
    public boolean pointOn(Point2D.Float p) {
        return (line.ptLineDist(p) <= borderStrokeWidth);
    }

    @Override
    public void moveToInStart(Point2D.Float p) {
        float deltaX = p.x - startPoint.x;
        float deltaY = p.y - startPoint.y;

        line.setLine(line.x1 + deltaX, line.y1 + deltaY,
                line.x2 + deltaX, line.y2 + deltaY);
    }

    @Override
    public void moveToInStart(float x, float y) {
        float deltaX = x - startPoint.x;
        float deltaY = y - startPoint.y;

        line.setLine(line.x1 + deltaX, line.y1 + deltaY,
                line.x2 + deltaX, line.y2 + deltaY);
    }

    @Override
    public void putEndPoint(float x, float y) {
        endPoint = new Point2D.Float(x, y);
        line.setLine(line.x1, line.y1, x, y);
    }

    @Override
    public boolean pointOn(float x, float y) {
        return (line.ptLineDist(x, y) <= borderStrokeWidth);
    }

    @Override
    public boolean isFilled() {
        return false;
    }

    @Override
    public void setFill() {
    }

    @Override
    public void setFill(Color c) {
        color = c;
    }

    @Override
    public void disableFill() {
    }

    @Override
    public boolean pointOnFill(Point.Float p) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean pointOnFill(float x, float y) {
        // TODO Auto-generated method stub
        return false;
    }


}