import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Ellipse implements Drawable {

    Ellipse2D.Float ellipse;
    BasicStroke borderStroke = new BasicStroke();
    float alpha = 1f;
    Color color = Color.BLACK;
    Color fillColor = Color.BLUE;
    float borderStrokeWidth = this.borderStroke.getLineWidth();

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
    public void setBorder(Color c, BasicStroke s) {
        color = c;
        borderStroke = s;
    }

    @Override
    public Color getBorderColor() {
        return color;
    }

    @Override
    public BasicStroke getBasicStroke() {
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
    public void moveStartTo(Point2D.Float p) {
        ellipse.setFrame(p.x, p.y, ellipse.width, ellipse.height);
    }

    @Override
    public void moveStartTo(float x, float y) {
        ellipse.setFrame(x, y, ellipse.width, ellipse.height);
    }

    @Override
    public Point2D.Float getStart() {
        return new Point2D.Float(ellipse.x, ellipse.y);
    }

    @Override
    public void putEndPoint(Point2D.Float p) {
        ellipse.setFrame(ellipse.x, ellipse.y, p.x - ellipse.x, p.y - ellipse.y);
    }

    @Override
    public void putEndPoint(float x, float y) {
        ellipse.setFrame(ellipse.x, ellipse.y, x - ellipse.x, y - ellipse.y);
    }

    @Override
    public Point2D.Float getEndPoint() {
        return new Point2D.Float(ellipse.x + ellipse.width, ellipse.y + ellipse.height);
    }

    @Override
    public boolean pointOn(Point2D.Float p) {
        Ellipse2D.Float e1 = new Ellipse2D.Float(ellipse.x - borderStrokeWidth,
                ellipse.y - borderStrokeWidth,
                ellipse.width - borderStrokeWidth,
                ellipse.height - borderStrokeWidth);
        return (ellipse.contains(p) && (!e1.contains(p)));
    }

    @Override
    public boolean pointOn(float x, float y) {
        Ellipse2D.Float e1 = new Ellipse2D.Float(ellipse.x - borderStrokeWidth,
                ellipse.y - borderStrokeWidth,
                ellipse.width - borderStrokeWidth,
                ellipse.height - borderStrokeWidth);
        return (ellipse.contains(x, y) && (!e1.contains(x, y)));
    }

    @Override
    public boolean pointOnFill(Point2D.Float p) {
        Ellipse2D.Float e1 = new Ellipse2D.Float(ellipse.x - borderStrokeWidth,
                ellipse.y - borderStrokeWidth,
                ellipse.width - borderStrokeWidth,
                ellipse.height - borderStrokeWidth);
        return (e1.contains(p));
    }

    @Override
    public boolean pointOnFill(float x, float y) {
        Ellipse2D.Float e1 = new Ellipse2D.Float(ellipse.x - borderStrokeWidth,
                ellipse.y - borderStrokeWidth,
                ellipse.width - borderStrokeWidth,
                ellipse.height - borderStrokeWidth);
        return (e1.contains(x, y));
    }
}
