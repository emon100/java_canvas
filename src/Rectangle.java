import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Rectangle implements Drawable{
    Rectangle2D.Float rectangle;
    BasicStroke borderStroke;       //画笔轮廓
    float alpha = 1f;               //透明度
    Color color = Color.BLACK;      //画笔颜色
    Color fillColor = Color.BLUE;  //填充颜色
    boolean ifFillRec = false;      //是否填充

    Rectangle(Point2D.Float point){
        rectangle = new Rectangle2D.Float(point.x,point.y,0,0);
        borderStroke = new BasicStroke();
    }

    @Override
    public void drawOnGraphics2D(Graphics2D g) {
        g.setPaint(color);
        g.setStroke(borderStroke);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
        g.draw(rectangle);
        if (isFilled()) {            //填充
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
        rectangle.setRect(rectangle.x, rectangle.y,
                rectangle.width * times,
                rectangle.height * times);
    }

    @Override
    public void moveStartTo(Point2D.Float p) {
        rectangle.setRect(p.x,p.y,rectangle.width,rectangle.height);
    }

    @Override
    public void moveStartTo(float x, float y) {
        rectangle.setRect(x,y,rectangle.width,rectangle.height);
    }

    @Override
    public Point2D.Float getStart() {
        return new Point2D.Float(rectangle.x,rectangle.y);
    }

    @Override
    public void putEndPoint(Point2D.Float p) {
        rectangle.setRect(rectangle.x,rectangle.y,p.x - rectangle.x,p.y - rectangle.y);
    }

    @Override
    public void putEndPoint(float x, float y) {
        rectangle.setRect(rectangle.x,rectangle.y,x - rectangle.x,y - rectangle.y);
    }

    @Override
    public Point2D.Float getEndPoint() {
        return new Point2D.Float(rectangle.x + rectangle.width,rectangle.y + rectangle.height);
    }

    @Override
    public boolean pointOn(Point2D.Float p) {
        if (rectangle.width >= 0) {
            if (rectangle.height >= 0) {
                return (((p.x >= rectangle.x && p.x <= rectangle.x + rectangle.width)
                        && (p.y == rectangle.y || p.y == rectangle.y + rectangle.height))
                        || ((p.x == rectangle.x || p.x == rectangle.x + rectangle.width)
                        && (p.y >= rectangle.y && p.y <= rectangle.y + rectangle.height)));

            }
            else {
                return (((p.x >= rectangle.x && p.x <= rectangle.x + rectangle.width)
                        && (p.y == rectangle.y || p.y == rectangle.y + rectangle.height))
                        || ((p.x == rectangle.x || p.x == rectangle.x + rectangle.width)
                        && (p.y <= rectangle.y && p.y >= rectangle.y + rectangle.height)));

            }
        }

        else {
            if (rectangle.height >= 0) {
                return (((p.x <= rectangle.x && p.x >= rectangle.x + rectangle.width)
                        && (p.y == rectangle.y || p.y == rectangle.y + rectangle.height))
                        || ((p.x == rectangle.x || p.x == rectangle.x + rectangle.width)
                        && (p.y >= rectangle.y && p.y <= rectangle.y + rectangle.height)));

            }
            else {
                return (((p.x <= rectangle.x && p.x >= rectangle.x + rectangle.width)
                        && (p.y == rectangle.y || p.y == rectangle.y + rectangle.height))
                        || ((p.x == rectangle.x || p.x == rectangle.x + rectangle.width)
                        && (p.y <= rectangle.y && p.y >= rectangle.y + rectangle.height)));

            }

        }
    }

    @Override
    public boolean pointOn(float x, float y) {
        if (rectangle.width >= 0) {
            if (rectangle.height >= 0) {
                return (((x >= rectangle.x && x <= rectangle.x + rectangle.width)
                        && (y == rectangle.y || y == rectangle.y + rectangle.height))
                        || ((x == rectangle.x || x == rectangle.x + rectangle.width)
                        && (y >= rectangle.y && y <= rectangle.y + rectangle.height)));

            }
            else {
                return (((x >= rectangle.x && x <= rectangle.x + rectangle.width)
                        && (y == rectangle.y || y == rectangle.y + rectangle.height))
                        || ((x == rectangle.x || x == rectangle.x + rectangle.width)
                        && (y <= rectangle.y && y >= rectangle.y + rectangle.height)));

            }
        }

        else {
            if (rectangle.height >= 0) {
                return (((x <= rectangle.x && x >= rectangle.x + rectangle.width)
                        && (y == rectangle.y || y == rectangle.y + rectangle.height))
                        || ((x == rectangle.x || x == rectangle.x + rectangle.width)
                        && (y >= rectangle.y && y <= rectangle.y + rectangle.height)));

            }
            else {
                return (((x <= rectangle.x && x >= rectangle.x + rectangle.width)
                        && (y == rectangle.y || y == rectangle.y + rectangle.height))
                        || ((x == rectangle.x || x == rectangle.x + rectangle.width)
                        && (y <= rectangle.y && y >= rectangle.y + rectangle.height)));

            }

        }

    }

    @Override
    public boolean pointOnFill(Point2D.Float p) {
        if (rectangle.width >= 0) {
            if (rectangle.height >= 0) {
                return (p.x < rectangle.x + rectangle.width && p.x > rectangle.x)
                        && (p.y < rectangle.y + rectangle.height && p.y > rectangle.y);
            }

            else {
                return (p.x < rectangle.x + rectangle.width && p.x > rectangle.x)
                        && (p.y > rectangle.y + rectangle.height && p.y < rectangle.y);

            }
        }

        else {
            if (rectangle.height >= 0) {
                return (p.x > rectangle.x + rectangle.width && p.x < rectangle.x)
                        && (p.y < rectangle.y + rectangle.height && p.y > rectangle.y);
            }

            else {
                return (p.x > rectangle.x + rectangle.width && p.x < rectangle.x)
                        && (p.y > rectangle.y + rectangle.height && p.y < rectangle.y);

            }
        }
    }

    @Override
    public boolean pointOnFill(float x, float y) {
        if (rectangle.width >= 0) {
            if (rectangle.height >= 0) {
                return (x < rectangle.x + rectangle.width && x > rectangle.x)
                        && (y < rectangle.y + rectangle.height && y > rectangle.y);
            }

            else {
                return (x < rectangle.x + rectangle.width && x > rectangle.x)
                        && (y > rectangle.y + rectangle.height && y < rectangle.y);

            }
        }

        else {
            if (rectangle.height >= 0) {
                return (x > rectangle.x + rectangle.width && x < rectangle.x)
                        && (y < rectangle.y + rectangle.height && y > rectangle.y);
            }

            else {
                return (x > rectangle.x + rectangle.width && x < rectangle.x)
                        && (y > rectangle.y + rectangle.height && y < rectangle.y);

            }
        }

    }
}