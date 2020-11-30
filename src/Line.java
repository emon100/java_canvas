import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Float;

/**
 * 直线类
 */
public class Line implements Drawable {
    Line2D.Float line;
    MyStroke borderStroke;
    float alpha = 1f;
    Color color = Color.BLACK;

    public Line(Point point) {
        line = new Line2D.Float(point.x, point.y, point.x, point.y);
        borderStroke = new MyStroke();
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
    public Point2D.Float getStart() {
        return (Float) line.getP1();
    }

    @Override
    public void putEndPoint(Point2D.Float p) {
        line.setLine(line.x1, line.y1, p.x, p.y);
    }

    @Override
    public Point2D.Float getEndPoint() {
        return (Float) line.getP2();
    }

    @Override
    public boolean pointOn(Point2D.Float p) {
        return (line.contains(p));
    }

    @Override
    public void moveStartTo(Point2D.Float p) {
        line.setLine(p.x, p.y, line.x2, line.y2);
    }

    @Override
    public void moveStartTo(float x, float y) {
        line.setLine(x, y, line.x2, line.y2);
    }

    @Override
    public void putEndPoint(float x, float y) {
        line.setLine(line.x1, line.y1, x, y);
    }

    @Override
    public boolean pointOn(float x, float y) {
        return (line.contains(x, y));
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