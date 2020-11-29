import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class Triangle implements Drawable{

    Triangle2D triangle;
    BasicStroke borderStroke = new BasicStroke();
    float alpha = 1f;
    Color color = Color.BLACK;
    Color fillColor = Color.BLUE;
    boolean ifFillTri = false;
    float borderStrokeWidth = this.borderStroke.getLineWidth();

    Triangle(Point2D.Float point) {
        triangle = new Triangle2D(point.x, point.y ,point.x ,point.y);
    }

    @Override
    public void drawOnGraphics2D(Graphics2D g) {
        g.setPaint(color);
        g.setStroke(borderStroke);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
        g.draw(triangle);
        if (isFilled()) {
            g.setColor(fillColor);
            g.fill(triangle);
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
        return ifFillTri;
    }

    @Override
    public void setFill() {
        ifFillTri = true;
        fillColor = Color.BLUE;
    }

    @Override
    public void setFill(Color c) {
        ifFillTri = true;
        fillColor = c;
    }

    @Override
    public Color getFillColor() {
        return fillColor;
    }

    @Override
    public void disableFill() {
        ifFillTri = false;
    }

    @Override
    public void scale(float times) {
        triangle.setTri(triangle.x1, triangle.y1,
                triangle.x1 + triangle.halfWidth * times,
                triangle.y1 + triangle.height * times);
    }

    @Override
    public void moveStartTo(Point2D.Float p) {
        triangle.setTri(p.x, p.y, triangle.x2, triangle.y2);
    }

    @Override
    public void moveStartTo(float x, float y) {
        triangle.setTri(x, y, triangle.x2, triangle.y2);
    }

    @Override
    public Point2D.Float getStart() {
        return new Point2D.Float(triangle.x1, triangle.y1);
    }

    @Override
    public void putEndPoint(Point2D.Float p) {
        triangle.setTri(triangle.x1, triangle.y1, p.x, p.y);
    }

    @Override
    public void putEndPoint(float x, float y) {
        triangle.setTri(triangle.x1, triangle.y1, x, y);
    }

    @Override
    public Point2D.Float getEndPoint() {
        return new Point2D.Float(triangle.x2, triangle.y2);
    }

    @Override
    public boolean pointOn(Point2D.Float p) {
    /*    if (p.y == triangle.y2) {
            if (triangle.x2 >= triangle.x1) {
                return (p.x <= triangle.x2 && p.x >= triangle.x3);
            }
            else
                return (p.x >= triangle.x2 && p.x <= triangle.x3);
        }
        else {
            float k1 = triangle.getK(triangle.x1, triangle.y1, triangle.x2, triangle.y2);
            float k2 = triangle.getK(triangle.x1, triangle.y1, triangle.x3, triangle.y3);
            float b1 = triangle.getB(triangle.x1, triangle.y1, k1);
            float b2 = triangle.getB(triangle.x1, triangle.y1, k2);

            if (triangle.x2 >= triangle.x3) {
                if (p.x >= triangle.x3 && p.x <= triangle.x1) {
                    return (p.y == triangle.func(k2, b2 , p.x));
                }
                else if (p.x >= triangle.x1 && p.x <= triangle.x2) {
                    return (p.y == triangle.func(k1, b1, p.x));
                }
                else return false;
            }

            else {
                if (p.x >= triangle.x2 && p.x <= triangle.x1) {
                    return (p.y == triangle.func(k1, b1, p.x));
                }
                else if (p.x >= triangle.x1 && p.x <= triangle.x3) {
                    return (p.y == triangle.func(k1, b1, p.x));
                }
                else return false;

            }

        }*/

        Triangle2D t1 = new Triangle2D(triangle.x1 - borderStrokeWidth,
                triangle.y1 - borderStrokeWidth,
                triangle.x2 -borderStrokeWidth,
                triangle.y2 + borderStrokeWidth);
        return (triangle.contains(p) && (!t1.contains(p)));

    }

    @Override
    public boolean pointOn(float x, float y) {
        Triangle2D t1 = new Triangle2D(triangle.x1 - borderStrokeWidth,
                triangle.y1 - borderStrokeWidth,
                triangle.x2 -borderStrokeWidth,
                triangle.y2 + borderStrokeWidth);
        return (triangle.contains(x, y) && (!t1.contains(x, y)));
    }

    @Override
    public boolean pointOnFill(Point2D.Float p) {
        Triangle2D t1 = new Triangle2D(triangle.x1 - borderStrokeWidth,
                triangle.y1 - borderStrokeWidth,
                triangle.x2 -borderStrokeWidth,
                triangle.y2 + borderStrokeWidth);
        return (t1.contains(p));
    }

    @Override
    public boolean pointOnFill(float x, float y) {
        Triangle2D t1 = new Triangle2D(triangle.x1 - borderStrokeWidth,
                triangle.y1 - borderStrokeWidth,
                triangle.x2 -borderStrokeWidth,
                triangle.y2 + borderStrokeWidth);
        return (t1.contains(x, y));

    }

    private static class Triangle2D extends Path2D.Float {
        float x1,y1,x2,y2,x3,y3;

        float height, halfWidth;
        Point2D.Float p1,p2,p3;

        Triangle2D(float x1, float y1, float x2, float y2) {
            setTri(x1, y1, x2, y2);
        }

        private void setTri(float x1, float y1, float x2, float y2) {
            this.reset();
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;

            x3 = 2 * this.x1 - this.x2;
            y3 = this.y2;

            height = y2 - y1;
            halfWidth = x2 - x1;

            p1 = new Point2D.Float(x1, y1);
            p2 = new Point2D.Float(x2, y2);         //终点
            p3 = new Point2D.Float(x3, y3);        //对称点

            moveTo(p1.x, p1.y);
            lineTo(p2.x, p2.y);
            lineTo(p3.x, p3.y);
            closePath();
        }

    }

}
