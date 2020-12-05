import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.AffineTransform;

public class Path implements Drawable {
    Point.Float startPoint;
    Point2D.Float endPoint;
    GeneralPath line;
    MyStroke borderStroke = new MyStroke();
    float alpha = 1f;
    Color color = Color.BLACK;

    int widthTimes = 5;
    float borderStrokeWidth = this.widthTimes * this.borderStroke.getBasicStroke().getLineWidth();

    public Path(Point.Float p) {
        startPoint = p;
        line = new GeneralPath();
        line.moveTo(p.x, p.y);
    }

    @Override
    public void drawOnGraphics2D(Graphics2D g) {
        // initial g settings
        g.setPaint(color); // 设置画笔颜色
        g.setStroke(borderStroke.getBasicStroke()); // 设置画笔
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
        line.transform(AffineTransform.getScaleInstance(times, times));
    }


    @Override
    public void putEndPoint(Point2D.Float p) {
        endPoint = p;
        line.lineTo(p.x, p.y);
    }

    @Override
    public Point2D.Float getEndPoint() {
        return  endPoint;
    }

    @Override
    public Rectangle2D getOutBound() {
        return line.getBounds2D();
    }

    @Override
    public Point2D.Float getTopLeft() {
        return new Point2D.Float(line.getBounds().x, line.getBounds().y);
    }

    @Override
    public Point2D.Float getBottomRight() {
        return new Point2D.Float(
                line.getBounds().x + line.getBounds().width,
                line.getBounds().y + line.getBounds().height
        );
    }

    @Override
    public boolean pointOn(Point2D.Float p) {
//        扩大点至矩形，判断是否相交
        Rectangle2D.Float r = new Rectangle2D.Float(p.x - borderStrokeWidth,
                p.y - borderStrokeWidth,
                2 * borderStrokeWidth,
                2 * borderStrokeWidth);
        return line.intersects(r);
    }

    @Override
    public void moveToInStart(Point2D.Float p) {
        line.transform(AffineTransform.getTranslateInstance(p.getX() - startPoint.getX(),
                p.getY() - startPoint.getY()));

        startPoint = p;
    }

    @Override
    public void moveToInStart(float x, float y) {
        line.transform(AffineTransform.getTranslateInstance(x - startPoint.getX(),
                y - startPoint.getY()));
        startPoint.setLocation(x, y);
    }

    @Override
    public Point2D.Float getStartPoint() {
        return startPoint;
    }

    @Override
    public void putEndPoint(float x, float y) {
        endPoint = new Point2D.Float(x, y);
        line.lineTo(x, y);
    }

    @Override
    public boolean pointOn(float x, float y) {
        //        扩大点至矩形，判断是否相交
        Rectangle2D.Float r = new Rectangle2D.Float(x - borderStrokeWidth,
                y - borderStrokeWidth,
                2 * borderStrokeWidth,
                2 * borderStrokeWidth);
        return line.intersects(r);
    }

    @Override
    public boolean isFilled() {
        return false;
    }

    @Override
    public void setFill() { }

    @Override
    public void setFill(Color c) {
        color = c;
    }

    @Override
    public void disableFill() { }

    @Override
    public boolean pointOnFill(Point2D.Float p) {
        return false;
    }

    @Override
    public boolean pointOnFill(float x, float y) {
        return false;
    }


}
