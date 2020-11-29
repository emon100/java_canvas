import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.awt.geom.Point2D.Float;

import javax.swing.JPanel;
import javax.swing.JTextField;



public class BasicDrawableFactory {
    private static class Line implements Drawable {
        Line2D.Float line;
        BasicStroke borderStroke;
        float alpha = 1f;
        Color color = Color.BLACK;

        private Line(Point point) {
            line = new Line2D.Float(point.x, point.y, point.x, point.y);
            borderStroke = new BasicStroke();
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
            return new Point2D.Float(line.x1, line.y1);
        }

        @Override
        public void putEndPoint(Point2D.Float p) {
            line.setLine(line.x1, line.y1, p.x, p.y);
        }

        @Override
        public Point2D.Float getEndPoint() {
            // TODO Auto-generated method stub
            return new Point2D.Float(line.x1, line.y1);
        }

        @Override
        public boolean pointOn(Point2D.Float p) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void moveStartTo(Float p) {
            // TODO Auto-generated method stub
            line.setLine(p.x, p.y, line.x2, line.y2);
        }

        @Override
        public void moveStartTo(float x, float y) {
            // TODO Auto-generated method stub
            line.setLine(x, y, line.x2, line.y2);
        }

        @Override
        public void putEndPoint(float x, float y) {
            // TODO Auto-generated method stub
            line.setLine(line.x1, line.y1, x, y);
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
        public boolean pointOnFill(Float p) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean pointOnFill(float x, float y) {
            // TODO Auto-generated method stub
            return false;
        }

    }

    private static class Path implements Drawable {
        Point.Float startPoint;
        GeneralPath line;
        BasicStroke borderStroke = new BasicStroke();
        float alpha = 1f;
        Color color = Color.BLACK;

        private Path(Point.Float p) {
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
        public boolean pointOnFill(Float p) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean pointOnFill(float x, float y) {
            // TODO Auto-generated method stub
            return false;
        }


    }

    private static class TextBox implements Drawable{

        Point2D.Float startPoint;

        Color c;

        TextBox(Point2D.Float p) {
            startPoint = p;
        }
        
        //几乎所有的计算都可以借用矩形
        //filled代表是否填充文字
        String filled = null;


        final int radius = 5;

        @Override
        public void drawOnGraphics2D(Graphics2D g) {
            if(!isFilled()){

                FontRenderContext context = g.getFontRenderContext();
                Font f = new Font("SansSerif",Font.BOLD,14);
            }else{
                g.drawOval((int)startPoint.x, (int) startPoint.y,radius,radius);
            }
        }

        @Override 
        public boolean isFilled(){
            return filled==null; 
        }

        @Override
        public void setFill(){ //弹出一个选框，设置文字，影响字体，字号等
            // TODO Auto-generated method stub
        }

        @Override
        public void setFill(Color c){ 
            //TODO 直接设置字颜色
        }

        @Override
        public void setAlpha(float f) {
            // TODO Auto-generated method stub

        }

        @Override
        public void setColor(Color c) {
            // TODO Auto-generated method stub

        }

        @Override
        public void setBorder(Color c, BasicStroke s) {
            System.out.println("ok");
            JPanel panel = new JPanel();
            JTextField tf = new JTextField("Input here",30);
            panel.add(tf);
            panel.setVisible(true);
        }

        @Override
        public Color getBorderColor() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public BasicStroke getBasicStroke() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Color getFillColor() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void disableFill() {
            // TODO Auto-generated method stub

        }

        @Override
        public void scale(float times) {
            // TODO Auto-generated method stub

        }

        @Override
        public void moveStartTo(Float p) {
            startPoint = p ;
        }

        @Override
        public void moveStartTo(float x, float y) {
            startPoint.setLocation(x, y);
        }

        @Override
        public Float getStart() {
            return startPoint;
        }

        @Override
        public void putEndPoint(Float p) {
            startPoint = p;
        }

        @Override
        public void putEndPoint(float x, float y) {
            startPoint.setLocation(x, y);
        }

        @Override
        public Float getEndPoint() {
            return startPoint;
        }

        @Override
        public boolean pointOn(Float p) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean pointOn(float x, float y) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean pointOnFill(Float p) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean pointOnFill(float x, float y) {
            // TODO Auto-generated method stub
            return false;
        }

    }

    public static Drawable makeLine(int startx, int starty, int endx, int endy) {
        var a = new Line(new Point(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
    }

    public static Drawable makePath(int startx, int starty, int endx, int endy) {
        var a = new Path(new Point.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
    }

    public static Drawable makeRec(int startx, int starty, int endx, int endy) {
        var a = new Rectangle(new Point2D.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
    }

    public static Drawable makeTri(int startx, int starty ,int endx, int endy) {
        var a = new Triangle(new Point2D.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
    }

    public static Drawable makeEllipse(int startx, int starty, int endx, int endy) {
        var a = new Ellipse(new Point2D.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
    }

	public static Drawable makeTextBox(int startx, int starty ,int endx, int endy) {
		var a = new TextBox(new Point2D.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
	}
}
