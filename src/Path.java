import java.awt.*;
import java.awt.geom.*;
public class Path implements Drawable {
    Point.Float startPoint;
    GeneralPath line;
    BasicStroke borderStroke = new BasicStroke();
    float alpha = 1f;
    Color color = Color.BLACK;

    public Path(Point.Float p) {
        startPoint = p;
        line = new GeneralPath();
        line.moveTo(p.x, p.y);
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
    public Color getFillColor() {
        return color;
    }

    @Override
    public void scale(float times) {
        // TODO Auto-generated method stub
    }

    @Override
    public Point2D.Float getStart() {
        return startPoint;
    }

    @Override
    public void putEndPoint(Point2D.Float p) {
        line.lineTo(p.x, p.y);
    }

    @Override
    public Point2D.Float getEndPoint() {
        // TODO Auto-generated method stub
        return  (Point2D.Float) line.getCurrentPoint();
    }

    @Override
    public boolean pointOn(Point2D.Float p) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void moveStartTo(Point2D.Float p) {
        // TODO Auto-generated method stub
        startPoint = p;
        line.moveTo(p.x, p.y);
        
    }

    @Override
    public void moveStartTo(float x, float y) {
        // TODO Auto-generated method stub
        startPoint.setLocation(x, y);
        line.moveTo(x, y);
    }

    @Override
    public void putEndPoint(float x, float y) {
        // TODO Auto-generated method stub
        line.lineTo(x, y);
    }

    @Override
    public boolean pointOn(float x, float y) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isFilled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setFill() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFill(Color c) {
        // TODO Auto-generated method stub

    }

    @Override
    public void disableFill() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean pointOnFill(Point2D.Float p) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean pointOnFill(float x, float y) {
        // TODO Auto-generated method stub
        return false;
    }


}
