import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

import javax.swing.*;



public class Canvas extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 2078607234615329835L;

    public static void main(String[] args) {
        new Canvas();
    }

    public Canvas() {
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new PaintSurface(), BorderLayout.CENTER);
        this.setVisible(true);
    }

    private class PaintSurface extends JComponent {
        /**
         *
         */
        private static final long serialVersionUID = 9195128580961035823L;

        ArrayList<Shape> shapes = new ArrayList<Shape>();

        
        Point startDrag, endDrag;
        int ra=0;//控制下一个图形的形状
        public PaintSurface() {
            this.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    // 鼠标按下去会产生一个点
                    startDrag = new Point(e.getX(), e.getY());
                    endDrag = new Point(e.getX(), e.getY());

                    var a = new Random();
                    ra = a.nextInt(2);
                    repaint();
                }

                //鼠标松开是可以创建一个矩形，将起始点归零
                public void mouseReleased(MouseEvent e) {
                    if(ra==0){
                        Shape r = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
                        shapes.add(r);
                    }else{
                        Shape r  = makeLine(startDrag.x, startDrag.y,e.getX(), e.getY());
                        shapes.add(r);
                    }
                    startDrag = null;
                    endDrag = null;
                    repaint();
                }
            });

            //鼠标移动时，我们只需要知道鼠标的重点位置就好
            this.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    //System.out.println(e.getX()+" "+e.getY());
                    endDrag.move(e.getX(),e.getY());
                    repaint();
                }
            });
        }

        //初始化背景
        private void paintBackground(Graphics2D g2) {
            g2.setPaint(Color.LIGHT_GRAY);
            var size = getSize();
            for (int i = 0; i < size.width; i += 10) {
                Shape line = new Line2D.Float(i, 0, i, size.height);
                g2.draw(line);
            }

            for (int i = 0; i < size.height; i += 10) {
                Shape line = new Line2D.Float(0, i, size.width, i);
                g2.draw(line);
            }
            
        }

        Color[] colors = { Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.RED, Color.BLUE, Color.PINK };
        public void paintComponent(Graphics g) {
            System.out.println("paintComponent called");
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            //step1: paintBackground
            paintBackground(g2);
            
            
            //step2: paintBoard
            int colorIndex = 0;
            g2.setStroke(new BasicStroke(2));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));

            for (Shape s : shapes) {
                g2.setPaint(Color.BLACK);
                g2.draw(s);
                g2.setPaint(colors[(colorIndex++) % 6]);
                g2.fill(s);
            }   

            //step3: paintTips

            if (startDrag != null && endDrag != null) {
                var oldStroke = g2.getStroke();
                g2.setPaint(Color.green);
                float[] dash1 = {3.0f,3.0f};
                g2.setStroke( new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 3.0f));
                Shape r;
                if(ra==0){
                    r = makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                }else{
                    r = makeLine(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                }
                g2.draw(r);
                g2.setStroke(oldStroke);
            }


            
        }
        

        private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
            return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
        }
        private Line2D.Float makeLine(int x1,int y1,int x2,int y2){
            return new Line2D.Float(x1,y1,x2,y2);
        }
    }
}