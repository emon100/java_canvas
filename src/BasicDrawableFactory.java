import java.awt.*;
import java.awt.geom.*;


public class BasicDrawableFactory {
    private static class Line implements Drawable {
        Line2D.Float line;
        BasicStroke borderStroke;
        float alpha= 1f;
        Color color=Color.BLACK;

        public Line(Point point) {
            line = new Line2D.Float(point.x,point.y,point.x,point.y);
            borderStroke = new BasicStroke();
        }

        @Override
        public void drawOnGraphics2D(Graphics2D g) {
            // initial g settings
            g.setPaint(color);
            g.setStroke(borderStroke);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); //设置透明度
            //
            g.draw(line);

        }

        @Override
        public void setAlpha(float f) {
            if(f<=1 && f>=0){ //validate alpha value
                alpha = f;
            }
        }

        @Override
        public float getAlpha() {
            return alpha;
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
        public void setFillColor(Color c) {
            color = c;
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
        public void MovestartTo(Point2D.Float p) {
            line.setLine(p.x,p.y,line.x2,line.y2);
        }

        @Override
        public Point2D.Float getStart() {
            return new Point2D.Float(line.x1,line.y1);
        }

        @Override
        public void putEndPoint(Point2D.Float p) {
            line.setLine(line.x1,line.y1,p.x,p.y);
        }

        @Override
        public Point2D.Float getEndPoint() {
            // TODO Auto-generated method stub
            return new Point2D.Float(line.x1,line.y1);
        }

        @Override
        public boolean pointOn(Point2D.Float p) {
            // TODO Auto-generated method stub
            return false;
        }

    }
    public static Drawable makeLine(int startx,int starty,int endx,int endy) {
        var a =  new Line(new Point(startx,starty));
        a.putEndPoint(new Point2D.Float(endx,endy));
        return a;
    }
    
}
