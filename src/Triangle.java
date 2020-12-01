import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Triangle implements Drawable{

    Triangle2D triangle;
    MyStroke borderStroke = new MyStroke();
    float alpha = 1f;
    Color color = Color.BLACK;
    Color fillColor = Color.BLUE;
    boolean ifFillTri = false;

    int widthTimes = 2;
    float basicBorderStrokeWidth = this.borderStroke.getLineWidth();
    float borderStrokeWidth = this.widthTimes * this.borderStroke.getLineWidth();

    Triangle(Point2D.Float point) {
        triangle = new Triangle2D(point.x, point.y ,point.x ,point.y);
    }

    @Override
    public void drawOnGraphics2D(Graphics2D g) {
        g.setPaint(color);
        g.setStroke(borderStroke);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
        g.draw(triangle.tri);
        if (isFilled()) {
            g.setColor(fillColor);
            g.fill(triangle.tri);
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
    public void moveToInStart(Point2D.Float p) {
        triangle.setTri(p.x, p.y, triangle.x2, triangle.y2);
    }

    @Override
    public void moveToInStart(float x, float y) {
        triangle.setTri(x, y, triangle.x2, triangle.y2);
    }

    @Override
    public void setStartPoint(Point2D.Float p) {
        triangle.x1 = p.x;
        triangle.y1 = p.y;
    }

    @Override
    public void setStartPoint(float x, float y) {
        triangle.x1 = x;
        triangle.y1 = y;
    }

    @Override
    public Point2D.Float getStartPoint() {
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
    public Rectangle2D getOutBound() {
        float x, y;
        float width,height;

        if (triangle.y1 >= triangle.y2) {
            height = triangle.y1 - triangle.y2;
            if (triangle.x1 >= triangle.x2) {
                x = triangle.x2;
                y = triangle.y2;
                width = triangle.x3 - triangle.x2;
            }
            else {
                x = triangle.x3;
                y = triangle.y3;
                width = triangle.x2 - triangle.x3;
            }
        }
        else {
            y = triangle.y1;
            height = triangle.y2 - triangle.y1;
            if (triangle.x1 >= triangle.x2) {
                x = triangle.x2;
                width = triangle.x3 - triangle.x2;
            }
            else {
                x = triangle.x3;
                width = triangle.x2 - triangle.x3;
            }
        }

        return new Rectangle2D.Float(x, y, width, height);

//        return triangle.tri.getBounds2D();
    }

    @Override
    public boolean pointOn(Point2D.Float p) {
        Rectangle2D.Float r = new Rectangle2D.Float(p.x - borderStrokeWidth,
                p.y - borderStrokeWidth,
                2 * borderStrokeWidth,
                2 * borderStrokeWidth);
        return triangle.tri.intersects(r);

//        Triangle2D t1 = getBound();
//        return (triangle.contains(p) && (!t1.contains(p)));

    }

    private Triangle2D getBound() {
        Triangle2D t;
        if (triangle.y1 >= triangle.y2) {
            if (triangle.x1 >= triangle.x2) {
                t = new Triangle2D(triangle.x1,
                        triangle.y1 - borderStrokeWidth,
                        triangle.x2 + borderStrokeWidth,
                        triangle.y2 + borderStrokeWidth);
            }
            else {
                t = new Triangle2D(triangle.x1,
                        triangle.y1 - borderStrokeWidth,
                        triangle.x2 - borderStrokeWidth,
                        triangle.y2 + borderStrokeWidth);
            }
        }
        else {
            if (triangle.x1 >= triangle.x2) {
                t = new Triangle2D(triangle.x1,
                        triangle.y1 + borderStrokeWidth,
                        triangle.x2 + borderStrokeWidth,
                        triangle.y2 - borderStrokeWidth);
            }
            else {
                t = new Triangle2D(triangle.x1,
                        triangle.y1 + borderStrokeWidth,
                        triangle.x2 - borderStrokeWidth,
                        triangle.y2 - borderStrokeWidth);
            }
        }
        return t;
    }

    @Override
    public boolean pointOn(float x, float y) {
        Rectangle2D.Float r = new Rectangle2D.Float(x - borderStrokeWidth,
                y - borderStrokeWidth,
                2 * borderStrokeWidth,
                2 * borderStrokeWidth);
        return triangle.tri.intersects(r);
    }

    @Override
    public boolean pointOnFill(Point2D.Float p) {
        Triangle2D t1 = getBound();
        return (t1.contains(p));
    }

    @Override
    public boolean pointOnFill(float x, float y) {
        Triangle2D t1 = getBound();
        return (t1.contains(x, y));

    }

    private static class Triangle2D implements Serializable{
        Path2D.Float tri = new Path2D.Float();
        float x1,y1,x2,y2,x3,y3;

        float height, halfWidth;
        //Point2D.Float p1,p2,p3;

        Triangle2D(float x1, float y1, float x2, float y2) {
            setTri(x1, y1, x2, y2);
        }

        public boolean contains(float x,float y){
            return tri.contains(x,y);
        }

        public boolean contains(Point2D.Float p) {
            return tri.contains(p);
        }

        private void setTri(float x1, float y1, float x2, float y2) {
            tri.reset();
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;

            x3 = 2 * this.x1 - this.x2;
            y3 = this.y2;

            height = y2 - y1;
            halfWidth = x2 - x1;

//            p1 = new Point2D.Float(x1, y1);
//            p2 = new Point2D.Float(x2, y2);         //终点
//            p3 = new Point2D.Float(x3, y3);        //对称点

            tri.moveTo(x1, y1);
            tri.lineTo(x2, y2);
            tri.lineTo(x3, y3);
            tri.closePath();
        }

    }

}
