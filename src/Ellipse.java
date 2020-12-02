import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Ellipse implements Drawable {

    Point2D.Float startPoint;
    Point2D.Float endPoint = null;
    Ellipse2D.Float ellipse;

    MyStroke borderStroke = new MyStroke();
    float alpha = 1f;
    Color color = Color.BLACK;
    Color fillColor = Color.BLUE;

    int widthTimes = 2;
    float basicBorderStrokeWidth = this.borderStroke.getLineWidth();
    float borderStrokeWidth = this.widthTimes * this.borderStroke.getLineWidth();

    boolean iffFillEll = false;

    Ellipse(Point2D.Float point) {
        startPoint = point;
        ellipse = new Ellipse2D.Float(point.x, point.y, 0, 0);
    }


    @Override
    public void drawOnGraphics2D(Graphics2D g) {
        g.setPaint(color);
        g.setStroke(borderStroke);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
        g.draw(ellipse);
        if (isFilled()) {
            g.setColor(fillColor);
            g.fill(ellipse);
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
        return iffFillEll;
    }

    @Override
    public void setFill() {
        iffFillEll = true;
    }

    @Override
    public void setFill(Color c) {
        iffFillEll = true;
        fillColor = c;
    }

    @Override
    public Color getFillColor() {
        return fillColor;
    }

    @Override
    public void disableFill() {
        iffFillEll = false;
    }

    @Override
    public void scale(float times) {
        ellipse.setFrame(
                ellipse.x, ellipse.y,
                ellipse.width * times,
                ellipse.height * times
        );
    }

    @Override
    public void moveToInStart(Point2D.Float p) {
        float deltaX = p.x - startPoint.x;
        float deltaY = p.y - startPoint.y;

        ellipse.setFrame(
                ellipse.x + deltaX, ellipse.y + deltaY,
                ellipse.width, ellipse.height
        );
    }

    @Override
    public void moveToInStart(float x, float y) {
        float deltaX = x - startPoint.x;
        float deltaY = y - startPoint.y;

        ellipse.setFrame(
                ellipse.x + deltaX, ellipse.y + deltaY,
                ellipse.width, ellipse.height
        );
    }

    @Override
    public Point2D.Float getStartPoint() {
        return (Point2D.Float) startPoint.clone();
    }

    @Override
    public void putEndPoint(Point2D.Float p) {
        endPoint = p;

        if (p.getX() >= startPoint.x) {
            if (p.getY() >= startPoint.y) {
                ellipse.setFrame(
                        startPoint.x, startPoint.y,
                        p.getX() - startPoint.x,
                        p.getY() - startPoint.y
                );
            }
            else {
                ellipse.setFrame(
                        startPoint.x, p.getY(),
                        p.getX() - startPoint.x,
                        startPoint.y - p.getY()
                );
            }
        }
        else {
            if (p.getY() >= startPoint.y) {
                ellipse.setFrame(
                        p.getX(), startPoint.y,
                        startPoint.x - p.getX(),
                        p.getY() - startPoint.y
                );
            }
            else {
                ellipse.setFrame(
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
            if (y >= startPoint.y) {
                ellipse.setFrame(
                        startPoint.x, startPoint.y,
                        x - startPoint.x,
                        y - startPoint.y
                );
            }
            else {
                ellipse.setFrame(
                        startPoint.x, y,
                        x - startPoint.x,
                        startPoint.y - y
                );
            }
        }
        else {
            if (y >= startPoint.y) {
                ellipse.setFrame(
                        x, startPoint.y,
                        startPoint.x - x,
                        y - startPoint.y
                );
            }
            else {
                ellipse.setFrame(
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
    public Rectangle2D getOutBound() {
        return ellipse.getBounds2D();
    }

    @Override
    public Point2D.Float getTopLeft() {
        return new Point2D.Float(ellipse.getBounds().x, ellipse.getBounds().y);

    }

    @Override
    public Point2D.Float getBottomRight() {
        return new Point2D.Float(
                ellipse.getBounds().x + ellipse.getBounds().width,
                ellipse.getBounds().y + ellipse.getBounds().height
        );
    }

    @Override
    public boolean pointOn(Point2D.Float p) {
        Ellipse2D.Float e1 = getSmallerEll();
        Ellipse2D.Float e2 = getBiggerEll();

        return (e2.contains(p) && (!e1.contains(p)));
    }

    @Override
    public boolean pointOn(float x, float y) {
        Ellipse2D.Float e1 = getSmallerEll();
        Ellipse2D.Float e2 = getBiggerEll();

        return (e2.contains(x, y) && (!e1.contains(x, y)));
    }

    @Override
    public boolean pointOnFill(Point2D.Float p) {
        Ellipse2D.Float e1 = getSmallerEll();
        return (e1.contains(p));
    }

    @Override
    public boolean pointOnFill(float x, float y) {
        Ellipse2D.Float e1 = getSmallerEll();
        return (e1.contains(x, y));
    }

    private Ellipse2D.Float getSmallerEll() {
        return new Ellipse2D.Float(
                ellipse.x + basicBorderStrokeWidth,
                ellipse.y + basicBorderStrokeWidth,
                ellipse.width - 2 * basicBorderStrokeWidth,
                ellipse.height - 2 * basicBorderStrokeWidth
        );
    }

    private Ellipse2D.Float getBiggerEll() {
        return new Ellipse2D.Float(
                ellipse.x - borderStrokeWidth,
                ellipse.y - borderStrokeWidth,
                ellipse.width + 2 * borderStrokeWidth,
                ellipse.height + 2 * borderStrokeWidth
        );
    }
}
