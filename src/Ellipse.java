import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Ellipse implements Drawable {

    Ellipse2D ellipse;
    BasicStroke borderStroke;
    float alpha = 1f;
    Color color = Color.BLACK;
    Color fillColor = Color.BLUE;
    boolean iffFillEll = false;

    @Override
    public void drawOnGraphics2D(Graphics2D g) {

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
        return null;
    }

    @Override
    public void disableFill() {

    }

    @Override
    public void scale(float times) {

    }

    @Override
    public void moveStartTo(Point2D.Float p) {

    }

    @Override
    public void moveStartTo(float x, float y) {

    }

    @Override
    public Point2D.Float getStart() {
        return null;
    }

    @Override
    public void putEndPoint(Point2D.Float p) {

    }

    @Override
    public void putEndPoint(float x, float y) {

    }

    @Override
    public Point2D.Float getEndPoint() {
        return null;
    }

    @Override
    public boolean pointOn(Point2D.Float p) {
        return false;
    }

    @Override
    public boolean pointOn(float x, float y) {
        return false;
    }

    @Override
    public boolean pointOnFill(Point2D.Float p) {
        return false;
    }

    @Override
    public boolean pointOnFill(float x, float y) {
        return false;
    }
}
