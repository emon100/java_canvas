import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Ellipse implements Drawable {

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
        ellipse.setFrame(ellipse.x, ellipse.y,
                ellipse.width * times,
                ellipse.height * times);
    }

    @Override
    public void moveToInStart(Point2D.Float p) {
        ellipse.setFrame(p.x, p.y, ellipse.width, ellipse.height);
    }

    @Override
    public void moveToInStart(float x, float y) {
        ellipse.setFrame(x, y, ellipse.width, ellipse.height);
    }

    @Override
    public void setStartPoint(Point2D.Float p) {
        ellipse.x = p.x;
        ellipse.y = p.y;
    }

    @Override
    public void setStartPoint(float x, float y) {
        ellipse.x = x;
        ellipse.y = y;
    }

    @Override
    public Point2D.Float getStartPoint() {
        return new Point2D.Float(ellipse.x, ellipse.y);
    }

    @Override
    public void putEndPoint(Point2D.Float p) {
        if (p.getX() >= ellipse.x) {
            if (p.getY() >= ellipse.y) {
                ellipse.setFrame(ellipse.x, ellipse.y,
                        p.getX() - ellipse.x, p.getY() - ellipse.y);
            }
            else {
                ellipse.setFrame(ellipse.x, p.getY(),
                        p.getX() - ellipse.x,
                        ellipse.y - p.getY());  //TODO fix ellipse y
            }
        }
        else {
            if (p.getY() >= ellipse.y) {
                ellipse.setFrame(p.getX(), ellipse.y,
                        ellipse.x - p.getX(),
                        p.getY() - ellipse.y);
            }
            else {
                ellipse.setFrame(p.getX(), p.getY(),
                        ellipse.x - p.getX(),
                        ellipse.y - p.getY());
            }
        }

    }

    @Override
    public void putEndPoint(float x, float y) {
        if (x >= ellipse.x) {
            if (y >= ellipse.y) {
                ellipse.setFrame(ellipse.x, ellipse.y,
                        x - ellipse.x, y - ellipse.y);
            }
            else {
                ellipse.setFrame(ellipse.x, y,
                        x - ellipse.x,
                        ellipse.y - y);  //TODO fix ellipse y
            }
        }
        else {
            if (y >= ellipse.y) {
                ellipse.setFrame(x, ellipse.y,
                        ellipse.x - x,
                        y - ellipse.y);
            }
            else {
                ellipse.setFrame(x, y,
                        ellipse.x - x,
                        ellipse.y - y);
            }
        }

    }

    @Override
    public Point2D.Float getEndPoint() {
        return new Point2D.Float(ellipse.x + ellipse.width, ellipse.y + ellipse.height);
    }

    @Override
    public Rectangle2D getOutBound() {
        return ellipse.getBounds2D();
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
        return new Ellipse2D.Float(ellipse.x + basicBorderStrokeWidth,
                ellipse.y + basicBorderStrokeWidth,
                ellipse.width - 2 * basicBorderStrokeWidth,
                ellipse.height - 2 * basicBorderStrokeWidth);
    }

    private Ellipse2D.Float getBiggerEll() {
        return new Ellipse2D.Float(ellipse.x - borderStrokeWidth,
                ellipse.y - borderStrokeWidth,
                ellipse.width + 2 * borderStrokeWidth,
                ellipse.height + 2 * borderStrokeWidth);
    }
}
