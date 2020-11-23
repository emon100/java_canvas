import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

class PaintSurface extends JComponent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    ArrayList<Shape> shapes = new ArrayList<Shape>();
    
    public Color[] colors = { Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.RED, Color.BLUE, Color.PINK };
    int ra = 0;// 控制下一个图形的形状
    
    Point startDrag, endDrag;
    Dimension size = getSize(); //当前窗口大小


    GeneralPath gp =  new GeneralPath();
    public PaintSurface() {
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startDrag = e.getPoint();
                endDrag = e.getPoint();
                gp =  new GeneralPath();
                gp.moveTo(startDrag.getX(), startDrag.getY());
                ++ra;
                repaint();
            }
            
            // 鼠标松开是可以创建一个矩形，将起始点归零
            public void mouseReleased(MouseEvent e) {
                if (ra%3 == 0) {
                    Shape r = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
                    shapes.add(r);
                } else if(ra%3 == 1){
                    Shape r = makeLine(startDrag.x, startDrag.y, e.getX(), e.getY());
                    shapes.add(r);
                }else if(ra %3 == 2){
                    shapes.add(gp);
                }
                startDrag = null;
                endDrag = null;
                repaint();
            }
        });
        
        // 鼠标移动时，我们只需要知道鼠标的重点位置就好
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // System.out.println(e.getX()+" "+e.getY());
                int x = e.getX();
                int y = e.getY();
                endDrag.move(x, y);
                gp.lineTo(x, y);

                repaint();
            }
        });
    }
    
    // 初始化背景
    private void paintBackground(Graphics2D g2) {
        g2.setPaint(Color.LIGHT_GRAY);
        
        for (int i = 0; i < size.width; i += 10) {
            Shape line = new Line2D.Float(i, 0, i, size.height);
            g2.draw(line);
        }
        
        for (int i = 0; i < size.height; i += 10) {
            Shape line = new Line2D.Float(0, i, size.width, i);
            g2.draw(line);
        }

    }

    private void paintBoard(Graphics2D g2){
        int colorIndex = 0;
        g2.setStroke(new BasicStroke(2));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f)); //设置透明度

        for (Shape s : shapes) {
            g2.setPaint(Color.BLACK);
            g2.draw(s);
            g2.setPaint(colors[(colorIndex++) % 6]);
            g2.fill(s);
        }

    }

    private Point getToolTipdrawPoint(Point mouseNow){
        int newX =  Math.max(Math.min(mouseNow.x,size.width-70),5);
        int newY =  Math.min(Math.max(mouseNow.y,15),size.height-5);
        return new Point(newX,newY);
    }

    private void paintTips(Graphics2D g2){
        //提示的虚线
        var oldComposite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        if (startDrag != null && endDrag != null) {
            var oldStroke = g2.getStroke();
            g2.setPaint(Color.BLUE);
            float[] dash1 = { 3.0f, 3.0f };
            g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 3.0f));
            Shape r=null;
            if (ra %3 == 0) {
                r = makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
            } else if(ra %3 == 1) {
                r = makeLine(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
            } else{
                r = gp;
            }
            g2.draw(r);
            g2.setStroke(oldStroke);
        
            //提示坐标
            Point toolTipPoint = getToolTipdrawPoint(endDrag);
            g2.setPaint(Color.BLACK);
            g2.drawString("H:"+endDrag.y+" W:"+endDrag.x,toolTipPoint.x,toolTipPoint.y);
        }


        g2.setComposite(oldComposite);
    }


    public void paintComponent(Graphics g) {
        size = getSize();
        System.out.println("paintComponent called");
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // step1: paintBackground
        paintBackground(g2);

        // step2: paintBoard
        paintBoard(g2);
        
        // step3: paintTips
        paintTips(g2);


    }

    private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
        return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    private Line2D.Float makeLine(int x1, int y1, int x2, int y2) {
        return new Line2D.Float(x1, y1, x2, y2);
    }
    //Shape, 种类  ,color,
}
